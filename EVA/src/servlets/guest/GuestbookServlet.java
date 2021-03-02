package servlets.guest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Guestbook")
public class GuestbookServlet extends HttpServlet
{

    /**
     * 
     */
    private static final long serialVersionUID = 6189740681966896990L;

    protected final static String FIRST_NAME = "vorname";

    protected final static String SURNAME = "nachname";

    protected final static String ADDRESS = "wohnort";

    protected final static String AGE = "alter";

    protected final static String CONTENT = "inhalt";

    protected final static String SEPERATOR1 = ": ";

    protected final static String P_BEGIN = "<p>";

    protected final static String P_END = "</p>\n";

    protected final static String TEXT_INPUT_BEGIN = "<input type=\"text\" name=\"";

    protected final static String TEXT_INPUT_END = "\" size=\"20\"/></p>";

    protected final static String TEXTAREA_INPUT_BEGIN = "<textarea name=\"";

    protected final static String TEXTAREA_INPUT_END = "\" rows=\"10\" cols=\"70\"></textarea>";

    private ConcurrentLinkedDeque<AddressBookElement> addressBook;

    @Override
    public void init()
    {
        this.addressBook = new ConcurrentLinkedDeque<AddressBookElement>();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter output = response.getWriter();

        output.println("<html>");
        output.println("<head>");
        output.println("<title>Guestbook</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<form method=\"post\" action=\"Guestbook\">");
        output.println(P_BEGIN + FIRST_NAME + SEPERATOR1 + TEXT_INPUT_BEGIN + FIRST_NAME + TEXT_INPUT_END);
        output.println(P_BEGIN + SURNAME + SEPERATOR1 + TEXT_INPUT_BEGIN + SURNAME + TEXT_INPUT_END);
        output.println(P_BEGIN + ADDRESS + SEPERATOR1 + TEXT_INPUT_BEGIN + ADDRESS + TEXT_INPUT_END);
        output.println(P_BEGIN + AGE + SEPERATOR1 + TEXT_INPUT_BEGIN + AGE + TEXT_INPUT_END);
        output.println(P_BEGIN + CONTENT + SEPERATOR1 + TEXTAREA_INPUT_BEGIN + CONTENT + TEXTAREA_INPUT_END);
        output.println("<input type=\"submit\" value=\"Save\"/>");
        output.println("</form>");
        addressBook.forEach(e -> output.println(e.getEntryAsHTML()));
        output.println("</body>");
        output.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String firstName = request.getParameter(FIRST_NAME);
        String surname = request.getParameter(SURNAME);
        String address = request.getParameter(ADDRESS);
        String age = request.getParameter(AGE);
        String content = request.getParameter(CONTENT);

        System.out.println("new Entry: " + firstName + " : " + surname + " : " + address + " : " + age + " : " + content);

        if (isNotNull(firstName, surname, address, age, content))
        {
            addressBook.add(new AddressBookElement(firstName, surname, address, age, content));
        }

        doGet(request, response);

    }

    private boolean isNotNull(String... strings)
    {
        for (String s : strings)
        {
            if (s == null || s.equals(""))
            {
                return false;
            }
        }
        return true;
    }

}
