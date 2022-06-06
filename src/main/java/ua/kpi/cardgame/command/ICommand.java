package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ICommand {
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
