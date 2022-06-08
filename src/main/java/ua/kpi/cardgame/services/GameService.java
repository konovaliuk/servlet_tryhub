package ua.kpi.cardgame.services;

import org.json.simple.JSONObject;
import ua.kpi.cardgame.dao.DAOFactory;
import ua.kpi.cardgame.dao.interfaces.*;
import ua.kpi.cardgame.entities.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GameService {
    private int searchingUsersCount = 0;
    private List<Card> conditions;
    private List<Card> userCards;
    private List<User> sessionUsers;
    private int lastLeader;
    private GameSession gameSession;

    private boolean searchGameAction(User user, String action) {
        UserSearchGameDAO userSearchGameDAO = DAOFactory.getUserSearchGameDAO();

        try {
            if ("search".equals(action)) {
                userSearchGameDAO.startUserSearchGame(user.getUserId());
            } else if ("cancel".equals(action)) {
                userSearchGameDAO.stopUserSearchGame(user.getUserId());
            }
            userSearchGameDAO.commitTransaction();
            return true;
        } catch (SQLException e) {
            userSearchGameDAO.rollbackTransaction();
            return false;
        }
    }

    public boolean searchGame(User user) {
        if (gameSession != null) {
            return false;
        }
        if (searchGameAction(user, "search")) {
            searchingUsersCount++;
            if (searchingUsersCount == 2) {
                startGame();
            }
            return true;
        }
        return false;
    }

    public String getSessionInfo(User user) {
        JSONObject jsonResponse = new JSONObject();

        if (sessionUsers == null || !sessionUsers.contains(user)) {
            jsonResponse.put("result", "");
            return jsonResponse.toJSONString();
        }

        EventDAO eventDAO = DAOFactory.getEventDAO();
        GameSessionDAO gameSessionDAO = DAOFactory.getGameSessionDAO();
        UserGameSessionDAO userGameSessionDAO = DAOFactory.getUserGameSessionDAO();
        IUserCardsDAO userCardsDAO = DAOFactory.getUserCardsDAO();
        ICardDAO cardDAO = DAOFactory.getCardDAO();

        try {
            Event event = eventDAO.getEventById(gameSession.getEventId());

            if (new Date().getTime() - gameSession.getEventStartTime().getTime() > event.getDuration().toMillis()) {
                int nextEventID = event.getEventId() % 2 + 1; // we have only two events

                if (nextEventID == 2) {
                    lastLeader = (lastLeader + 1) % sessionUsers.size();
                    gameSessionDAO.updateEvent(gameSession, nextEventID);
                } else if (nextEventID == 1) {
                    if (gameSession.getStage() + 1 == 4) {
                        stopGame();
                        jsonResponse.put("result", "");
                        return jsonResponse.toJSONString();
                    }
                    gameSessionDAO.updateLeader(gameSession, sessionUsers.get(lastLeader).getUserId());
                    gameSessionDAO.updateCondition(gameSession, conditions.remove(0).getCardId());
                    gameSessionDAO.updateStage(gameSession, gameSession.getStage() + 1);
                    gameSessionDAO.updateEvent(gameSession, nextEventID);
                    for (User player : sessionUsers) {
                        userGameSessionDAO.updateUserSessionChoice(
                            new UserGameSession(gameSession.getSessionId(), player.getUserId(), 0), 0
                        );
                    }
                }
                gameSessionDAO.commitTransaction();
            }

            List<String> cards = userCardsDAO.getUserCards(gameSession.getSessionId(), user.getUserId()).stream()
                .map(card -> {
                    try {
                        return cardDAO.getCardById(card.getCardId()).toJSON();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());

            List<String> choices = userGameSessionDAO.getUsersBySessionId(gameSession.getSessionId()).stream()
                .map(userGameSession -> {
                    try {
                        Card card = cardDAO.getCardById(userGameSession.getUserChoice());
                        if (card == null) {
                            return "null";
                        } else {
                            return card.toJSON();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());

            jsonResponse.put("condition", cardDAO.getCardById(gameSession.getConditionId()).toJSON());
            jsonResponse.put("user_cards", cards);
            jsonResponse.put("user_choices", choices);
            jsonResponse.put("game_session", gameSession.toJSON());
        } catch (SQLException e) {
            gameSessionDAO.rollbackTransaction();
            e.printStackTrace();
        }

        return jsonResponse.toJSONString();
    }

    private void startGame() {
        ICardDAO cardDAO = DAOFactory.getCardDAO();
        List<Card> cards = null;

        try {
            cards = cardDAO.getAllCards();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conditions = cards.stream().filter(card -> card.getType() == CardType.IMAGE).collect(Collectors.toList());
        userCards = cards.stream().filter(card -> card.getType() == CardType.TEXT).collect(Collectors.toList());
        try {
            UserSearchGameDAO userSearchGameDAO = DAOFactory.getUserSearchGameDAO();
            IUserDAO userDAO = DAOFactory.getUserDAO();
            sessionUsers = userSearchGameDAO.getAllUserSearchGame().stream()
                    .map(user -> {
                        try {
                            return userDAO.getUserById(user.getUserId());
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        GameSessionDAO gameSessionDAO = DAOFactory.getGameSessionDAO();
        lastLeader = 0;
        try {
            UserGameSessionDAO userGameSessionDAO = DAOFactory.getUserGameSessionDAO();
            UserSearchGameDAO userSearchGameDAO = DAOFactory.getUserSearchGameDAO();
            IUserCardsDAO userCardsDAO = DAOFactory.getUserCardsDAO();
            gameSession = gameSessionDAO.createSession(
            1, sessionUsers.get(lastLeader).getUserId(), conditions.remove(0).getCardId(), 1
            );
            for (User user : sessionUsers) {
                userGameSessionDAO.setUserGameSession(user.getUserId(), gameSession.getSessionId());
                for (int i = 0; i < 3; i++) {
                    userCardsDAO.addUserCard(
                        new UserCard(gameSession.getSessionId(), user.getUserId(), userCards.remove(0).getCardId())
                    );
                }
                userSearchGameDAO.stopUserSearchGame(user.getUserId());
            }
            gameSessionDAO.commitTransaction();
        } catch (SQLException e) {
            gameSessionDAO.rollbackTransaction();
            e.printStackTrace();
        }
    }

    private void stopGame() {
        IUserCardsDAO userCardsDAO = DAOFactory.getUserCardsDAO();
        UserGameSessionDAO userGameSessionDAO = DAOFactory.getUserGameSessionDAO();
        GameSessionDAO gameSessionDAO = DAOFactory.getGameSessionDAO();

        for (User user : sessionUsers) {
            try {
                userCardsDAO.deleteAllUserCards(gameSession.getSessionId(), user.getUserId());
                userGameSessionDAO.deleteAllBySessionId(gameSession.getSessionId());
                gameSessionDAO.deleteSessionById(gameSession.getSessionId());
                gameSessionDAO.commitTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
                gameSessionDAO.rollbackTransaction();
            }
        }

        conditions = null;
        userCards = null;
        gameSession = null;
        sessionUsers = null;
        lastLeader = 0;
        searchingUsersCount = 0;
    }

    public boolean cancelSearchingGame(User user) {
        if (searchGameAction(user, "cancel")) {
            searchingUsersCount--;
            return true;
        }
        return false;
    }

    public String makeCardChoice(User user, int cardId) {
        JSONObject jsonResponse = new JSONObject();

        if (sessionUsers == null || !sessionUsers.contains(user)) {
            jsonResponse.put("result", "");
            return jsonResponse.toJSONString();
        }

        UserGameSessionDAO userGameSessionDAO = DAOFactory.getUserGameSessionDAO();
        IUserDAO userDAO = DAOFactory.getUserDAO();

        if (gameSession.getLeaderId() == user.getUserId()) {
            try {
                List<Integer> userIds = userGameSessionDAO.getUsersBySessionId(gameSession.getSessionId()).stream()
                        .filter(playerChoice -> playerChoice.getUserChoice() == cardId)
                        .map(userChoice -> userChoice.getUserId()).collect(Collectors.toList());
                User player = userDAO.getUserById(userIds.get(0));
                userDAO.updateUserRate(player, player.getRate() + 1);
                for (User sessionUser : sessionUsers) {
                    if (sessionUser.getUserId() == gameSession.getLeaderId()) {
                        userGameSessionDAO.updateUserSessionChoice(
                            new UserGameSession(gameSession.getSessionId(), sessionUser.getUserId(), cardId), cardId
                        );
                    } else {
                        userGameSessionDAO.updateUserSessionChoice(
                            new UserGameSession(gameSession.getSessionId(), sessionUser.getUserId(), 0), 0
                        );
                    }
                }
                userGameSessionDAO.commitTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
                userDAO.rollbackTransaction();
            }
        } else {
            try {
                IUserCardsDAO userCardsDAO = DAOFactory.getUserCardsDAO();
                userGameSessionDAO.updateUserSessionChoice(
                    new UserGameSession(gameSession.getSessionId(), user.getUserId(), cardId), cardId
                );
                userCardsDAO.deleteUserCard(new UserCard(gameSession.getSessionId(), user.getUserId(), cardId));
                userCardsDAO.addUserCard(new UserCard(gameSession.getSessionId(), user.getUserId(), userCards.remove(0).getCardId()));
                userGameSessionDAO.commitTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
                userGameSessionDAO.rollbackTransaction();
            }
        }

        jsonResponse.put("result", "success");
        return jsonResponse.toJSONString();
    }
}