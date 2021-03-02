package socket.tcp.mail;

import java.io.IOException;
import java.util.Arrays;

import socket.tcp.TCPSocket;

public class SendMail
{
    private String mailServer;

    private int port;

    private String sendingHost;

    private String mailFrom;

    private String mailTo;

    public SendMail(String mailServer, int port, String sendingHost, String mailFrom, String mailTo)
    {
        this.mailServer = mailServer;
        this.port = port;
        this.sendingHost = sendingHost;
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;

    }

    public void send(String message, int numberOfMails)
    {

        try (TCPSocket socket = new TCPSocket(mailServer, port))
        {
            SMTP smtp = new SMTP(socket, numberOfMails > 1);
            if (smtp.isAnswerReadyForMail())
            {
                smtp.hello(sendingHost);
                if (smtp.isAnswerOK())
                {
                    for (int i = 0; i < numberOfMails; i++)
                    {
                        System.out.println("\nSend Mail " + (i + 1) + "/" + numberOfMails);

                        smtp.from(mailFrom);

                        if (smtp.isAnswerOK())
                        {
                            smtp.to(mailTo);

                            if (smtp.isAnswerOK())
                            {
                                smtp.data();

                                if (smtp.isAnswerReadyForData())
                                {
                                    smtp.sendData(message);

                                    if (smtp.isAnswerOK())
                                    {

                                        System.out.println("\n--> --Mail " + (i + 1) + "/" + numberOfMails + " send--\n");
                                        continue;

                                    }
                                    else
                                    {
                                        printError("sending data");
                                    }
                                }
                                else
                                {
                                    printError("data");
                                }
                            }
                            else
                            {
                                printError("mailTo");
                            }
                        }
                        else
                        {
                            printError("mailFrom");
                        }
                    } // end of for-loopelse
                }
                else
                {
                    printError("sendingHost");
                }
            }
            else
            {
                printError("not ready for mail");
            }
            smtp.quit();

            if (smtp.isAnswerClosing())
            {
            }
            else
            {
                printError("quit");
            }

        } // end of try
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void printError(String message)
    {
        System.out.println("problem with " + message);
    }

    public static void main(String[] args)
    {
        if (args.length < 7)
        {
            System.out.println("parameters: <mail server> <mail server port> <sending host> <from eMail> <to eMail> <message> <number of mails>");
            return;
        }

        System.out.println("starting SendMail with " + Arrays.toString(args));

        String mailServer = args[0];
        int port = Integer.valueOf(args[1]);
        String sendingHost = args[2];
        String mailFrom = args[3];
        String mailTo = args[4];
        String message = args[5];
        int numberOfMails = Integer.valueOf(args[6]);

        SendMail mail = new SendMail(mailServer, port, sendingHost, mailFrom, mailTo);

        mail.send(message, numberOfMails);
        System.out.println("end SendMail\n\n");
    }

}
