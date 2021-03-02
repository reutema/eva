package socket.tcp.counter;

import socket.tcp.TCPSocket;

public class Client
{
    public static void main(String args[])
    {
        if (args.length < 3)
        {
            System.out.println("usage: java " + "socket.tcp.counter.Client " + "<server> <port> <count>");
            return;
        }

        int port = Integer.valueOf(args[1]);

        // create socket connection
        try (TCPSocket tcpSocket = new TCPSocket(args[0], port))
        {
            // set counter to zero
            System.out.println("setting counter to 0");
            tcpSocket.sendLine("reset");
            String reply = tcpSocket.receiveLine();
            // get count, initialize start time
            System.out.println("incrementing");
            int count = new Integer(args[2]).intValue();
            long startTime = System.currentTimeMillis();
            // perform increment "count" number of times
            for (int i = 0; i < count; i++)
            {
                tcpSocket.sendLine("increment");
                reply = tcpSocket.receiveLine();
            }

            // display statistics
            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            System.out.println("elapsed time = " + duration + " msecs");
            if (count > 0)
            {
                System.out.println("average ping = " + +((duration) / (float) count) + " msecs");
            }
            System.out.println("counter = " + reply);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("TCP connection closed");
    }
}