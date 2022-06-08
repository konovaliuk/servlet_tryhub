package ua.kpi.cardgame.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.kpi.cardgame.command.ICommand;
import ua.kpi.cardgame.command.UserOnlineCommand;

import java.io.IOException;

@WebServlet(name="Controller", urlPatterns = {"/register", "/login", "/logout", "/main", "/lobby", "/api/*"})
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp)
    throws IOException, ServletException {
        new UserOnlineCommand().execute(req, resp);
        ICommand command = ControllerHelper.getInstance().getCommand(req);
        String page = command.execute(req, resp);
        if (page.startsWith("redirect:")) {
            resp.sendRedirect(page.substring(9));
        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        }
    }
}