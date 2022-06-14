package ua.kpi.cardgame.services;

import ua.kpi.cardgame.dao.DAOFactory;
import ua.kpi.cardgame.dao.interfaces.IUserDAO;
import ua.kpi.cardgame.dao.interfaces.IUserOnlineDAO;
import ua.kpi.cardgame.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService {
    public User register(String login, String password) {
        IUserDAO userDAO = DAOFactory.getUserDAO();
        IUserOnlineDAO userOnlineDAO = DAOFactory.getUserOnlineDAO();
        User user = null;

        try {
            if (userDAO.getUserByLogin(login) == null) {
                user = userDAO.createUser(login, password);
                userOnlineDAO.setUserOnline(user.getUserId());
//                userDAO.commitTransaction();
            } else {
                return null;
            }
        } catch (SQLException e) {
//            userDAO.rollbackTransaction();
            e.printStackTrace();
        }

        return user;
    }

    public User login(String login, String password) {
        IUserDAO userDAO = DAOFactory.getUserDAO();
        IUserOnlineDAO userOnlineDAO = DAOFactory.getUserOnlineDAO();
        User user = null;

        try {
            user = userDAO.getUserByLogin(login);
            if (user != null && !password.equals(user.getPassword())) {
                user = null;
            } else if (user != null) {
                userOnlineDAO.setUserOnline(user.getUserId());
//                userOnlineDAO.commitTransaction();
            }
        } catch (SQLException e) {
//            userOnlineDAO.rollbackTransaction();
            e.printStackTrace();
        }

        return user;
    }

    public void logout(User user) {
        IUserOnlineDAO userOnlineDAO = DAOFactory.getUserOnlineDAO();

        try {
            userOnlineDAO.setUserOffline(user.getUserId());
//            userOnlineDAO.commitTransaction();
        } catch (SQLException e) {
//            userOnlineDAO.rollbackTransaction();
            e.printStackTrace();
        }
    }

    public void setUserOnline(User user) {
        IUserOnlineDAO userOnlineDAO = DAOFactory.getUserOnlineDAO();

        try {
            userOnlineDAO.setUserOnline(user.getUserId());
//            userOnlineDAO.commitTransaction();
        } catch (SQLException e) {
            // pass
        }
    }

    public List<User> getAllOnline() {
        IUserDAO userDAO = DAOFactory.getUserDAO();
        IUserOnlineDAO userOnlineDAO = DAOFactory.getUserOnlineDAO();
        List<User> users = new ArrayList<>();

        try {
            users = userOnlineDAO.getAllOnlineUsers().stream().
                    filter(userOnline -> new Date().getTime() - userOnline.getTimestamp().getTime() < 30000).
                    map(userOnline -> {
                        try {
                            return userDAO.getUserById(userOnline.getUserId());
                        } catch (SQLException e) {
                            return null;
                        }
                    }).
                    filter(user -> user != null).
                    sorted((User u1, User u2) -> u2.getRate() - u1.getRate()).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
