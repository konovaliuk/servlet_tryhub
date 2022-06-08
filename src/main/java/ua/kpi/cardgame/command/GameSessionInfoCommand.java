package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.ServiceFactory;

public class GameSessionInfoCommand implements ICommand {
    private final String API_PAGE = "/api.jsp";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "error");
            return API_PAGE;
        }

        String response = ServiceFactory.getGameService().getSessionInfo(user);
        try {
            if (((JSONObject) (new JSONParser().parse(response))).get("result") != "error") {
                req.getSession().removeAttribute("gameSearch");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        req.setAttribute("json_response", response);
        return API_PAGE;
    }
}
