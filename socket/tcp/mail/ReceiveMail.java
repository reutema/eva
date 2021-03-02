package socket.tcp.mail;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ReceiveMail
{

    private String mailServer;

    private int port;

    private char[] loginName;

    private char[] password;

    private POP3 pop3;

    public ReceiveMail(String mailServer, int port, char[] loginName, char[] password) throws UnknownHostException, IOException
    {
        this.pop3 = new POP3(mailServer, port, true);
        this.loginName = loginName;
        this.password = password;

    }

    public String[] getMails(int numberOfMails) throws IOException
    {
        int openMails = pop3.login(loginName, password);
        String[] messages = null;
        if (openMails != -1)
        {
            int rounds = (openMails < numberOfMails ? openMails : numberOfMails);
            messages = new String[rounds];
            for (int messageIndex = 0; messageIndex < rounds; messageIndex++)
            {
                messages[messageIndex] = pop3.retrieve(messageIndex + 1);
            }
        }
        return messages;
    }

    public void close() throws IOException
    {
        pop3.quit();
    }

    public static void main(String[] args)
    {
        if (args.length < 4)
        {
            System.out.println("parameters: <mail server> <port> <number of mails to read> <login name> <password>");
        }
        System.out.println("starting ReceiveMail");

        String mailServer = args[0];
        int port = Integer.valueOf(args[1]);
        int numberOfMails = Integer.valueOf(args[2]);
        char[] loginName = args[3].toCharArray();
        char[] password = args[4].toCharArray();

        ReceiveMail rm;
        try
        {
            rm = new ReceiveMail(mailServer, port, loginName, password);

            String[] mails = rm.getMails(numberOfMails);

            rm.close();

            System.out.println("---MAILS---\n");
            System.out.println(Arrays.toString(mails));
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("end ReceiveMail");

    }

}
