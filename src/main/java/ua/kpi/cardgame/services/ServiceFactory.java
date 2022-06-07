package ua.kpi.cardgame.services;

public class ServiceFactory {
    private static final UserService userService = new UserService();

    private ServiceFactory() {}

    public static UserService getUserService() {
        return userService;
    }
}
