package socket.tcp.mail;

import java.io.IOException;

import socket.tcp.TCPSocket;

public class SMTP
{

    private final String helo = "HELO ";

    private final String mailFrom = "MAIL FROM:<";

    private final String mailTo = "RCPT TO:<";

    private final String data = "DATA";

    private final String closing = ">";

    private final String dataClosing = ".";

    private final String quit = "QUIT";

    private final String newLine = "\n";

    private final String sendString = ">send: ";

    private final String recieveString = "<recieve: ";

    private TCPSocket socket;

    private boolean showAnswer;

    public SMTP(TCPSocket socket, boolean showAnswer)
    {
        this.socket = socket;
        this.showAnswer = showAnswer;
    }

    public void hello(String sendingHost) throws IOException
    {
        String s = helo + sendingHost;
        print(sendString + s);
        socket.sendLine(s);
    }

    public void from(String senderMailAddress) throws IOException
    {
        String s = mailFrom + senderMailAddress + closing;
        print(sendString + s);
        socket.sendLine(s);
    }

    public void to(String recieverMailAddress) throws IOException
    {
        String s = mailTo + recieverMailAddress + closing;
        print(sendString + s);
        socket.sendLine(s);
    }

    public void data() throws IOException
    {
        print(sendString + data);
        socket.sendLine(data);
    }

    public void sendData(String message) throws IOException
    {
        String s = message + newLine + dataClosing;
        print(sendString + s);
        socket.sendLine(s);
    }

    public void quit() throws IOException
    {
        String s = quit;
        print(sendString + s);
        socket.sendLine(s);
    }

    public boolean isAnswerOK() throws IOException
    {
        String answer = getAnswer();

        print(recieveString + answer);
        return answer.startsWith("250 ");
    }

    public boolean isAnswerReadyForMail() throws IOException
    {
        String answer = getAnswer();

        print(recieveString + answer);
        return answer.startsWith("220 ");
    }

    public boolean isAnswerReadyForData() throws IOException
    {
        String answer = getAnswer();

        print(recieveString + answer);
        return answer.startsWith("354 ");
    }

    public boolean isAnswerClosing() throws IOException
    {
        String answer = getAnswer();

        print(recieveString + answer);
        return answer.startsWith("221 ");
    }

    private String getAnswer() throws IOException
    {
        return socket.receiveLine() + "";
    }

    // private String getAnswers() throws IOException
    // {
    // StringBuilder sb = new StringBuilder();
    // String s = null;
    // while ((s = socket.receiveLine()) != null)
    // {
    // sb.append(s + newLine);
    // }
    // return sb.toString();
    // }

    private void print(String message)
    {
        if (showAnswer)
        {
            System.out.println(message);
        }
    }

}
