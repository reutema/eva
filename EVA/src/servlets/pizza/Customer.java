package servlets.pizza;

public class Customer
{

    private static int counter;

    private String id;

    private String name;

    private String address;

    private String order;

    private String cookieID;

    private long cookieExpireDate;

    public Customer()
    {
        this("Name not found", "Address not found", "Order not found");
    }

    public Customer(String name, String address, String order)
    {
        this(null, name, address, order);
    }

    public Customer(String id, String name, String address, String order)
    {
        this(id, name, address, order, "", -1);
    }

    public Customer(String id, String name, String address, String order, String cookieID, long cookieExpireDate)
    {
        setId(id);
        this.name = name;
        this.address = address;
        this.order = order;
        this.cookieID = cookieID;
        this.cookieExpireDate = cookieExpireDate;
    }

    public Customer(String[] attributes)
    {
        this(attributes, "", -1);
    }

    public Customer(String[] attributes, String cookieID, long cookieExpireDate)
    {
        setAttributes(attributes);
        this.cookieID = cookieID;
        this.cookieExpireDate = cookieExpireDate;
    }

    public synchronized String getId()
    {
        return id;
    }

    public synchronized void setId(String id)
    {
        this.id = (id == null || id.contentEquals("") ? generateNewCustomerID() : id);
    }

    public synchronized String getName()
    {
        return name;
    }

    public synchronized void setName(String name)
    {
        this.name = name;
    }

    public synchronized String getAddress()
    {
        return address;
    }

    public synchronized void setAddress(String address)
    {
        this.address = address;
    }

    public synchronized String getOrder()
    {
        return order;
    }

    public synchronized void setOrder(String order)
    {
        this.order = order;
    }

    public synchronized String getCookieID()
    {
        return cookieID;
    }

    public synchronized void setCookieID(String cookieID)
    {
        this.cookieID = cookieID;
    }

    public synchronized long getCookieExpireDate()
    {
        return cookieExpireDate;
    }

    public synchronized void setCookieExpireDate(long cookieExpireDate)
    {
        this.cookieExpireDate = cookieExpireDate;
    }

    public synchronized String getAttribute(String attribute, String[][] inputFields)
    {
        String result = "Error, attribute not valid";

        if (inputFields[0][0].contentEquals(attribute))
        {
            result = this.getId();
        }
        else if (inputFields[1][0].contentEquals(attribute))
        {
            result = this.getName();
        }
        else if (inputFields[2][0].contentEquals(attribute))
        {
            result = this.getAddress();
        }

        return result;
    }

    public synchronized void setAttributes(String[] attributes)
    {
        if (attributes.length < 4)
        {
            throw new IllegalArgumentException();
        }
        setId(attributes[0]);
        this.name = attributes[1];
        this.address = attributes[2];
        this.order = attributes[3];
    }

    public synchronized String getAttributesAsHTML(String[][] inputFields)
    {
        StringBuffer buffer = new StringBuffer();
        for (String[] field : inputFields)
        {
            buffer.append("<label>" + field[1] + ": " + getAttribute(field[0], inputFields) + "</label><br>");
        }

        buffer.append("<label> Pizza: " + order + "</label");

        return buffer.toString();
    }

    private static synchronized String generateNewCustomerID()
    {
        return ++counter + System.currentTimeMillis() + "";
    }

}
