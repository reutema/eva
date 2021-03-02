package servlets.pizza;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Pizza")
public class PizzaServlet extends HttpServlet
{
    /**
     * 
     */
    private static final long serialVersionUID = -8579714174537359319L;

    private final static String COOKIE_NAME = "pizzaCookie";

    private final static int MAX_COOKIE_AGE = 10;

    private final static String PIZZA_LIST = "pizzaliste";

    private final String[][] inputFields =
    {
        { "kundennummer", "Kundennummer" },
        { "kundenname", "Name" },
        { "adresse", "Adresse" } };

    private final String[] listOfPizzas =
    { "Vegetaria", "Diavolo", "Quattro Stagioni", "Funghi" };

    private ConcurrentHashMap<String, Customer> customerList;

    private Random rnd;

    @Override
    public void init() throws ServletException
    {
        this.customerList = new ConcurrentHashMap<String, Customer>();
        this.rnd = new Random();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Cookie cookie = getCustomerCookie(request);
        Customer customer = getCustomer(cookie);

        response.setContentType("text/html");
        printFormular(response.getWriter(), customer);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Cookie cookie = getCustomerCookie(request);
        String[] attributes = getAttributes(request);
        Customer customer;

        if (cookie != null)
        {
            customer = getCustomer(cookie);
            customer.setAttributes(attributes);
        }
        else
        {
            try
            {
                customer = new Customer(attributes);
            }
            catch (IllegalArgumentException iae)
            {
                customer = new Customer();
            }
        }

        Cookie newCookie = getNewCookie(customer);
        response.addCookie(newCookie);
        response.setContentType("text/html");

        printOrder(response.getWriter(), customer);

    }

    private void printOrder(PrintWriter out, Customer customer)
    {
        String title = "Your Order is";

        out.println("<html>");
        out.println("<title>");
        out.println(title);
        out.println("</title>");
        out.println("<head>");
        out.println(title + ":");
        out.println("</head>");
        out.println("<body><br>");
        out.println(customer.getAttributesAsHTML(inputFields));
        out.println("</body>");
        out.println("</html>");
    }

    private void printFormular(PrintWriter out, Customer customer)
    {
        String title = "Pizza Order Service";
        out.println("<html>");
        out.println("<title>");
        out.println(title);
        out.println("</title>");
        out.println("<head>");
        out.println(title);
        out.println("<body>");
        out.println("<form method=\"post\" action=\"Pizza\">");
        fillFormular(out, customer);
        out.println("<input type=\"submit\" value=\"Send\" size=\"20\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    private void fillFormular(PrintWriter out, Customer customer)
    {

        for (String[] s : inputFields)
        {
            if (!s[0].equals(inputFields[2][0]))
            {
                out.println(s[1] + "<input type=\"text\" name=\"" + s[0] + "\" value=\"" + getCustomerAttribute(customer, s[0]) + "\" size=\"20\"><br>");
            }
            else
            {
                out.println(s[1] + "<textarea type=\"text\" name=\"" + s[0] + "\" size=\"20\">" + getCustomerAttribute(customer, s[0]) + "</textarea><br>");
            }
        }

        String pizza;
        if (customer == null)
        {
            pizza = "";
        }
        else
        {
            pizza = customer.getOrder();
        }

        out.println("<label>Pizzas:");
        out.println("<select name=\"" + PIZZA_LIST + "\">");

        for (String s : listOfPizzas)
        {
            out.println("<option " + (pizza.contentEquals(s) ? " selected " : "") + "\" size=\"20\">" + s + "</option>");
        }
        out.println("</select>");
        out.println("</label>");

    }

    private Cookie getCustomerCookie(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().contentEquals(COOKIE_NAME))
                {
                    return cookie;
                }
            }
        }
        return null;
    }

    private Cookie getNewCookie(Customer customer)
    {
        String customerID = customer.getId();
        String cookieID = customerID + rnd.nextInt(256);
        Cookie cookie = new Cookie(COOKIE_NAME, cookieID);

        cookie.setMaxAge(MAX_COOKIE_AGE);
        customer.setCookieID(cookieID);
        customer.setCookieExpireDate(System.currentTimeMillis() + (MAX_COOKIE_AGE * 1000));
        customerList.put(cookieID, customer);

        return cookie;
    }

    private Customer getCustomer(Cookie cookie)
    {
        return (cookie == null ? null : customerList.get(cookie.getValue()));
    }

    private String getCustomerAttribute(Customer customer, String attribute)
    {
        if (customer == null)
        {
            return "";
        }

        return customer.getAttribute(attribute, inputFields);
    }

    private String[] getAttributes(HttpServletRequest request)
    {
        String[] attributes = new String[inputFields.length + 1];
        int i = 0;

        for (String[] s : inputFields)
        {
            attributes[i++] = request.getParameter(s[0]);
        }
        attributes[i] = request.getParameter(PIZZA_LIST);

        return attributes;
    }
}
