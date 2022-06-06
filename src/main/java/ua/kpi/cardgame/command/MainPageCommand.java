package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.UserService;

import java.util.List;

public class MainPageCommand implements ICommand {
    private final static String AUTH_PAGE = "redirect:/login";
    private final static String MAIN_PAGE = "/main.jsp";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("user") == null) {
            return AUTH_PAGE;
        }
        List<User> users = new UserService().getAllOnline();
        req.getSession().setAttribute("users", users);
        return MAIN_PAGE;
    }
}
