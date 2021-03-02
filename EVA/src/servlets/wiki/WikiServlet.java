package servlets.wiki;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Wiki")
public class WikiServlet extends HttpServlet
{
    private final static String CONTENT = "inhalt";

    private final int timeout = 60;

    private WikiArticle article;

    @Override
    public void init() throws ServletException
    {
        this.article = new WikiArticle("Demo", timeout);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Session
        HttpSession session = request.getSession(true);
        // session.setMaxInactiveInterval(60);
        article.setSessionLock(session);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Wiki</title>");
        if (article.isSessionLock(session))
        {
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + "<style>\r\n" + "p {\r\n" + "  text-align: center;\r\n" + "  font-size: 60px;\r\n" + "  margin-top: 0px;\r\n" + "}\r\n" + "</style>\r\n");
        }
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Wiki Editor for " + article.getTitle() + "</h1>");

        if (article.isSessionLock(session))
        {
            out.print("<h2>Time until option to save document expires:</h2>");
            out.println("<h2 id=\"timer\"></h2>");
            out.println("<form method=\"post\" action=\"Wiki\" >");
            out.println("<p><button type=\"submit\">Save</button></p>");
            out.println("<textarea name=\"" + CONTENT + "\" rows=\"40\" cols=\"70\">");
            out.println(article.getArticle());
            out.println("</textarea>");
            out.println("</form>");
            // Auskommentieren fuer ASB
            setTimer(out, timeout);
        }
        else
        {
            // article.printArticle(out);
            out.println("<label name=" + CONTENT + ">" + article.getArticle() + "</label>");
        }
        out.println("</body>");
        out.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO use Sessions

        // article and session
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        out.println("<html><head>\r\n" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + "<style>\r\n" + "p {\r\n" + "  text-align: center;\r\n" + "  font-size: 60px;\r\n" + "  margin-top: 0px;\r\n" + "}\r\n" + "</style>\r\n");

        if (article.isSessionLock(session))
        {
            String content = request.getParameter(CONTENT);
            System.out.println("Content: " + content);
            article.setArticle(session, content);

            article.removeSessionLock(session);
            session.invalidate();
            out.println("<title>SAVED</title");
            out.println("<h1>Saved!</h1>");
        }
        else
        {
            out.println("<title>ERROR</title");
            out.println("<h1>Saving was not successfully!</h1>");
        }
        // response
        response.setHeader("Refresh", "4; URL=Wiki");
        out.println("</head>\n<body>\n");
        out.println();
        // Auskommentieren fuer ASB
        setTimer(out, 5);
        out.println("<h2>Weiterleitung in 4 Sekunden...</h2>");
        out.println("<h2 id=\"timer\"></h2>");
        out.println("<textarea name=\"" + CONTENT + "\" readonly=\"readonly\">" + article.getArticle() + "</textarea>");
        out.println("</body>\n</html>");

    }

    // Skript zum anzeigen eines Counters auf Clientseite
    private void setTimer(PrintWriter output, int timeInSeconds)
    {
        int time = (timeInSeconds + 1) * 1000;
        output.println("<script>\r\n" + "var distance = " + time + ";\r\n" + "\r\n" + "// Update the count down every 1 second\r\n" + "var x = setInterval(function() {\r\n" + "\r\n" + "    \r\n" + "  // Find the distance between now and the count down date\r\n" + "  distance = distance-1000;\r\n" + "    \r\n" + "  // Time calculations for days, hours, minutes and seconds\r\n" + "  var days = Math.floor(distance / (1000 * 60 * 60 * 24));\r\n" + "  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));\r\n" + "  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));\r\n" + "  var seconds = Math.floor((distance % (1000 * 60)) / 1000);\r\n" + "    \r\n" + "  // Output the result in an element with id=\"timer\"\r\n" + "  document.getElementById(\"timer\").innerHTML = days + \"d \" + hours + \"h \"\r\n" + "  + minutes + \"m \" + seconds + \"s \";\r\n" + "    \r\n" + "  // If the count down is over, write some text \r\n" + "  if (distance < 0) {\r\n" + "    clearInterval(x);\r\n" + "    document.getElementById(\"timer\").innerHTML = \"EXPIRED\";\r\n" + "  }\r\n" + "}, 1000);\r\n" + "</script>");
    }
}
