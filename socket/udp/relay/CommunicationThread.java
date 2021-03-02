package socket.udp.relay;

import java.io.IOException;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class CommunicationThread extends Thread
{

    private DatagramSocket inSocket;

    private DatagramSocket outSocket;

    private InetAddress srcAddress;

    private int srcPort;

    private InetAddress dstAddress;

    private int dstPort;

    private DatagramPacket answer;

    private Reader controlReader;

    public CommunicationThread(DatagramSocket inSocket, InetAddress srcAddress, int srcPort, InetAddress dstAddress, int dstPort, int packetSize, Reader controlReader)
    {
        System.out.println("start new CommunicationThread for " + dstAddress + ":" + dstPort);
        this.inSocket = inSocket;

        // address and port of server
        this.dstAddress = dstAddress;
        this.dstPort = dstPort;

        // address and port of client
        this.srcAddress = srcAddress; // to reply
        this.srcPort = srcPort; // to reply

        byte[] buffer = new byte[packetSize];
        this.answer = new DatagramPacket(buffer, buffer.length);
        this.controlReader = controlReader;

        try
        {
            this.outSocket = new DatagramSocket();
        }
        catch (SocketException e)
        {
            System.out.println("ERROR CommunicationThread.Constructor, new DatagramSocket(): " + e.getMessage());
        }

        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run()
    {

        while (true)
        {
            // InetAddress myAddress = socket.getLocalAddress();
            // int myPort = socket.getLocalPort();

            // set new address and port of destination

            // creates new DatagramSocket for communication for send and receive
            // from server and answer client
            try
            {

                outSocket.receive(answer);
                System.out.println(Thread.currentThread() + "|" + dstAddress + ":" + dstPort + " recieve answer from server");

                try
                {

                    char[] cBuffer = new char[100];
                    controlReader.read(cBuffer);
                    // char input = (char) controlReader.read();
                    char input = cBuffer[0];
                    System.out.println("input = " + input);

                    // when input is 'j', continue without sending to server
                    if (input == '!')
                    {
                        System.out.println("shutdown");
                        break;
                    }
                    else if (input != 'j')
                    {
                        System.out.println("request deleted");
                        continue;
                    }

                }
                catch (IOException e)
                {
                    System.out.println("ERROR UDPRelay, Reader: " + e.getMessage());
                }

                answer.setAddress(srcAddress);
                answer.setPort(srcPort);

                inSocket.send(answer);
                System.out.println(Thread.currentThread() + "|" + dstAddress + ":" + dstPort + " send answer to client");

            }
            catch (IOException e)
            {
                System.out.println("ERROR CommunicationThread: " + e.getMessage());
            }
        }

    }

    public void sendToServer(DatagramPacket request)
    {
        request.setAddress(dstAddress);
        request.setPort(dstPort);

        try
        {
            this.outSocket.send(request);
            System.out.println(Thread.currentThread() + "|" + dstAddress + ":" + dstPort + " send request to server");
        }
        catch (IOException e)
        {
            System.out.println("ERROR UserInputThread/CommunicationThread.send(request): " + e.getMessage());
        }
    }

}
