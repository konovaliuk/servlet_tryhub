package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.ServiceFactory;

public class LogoutCommand implements ICommand {
    private static final String AUTH_PAGE = "redirect:/login";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ServiceFactory.getUserService().logout((User) req.getSession().getAttribute("user"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getSession().invalidate();
        return AUTH_PAGE;
    }
}
