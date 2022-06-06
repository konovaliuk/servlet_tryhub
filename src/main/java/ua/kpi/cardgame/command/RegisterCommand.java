package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.UserService;

public class RegisterCommand implements ICommand {
    private final static String AUTH_PAGE = "/auth.jsp";
    private final static String MAIN_PAGE = "redirect:/main";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("user") != null) {
            return MAIN_PAGE;
        }
        req.setAttribute("command", "register");

        if ("GET".equals(req.getMethod())) {
            return AUTH_PAGE;
        } else {
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
                req.setAttribute("error", "Invalid username or password");
                return AUTH_PAGE;
            }

            User user = new UserService().register(login, password);

            if (user == null) {
                req.setAttribute("error", "Username is already taken");
                return AUTH_PAGE;
            } else {
                req.getSession().setAttribute("user", user);
                return MAIN_PAGE;
            }
        }
    }
}
