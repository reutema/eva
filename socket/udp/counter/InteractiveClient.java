package socket.udp.counter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class InteractiveClient
{

    private static final int TIMEOUT = 2000; // 2 seconds

    public static void main(String args[])
    {
        if (args.length != 3)
        {
            System.out.println("usage: java " + "socket.udp.Client " + "<server> <port> <count>");
            return;
        }
        // create datagram socket
        try (UDPSocket udpSocket = new UDPSocket())
        {

            udpSocket.setTimeout(TIMEOUT);

            // get inet addr of server
            InetAddress serverAddr = InetAddress.getByName(args[0]);
            int port = new Integer(args[1]).intValue();

            // initialize start time
            long startTime = System.currentTimeMillis();
            int count = 0;

            while (true)
            {
                try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)))
                {

                    String line = "";
                    String reply = null;
                    do
                    {
                        System.out.println("Please enter next command... ");
                        line = stdIn.readLine();
                    }
                    while (!line.equals(""));

                    if (line.equals("reset"))
                    {

                        // set counter to zero
                        System.out.println("setting counter to 0");
                        udpSocket.send("reset", serverAddr, port);
                    }
                    else if (line.equals("increment"))
                    {

                        System.out.println("perform increment");
                        udpSocket.send("increment", serverAddr, port);
                    }
                    else if (line.equals("decrement"))
                    {

                        System.out.println("perform decrement");
                        udpSocket.send("decrement", serverAddr, port);
                    }
                    else if (line.contains("set "))
                    {

                        int value = new Integer(line.substring(line.indexOf("set ") + 1)).intValue();
                        System.out.println("perform set with " + value);
                        udpSocket.send("set " + value, serverAddr, port);
                    }
                    else if (line.equals("done") || line.equals("close") || line.equals("stop"))
                    {
                        break;
                    }
                    else if (line.equals("help"))
                    {
                        System.out.println("Commands:\nincrement\ndecrement\nset <value>\nreset\nhelp\ndone\nclose\nstop");
                    }
                    else
                    {
                        continue;
                    }

                    count++;

                    // receive reply
                    try
                    {
                        reply = udpSocket.receive(20);
                        System.out.println("counter = " + reply);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }

                }
            }

            // display statistics
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;

            System.out.println("elapsed time = " + duration + " msecs");

            if (count > 0)
            {
                System.out.println("average ping = " + ((duration) / (float) count) + " msecs");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("UDP socket closed");
    }

}
