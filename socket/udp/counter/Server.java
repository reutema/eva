package socket.udp.counter;

import java.io.IOException;
import java.net.DatagramSocket;

public class Server
{

    private static int counter;

    public static void runServer(DatagramSocket socket)
    {

        // wait for request packets
        System.out.println("waiting for client requests");

        try (UDPSocket udpSocket = new UDPSocket(socket))
        {

            while (true)
            {

                // receive request
                String request = "";
                try
                {
                    request = udpSocket.receive(20);
                }
                catch (IOException io)
                {
                    System.out.println(io);
                }

                System.out.println(request);

                if (request.equals("increment"))
                {
                    // perform increment
                    counter++;
                    // System.out.println(
                    // "counter increment by " + udpSocket.getSenderAddress() +
                    // ":" + udpSocket.getSenderPort());
                }
                else if (request.equals("decrement"))
                {
                    counter--;
                }
                else if (request.equals("reset"))
                {
                    // perform reset
                    counter = 0;
                    System.out.println("counter reset by " + udpSocket.getSenderAddress() + ":" + udpSocket.getSenderPort());
                }
                else if (request.contains("set "))
                {
                    try
                    {
                        int value = new Integer(request.substring(request.indexOf(" ") + 1)).intValue();
                        counter = value;
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println(nfe);
                    }
                }

                // generate answer
                String answer = String.valueOf(counter);
                // send answer
                try
                {
                    udpSocket.reply(answer);
                }
                catch (IOException io)
                {
                    System.out.println(io);
                }

            }
        }
        catch (Exception e)
        {
            System.out.println("> " + e);
        }
        System.out.println("UDP socket closed");

    }

    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            throw new IllegalArgumentException("only one argument with port number allowed");
        }
        int port = Integer.parseInt(args[0]);
        try (DatagramSocket sock = new DatagramSocket(port))
        {
            runServer(sock);
        }
    }

}
