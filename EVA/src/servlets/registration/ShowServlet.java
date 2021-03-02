package servlets.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/ShowServlet", loadOnStartup = 1)
public class ShowServlet extends HttpServlet
{

    @Override
    public void init() throws ServletException
    {
        ArrayList<Person> list = new ArrayList<Person>();
        getServletContext().setAttribute(RegisterServlet.PERSONLIST, list);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType("text/html");
        PrintWriter output = response.getWriter();
        ArrayList<Person> list = (ArrayList<Person>) getServletContext().getAttribute(RegisterServlet.PERSONLIST);

        output.println("<html>\n<header>");
        output.println("List of Users");
        output.println("</header>\n<body>");
        synchronized (list)
        {
            list.forEach(person -> output.println(person.getUsername()));
        }
        output.println("</body>\n</html>");

    }
}
