package socket.udp.relay;

import java.io.IOException;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserInputThread extends Thread
{

    private DatagramSocket inSocket;

    private InetAddress addressToRelayTo;

    private int portToRelayTo;

    private Reader controlReader;

    private ConcurrentLinkedQueue<DatagramPacket> queue;

    private HashMap<SocketAddress, CommunicationThread> map;

    private int packetSize;

    public UserInputThread(DatagramSocket inSocket, InetAddress addressToRelayTo, int portToRelayTo, Reader controlReader, ConcurrentLinkedQueue<DatagramPacket> queue, int packetSize)
    {
        this.inSocket = inSocket;
        this.addressToRelayTo = addressToRelayTo;
        this.portToRelayTo = portToRelayTo;
        this.controlReader = controlReader;
        this.queue = queue;
        this.packetSize = packetSize;

        map = new HashMap<SocketAddress, CommunicationThread>();

        this.start();
    }

    @Override
    public void run()
    {

        while (true)
        {

            // is waiting while no DatagramPacket/request is in queue
            waitWhileQueueIsEmpty();

            // gets request from queue
            DatagramPacket request = queue.poll();
            System.out.println("Request from: " + request.getSocketAddress() + ", do you want to delete it? [j/n]");

            // read user input for deleting, no action if 'j', break if '!',
            // else redirect to server
            try
            {
                char input = (char) controlReader.read();

                // when input is 'j', continue without sending to server
                if (input == '!')
                {
                    break;
                }
                else if (input != 'j')
                {
                    continue;
                }
            }
            catch (IOException e)
            {
                System.out.println("ERROR CommunicationThread: " + e.getMessage());
            }

            // creates new CommunicationThread-Object for send request; receive
            // and send answer

            SocketAddress srcSocketAddress = request.getSocketAddress();

            if (map.containsKey(srcSocketAddress))
            {

                map.get(srcSocketAddress).sendToServer(request);

            }
            else
            {
                InetAddress srcAddress = request.getAddress();
                int srcPort = request.getPort();

                CommunicationThread cThread = new CommunicationThread(inSocket, srcAddress, srcPort, addressToRelayTo, portToRelayTo, packetSize, controlReader);
                map.put(srcSocketAddress, cThread);
            }
        }

        System.out.println("shutdown UDPRelay...");

    }

    private synchronized void waitWhileQueueIsEmpty()
    {
        while (queue.isEmpty())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                System.out.println("ERROR UserInputThread.waitWhileQueueIsEmpty, waiting: " + e.getMessage());
            }
        }
    }
}
