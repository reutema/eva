package socket.tcp.counter;

import java.io.IOException;
import java.net.ServerSocket;

import socket.tcp.TCPSocket;

public class Server
{
    public static void runServer(ServerSocket socket)
    {

        int counter = 0;
        // create socket
        // wait for connection then create streams
        System.out.println("waiting for " + "client connection");
        while (true)
        {
            try (TCPSocket tcpSocket = new TCPSocket(socket.accept()))
            {
                // execute client requests
                while (true)
                {
                    String request = tcpSocket.receiveLine();
                    if (request != null)
                    {
                        if (request.equals("increment"))
                        {
                            // increment operation
                            counter++;
                        }
                        else if (request.equals("decrement"))
                        {
                            // decrement operation
                            counter--;
                        }
                        else if (request.equals("reset"))
                        {
                            // reset operation
                            counter = 0;
                            System.out.println("counter has " + "been reset");
                        }
                        else if (request.startsWith("set "))
                        {
                            try
                            {
                                // return int of string from request beginning
                                // at index 3
                                int value = Integer.valueOf(request.substring(4));

                                // set operation
                                counter = value;
                            }
                            catch (NumberFormatException nfe)
                            {
                                System.out.println("Server, can't set request: " + request + "\n" + nfe.getMessage());
                            }
                        }
                        String result = String.valueOf(counter);
                        tcpSocket.sendLine(result);
                    }
                    else
                    {
                        System.out.println("closing " + "connection");
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        } // while(true)
    }

    public static void main(String[] args) throws IOException
    {
        if (args.length < 1)
        {
            System.out.println("parameter: <port>");
            return;
        }

        int port = Integer.valueOf(args[0]);

        try (ServerSocket sock = new ServerSocket(port))
        {
            runServer(sock);
        }

    } // main
} // class