package ua.kpi.cardgame.services;

public class ServiceFactory {
    private static final UserService userService = new UserService();
    private static final GameService gameService = new GameService();

    private ServiceFactory() {}

    public static UserService getUserService() {
        return userService;
    }

    public static GameService getGameService() {
        return gameService;
    }
}
