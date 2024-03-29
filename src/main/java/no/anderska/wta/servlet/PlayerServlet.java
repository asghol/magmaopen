package no.anderska.wta.servlet;

import no.anderska.wta.SetupGame;
import no.anderska.wta.dto.PlayerDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PlayerServlet extends HttpServlet {

    private PlayerHandler playerHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("/list".equals(req.getPathInfo())) {
            resp.setContentType("text/json");
            List<PlayerDTO> players = playerHandler.playerList();
            resp.getWriter().append(JsonUtil.toJson(players).toString());
        } else {
            displayCreatePage(resp, null, "");
        }
    }

    private void displayCreatePage(HttpServletResponse resp, String errormessage, String nameValue) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.append("<html><body>");
        if (errormessage != null) {
            writer.append("<p style='color: red;'>").append(errormessage).append("</p>");
        }
        writer
                .append("<form method='POST' action='player'>") //
                .append("<input type='text' name='gamerName' value='") //
                .append(nameValue) //
                .append("'/>") //
                .append("<input type='submit' name='createGamer' value='Create Gamer'/>") //
                .append("</form>") //
        ;
        writer.append("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        String gamerName = req.getParameter("gamerName");
        String errormessage = validateName(gamerName);
        if (errormessage == null) {
            String playerId = playerHandler.createPlayer(gamerName);
            PrintWriter writer = resp.getWriter();
            writer.append("<html><body>");
            writer.append("<p>Welcome ");
            writer.append(gamerName);
            writer.append(" you have id ");
            writer.append(playerId);
            writer.append("</p>");
            writer.append("<p><a href='../categories.html'>To game status</a></p>");
            writer.append("</body></html>");
        } else {
            displayCreatePage(resp, errormessage, htmlEscape(gamerName));
        }
    }

    private String htmlEscape(String text) {
        return text //
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                ;
    }

    private String validateName(String gamerName) {
        if (gamerName == null || gamerName.trim().isEmpty()) {
            return "Empty name is not allowed";
        }
        for (char c : gamerName.toCharArray()) {
            if (Character.isLetter(c) || c == ' ') {
                continue;
            }
            return "Name can only contain letters";
        }
        return null;
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    @Override
    public void init() throws ServletException {
        this.playerHandler = SetupGame.instance().getPlayerHandler();

    }
}
