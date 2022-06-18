package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.ServiceFactory;

public class SearchGameCommand implements ICommand {
    private static final String API_PAGE = "/api.jsp";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject json = new JSONObject();
        try {
            User user = (User) req.getSession().getAttribute("user");
            Boolean result = false;

            if (req.getRequestURI().contains("search")) {
                result = ServiceFactory.getGameService().searchGame(user);
                req.getSession().setAttribute("gameSearch", result);
            } else if (req.getRequestURI().contains("cancel")){
                result = ServiceFactory.getGameService().cancelSearchingGame(user);
                req.getSession().setAttribute("gameSearch", false);
            }

            if (result) {
                json.put("result", true);
            } else {
                json.put("result", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "error");
        }
        req.setAttribute("json_response", json.toJSONString());
        return API_PAGE;
    }
}
