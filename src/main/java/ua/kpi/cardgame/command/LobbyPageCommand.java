package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LobbyPageCommand implements ICommand {
    private final static String LOBBY_PAGE = "/lobby.jsp";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return LOBBY_PAGE;
    }
}
