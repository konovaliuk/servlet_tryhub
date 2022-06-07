package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.ServiceFactory;

public class UserOnlineCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("user") != null) {
            ServiceFactory.getUserService().setUserOnline((User) req.getSession().getAttribute("user"));
        }

        return "";
    }
}
