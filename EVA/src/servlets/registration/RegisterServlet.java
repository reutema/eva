package servlets.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet
{
    public final static String PERSONLIST = "personenliste";

    public final static String USERNAME = "benutzername";

    public final static String PASSWORD = "passwort";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType("text/html");
        PrintWriter output = response.getWriter();

        output.println("<html>\n<header>");
        output.println("Login");
        output.println("</header>\n<body>");
        output.println("<form method=\"POST\" action=\"Register\">");
        output.println("<p> username:<input type=\"text\" name=\"" + USERNAME + "\" size=\"20\"/></p>");
        output.println("<p>password:<input type=\"password\" name=\"" + PASSWORD + "\" size=\"20\"/></p>");
        output.println("<button type=\"submit\">Login</button>");
        output.println("</form>\n</body>\n</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter(USERNAME);
        String pw = request.getParameter(PASSWORD);

        if (user.isEmpty() || pw.isEmpty())
        {
            doGet(request, response);
            return;
        }

        ArrayList<Person> list = (ArrayList<Person>) getServletContext().getAttribute(RegisterServlet.PERSONLIST);
        Person person = null;
        PrintWriter output = response.getWriter();
        output.println("<html>\n<header>");

        synchronized (list)
        {
            for (Person pers : list)
            {
                if (pers.getUsername().contentEquals(user) && pers.isPasswordValid(pw))
                {
                    person = pers;
                    output.println("Login was successfully!");
                }
            }
        }

        if (person == null)
        {

            person = new Person(user, pw);

            synchronized (list)
            {

                list.add(person);

            }

            output.println("Registration was successfully!");
        }

        output.println("</header>\n<body>");
        output.println("weiterleitung...");
        output.println("</body>\n</html>");

        response.setHeader("Refresh", "4; URL=ShowServlet");
    }
}
