package ua.kpi.cardgame.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import ua.kpi.cardgame.entities.User;
import ua.kpi.cardgame.services.ServiceFactory;

public class MakeChoiceCommand implements ICommand {
    private static final String API_PAGE = "/api.jsp";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "error");
            return API_PAGE;
        }


        int cardId = Integer.parseInt(req.getParameter("card_id"));
        req.setAttribute("json_response", ServiceFactory.getGameService().makeCardChoice(user, cardId));
        return API_PAGE;
    }
}
