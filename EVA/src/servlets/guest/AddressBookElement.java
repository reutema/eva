package servlets.guest;

public class AddressBookElement
{

    private String firstName;

    private String surname;

    private String address;

    private String age;

    private String content;

    public AddressBookElement(String firstName, String surname, String address, String age, String content)
    {
        setFirstName(firstName);
        setSurname(surname);
        setAddress(address);
        setAge(age);
        setContent(content);
    }

    public synchronized String getFirstName()
    {
        return firstName;
    }

    public synchronized void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public synchronized String getSurname()
    {
        return surname;
    }

    public synchronized void setSurname(String surname)
    {
        this.surname = surname;
    }

    public synchronized String getAddress()
    {
        return address;
    }

    public synchronized void setAddress(String address)
    {
        this.address = address;
    }

    public synchronized String getAge()
    {
        return age;
    }

    public synchronized void setAge(String age)
    {
        this.age = age;
    }

    public synchronized String getContent()
    {
        return content;
    }

    public synchronized void setContent(String content)
    {
        this.content = content;
    }

    public synchronized String getEntryAsHTML()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<h3>" + getFirstName() + " " + getSurname() + "</h3>\n");
        buffer.append(GuestbookServlet.P_BEGIN + GuestbookServlet.ADDRESS + GuestbookServlet.SEPERATOR1 + getAddress() + GuestbookServlet.P_END);
        buffer.append(GuestbookServlet.P_BEGIN + GuestbookServlet.AGE + GuestbookServlet.SEPERATOR1 + getAge() + GuestbookServlet.P_END);
        buffer.append(GuestbookServlet.P_BEGIN + GuestbookServlet.CONTENT + GuestbookServlet.SEPERATOR1 + getContent() + GuestbookServlet.P_END);
        buffer.append(GuestbookServlet.P_BEGIN + GuestbookServlet.P_END);

        return buffer.toString();

    }
}
