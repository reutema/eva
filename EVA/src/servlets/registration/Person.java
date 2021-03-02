package servlets.registration;

public class Person
{
    private String username;

    private String password;

    public Person(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return this.username;
    }

    public boolean isPasswordValid(final String passwd)
    {
        return this.password.equals(passwd);
    }
}
