package servlets.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignOff")
public class SignOffServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType("text/html");
        PrintWriter output = response.getWriter();

        output.println("<html>\n<header>");
        output.println("Login");
        output.println("</header>\n<body>");
        output.println("<form method=\"POST\" action=\"SignOff\">");
        output.println("<p> username:<input type=\"text\" name=\"" + RegisterServlet.USERNAME + "\" size=\"20\"/</p>");
        output.println("<p>password:<input type=\"password\" name=\"" + RegisterServlet.PASSWORD + "\" size=\"20\"/</p>");
        output.println("<button type=\"submit\">LogOut</button>");
        output.println("</form>\n</body>\n</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String user = request.getParameter(RegisterServlet.USERNAME);
        String pw = request.getParameter(RegisterServlet.PASSWORD);

        if (user.isEmpty() || pw.isEmpty())
        {
            doGet(request, response);
            return;
        }

        ArrayList<Person> list = (ArrayList<Person>) getServletContext().getAttribute(RegisterServlet.PERSONLIST);

        PrintWriter output = response.getWriter();
        output.println("<html>\n<header>");

        String message = "Sign off was'nt successfully";
        String successMessage = "Sign off was successfully";
        synchronized (list)
        {
            for (Person person : list)
            {
                if (person.getUsername().equals(user) && person.isPasswordValid(pw))
                {
                    list.remove(person);
                    message = successMessage;
                    break;
                }
            }
        }

        output.println(message);
        output.println("</header>\n<body>");
        output.println("weiterleitung...");
        output.println("</body>\n</html>");

        response.setHeader("Refresh", "4; URL=" + (message.equals(successMessage) ? "RegisterServlet" : "SignOff"));
    }

}
