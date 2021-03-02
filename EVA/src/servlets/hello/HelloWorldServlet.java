package servlets.hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns =
{ "/HalloWelt", "/HelloWorld" })
public class HelloWorldServlet extends HttpServlet
{
    private int counter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hallo Welt!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hallo Welt!</h1>");
        synchronized (this)
        {
            out.println("<h2>You are visitor " + ++counter + " and the current time ist " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        }
        out.println("</body>");
        out.println("</html>");
    }
}