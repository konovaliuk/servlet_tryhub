package ua.kpi.cardgame.servlet;

import jakarta.servlet.http.HttpServletRequest;
import ua.kpi.cardgame.command.*;

import java.util.HashMap;

public class ControllerHelper {
    private static ControllerHelper instance;
    private HashMap<String, ICommand> commands = new HashMap<>();

    private ControllerHelper() {
        commands.put("/register", new RegisterCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/main", new MainPageCommand());
        commands.put("/lobby", new LobbyPageCommand());
        commands.put("/api/searchGame", new SearchGameCommand());
        commands.put("/api/cancelSearch", new SearchGameCommand());
        commands.put("/api/getSessionInfo", new GameSessionInfoCommand());
        commands.put("/api/makeChoice", new MakeChoiceCommand());
    }

    public static ControllerHelper getInstance() {
        if (instance == null) {
            instance = new ControllerHelper();
        }

        return instance;
    }

    public ICommand getCommand(HttpServletRequest req) {
        if (req.getParameter("command") == null) {
            return commands.get(req.getRequestURI());
        } else {
            return commands.get(req.getParameter("command"));
        }
    }
}
