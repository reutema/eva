package servlets.calc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Calculator")
public class CalculatorServlet extends HttpServlet
{
    public final static String FIRST_OP = "firstOp";

    public final static String SECOND_OP = "secondOp";

    public final static String OPERATION = "operation";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

        response.setContentType("text/html");
        PrintWriter output = response.getWriter();

        try
        {
            int value1 = Integer.valueOf(request.getParameter(FIRST_OP));
            int value2 = Integer.valueOf(request.getParameter(SECOND_OP));
            String op = request.getParameter(OPERATION);

            int result = 0;
            switch (op)
            {
                case "+":
                    result = value1 + value2;
                    break;
                case "-":
                    result = value1 - value2;
                    break;
                case "*":
                    result = value1 * value2;
                    break;
                case "/":
                    result = value1 / value2;
                    break;
                default:
                    result = Integer.MIN_VALUE;
                    break;
            }

            output.println("<html>");
            output.println("<head>");
            output.println("<title>Ergebnis</title>");
            output.println("</head>");
            output.println("<body>");
            output.println("<h1>Ergebnis</h1>");
            output.print("<p>" + value1 + op + value2 + "=</p>");
            output.println("<p id=\"result\">" + result + "</p>");
            output.println("</body>");
            output.println("</html>");
        }
        catch (NumberFormatException | NullPointerException e)
        {
            output.println("<html>");
            output.println("<head>");
            output.println("<title>Error</title");
            output.println("</head>");
            output.println("<body>");
            output.println("<h1>No calculation possible!</h1>");
            output.println("<p>" + e.getMessage() + "</p>");
            output.println("</body>");
            output.println("</html>");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doGet(request, response);
    }

}
