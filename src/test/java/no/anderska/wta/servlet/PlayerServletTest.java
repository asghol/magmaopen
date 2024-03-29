package no.anderska.wta.servlet;

import no.anderska.wta.dto.PlayerDTO;
import org.dom4j.DocumentHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerServletTest {
    private HttpServletRequest req = mock(HttpServletRequest.class);
    private HttpServletResponse resp = mock(HttpServletResponse.class);
    private PlayerServlet servlet = new PlayerServlet();
    private PlayerHandler playerHandler = mock(PlayerHandler.class);
    private StringWriter htmlDoc = new StringWriter();

    @Test
    public void shouldDisplayCreatePlayerPage() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        when(req.getPathInfo()).thenReturn("/");


        servlet.service(req, resp);

        assertThat(htmlDoc.toString()) //
                .contains("<form method='POST' action='player'") //
                .contains("<input type='text' name='gamerName' value=''") //
                .contains("<input type='submit' name='createGamer' value='Create Gamer'") //
        ;

        verify(resp).setContentType("text/html");
        DocumentHelper.parseText(htmlDoc.toString());
    }

    @Test
    public void shouldDisplayPlayerList() throws Exception {
        when(req.getMethod()).thenReturn("GET");
        when(req.getPathInfo()).thenReturn("/list");

        when(playerHandler.playerList()).thenReturn(Arrays.asList(new PlayerDTO("PlayerOne", 10), new PlayerDTO("PlayerTwo", 20)));

        servlet.service(req, resp);

        verify(resp).setContentType("text/json");

        JSONArray jsonArray = new JSONArray(htmlDoc.toString());


        assertThat(jsonArray.length()).isEqualTo(2);

        JSONObject jsonObject = jsonArray.getJSONObject(0);

        assertThat(jsonObject.get("name")).isEqualTo("PlayerOne");

    }


    @Test
    public void shouldAddPlayer() throws Exception {
        when(req.getParameter("gamerName")).thenReturn("Gamers");
        when(req.getMethod()).thenReturn("POST");
        when(playerHandler.createPlayer(anyString())).thenReturn("42");

        servlet.service(req, resp);

        verify(resp).setContentType("text/html");
        verify(playerHandler).createPlayer("Gamers");

        assertThat(htmlDoc.toString()) //
                .contains("Welcome Gamers you have id 42")
                .contains("<a href='../categories.html'>To game status</a>")
        ;
        DocumentHelper.parseText(htmlDoc.toString());
    }

    @Test
    public void shouldNotAllowIllegalCharactersInName() throws Exception {
        when(req.getParameter("gamerName")).thenReturn("Gam<&>ers");
        when(req.getMethod()).thenReturn("POST");

        servlet.service(req, resp);

        verify(playerHandler, never()).createPlayer(anyString());

        assertThat(htmlDoc.toString()) //
                .contains("<input type='text' name='gamerName' value='Gam&lt;&amp;&gt;ers'") //
                .contains("<p style='color: red;'>Name can only contain letters</p>") //
        ;

        DocumentHelper.parseText(htmlDoc.toString());

    }

    @Test
    public void shouldNotAllowEmptyName() throws Exception {
        when(req.getParameter("gamerName")).thenReturn("");
        when(req.getMethod()).thenReturn("POST");

        servlet.service(req, resp);

        verify(playerHandler, never()).createPlayer(anyString());

        assertThat(htmlDoc.toString()) //
                .contains("<input type='text' name='gamerName' value=''") //
                .contains("<p style='color: red;'>Empty name is not allowed</p>") //
        ;

        DocumentHelper.parseText(htmlDoc.toString());

    }


    @Before
    public void setup() throws IOException {
        servlet.setPlayerHandler(playerHandler);
        when(resp.getWriter()).thenReturn(new PrintWriter(htmlDoc));
    }


}
