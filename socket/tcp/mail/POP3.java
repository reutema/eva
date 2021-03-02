package socket.tcp.mail;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;

import socket.tcp.TCPSocket;

public class POP3
{

    private final String comUser = "USER ";

    private final String comPassw = "PASS ";

    private final String comStatistic = "STAT ";

    private final String comRetrieve = "RETR ";

    private final String comQuit = "QUIT";

    private final String newLine = "\n";

    private final String seperator = " ";

    private TCPSocket socket;

    private boolean showAnswer;

    public POP3(String address, int port, boolean showAnswer) throws UnknownHostException, IOException
    {
        this.socket = new TCPSocket(address, port);
        this.showAnswer = showAnswer;
        System.out.println("<receive: " + socket.receiveLine());
    }

    /*
     * login with user and password and returns number of messages, if no
     * message it return -1
     */

    public int login(char[] user, char[] password) throws IOException
    {
        String send = comUser + new String(user);
        String[] answerArray = split(sendAndRecieve(send));

        if (isAnswerOK(answerArray))
        {
            send = comPassw + new String(password);
            answerArray = split(sendAndRecieve(send));

            if (isAnswerOK(answerArray))
            {
                clear(user);
                clear(password);
                return Integer.valueOf(42);
            }
        }

        return -1;

    }

    public String retrieve(int index) throws IOException
    {
        String send = comRetrieve + index;

        return sendAndRecieve(send);

    }

    public void quit() throws IOException
    {
        sendAndRecieve(comQuit);
    }

    private String sendAndRecieve(String message) throws IOException
    {

        socket.sendLine(message);

        String answer = socket.receiveLine();
        if (showAnswer)
        {
            System.out.println(">send: " + message);
            System.out.println("<receive: " + answer);
        }

        return answer;
    }

    private String[] split(String message)
    {
        return message.split(seperator);
    }

    public boolean isAnswerOK(String[] args) throws IOException
    {
        return args[0].equals("+OK");
    }

    private void clear(char[] array)
    {
        Random rnd = new Random();

        for (int i = 0; i < array.length; i++)
        {
            array[i] = (char) rnd.nextInt();
        }
    }
}
