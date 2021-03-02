package socket.udp.relay;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RecieverThread extends Thread
{

    private DatagramSocket socket;

    private ConcurrentLinkedQueue<DatagramPacket> queue;

    private int maxBytes;

    public RecieverThread(DatagramSocket socket, int maxBytes, ConcurrentLinkedQueue<DatagramPacket> queue)
    {
        this.socket = socket;
        this.maxBytes = maxBytes;
        this.queue = queue;
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run()
    {

        while (true)
        {

            // creates new DatagramPacket for receive
            byte[] buffer = new byte[maxBytes];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);

            // receive request, add it to queue and notify for UserInputThread
            try
            {
                socket.receive(request);
                queue.add(request);
                notify();
            }
            catch (IOException e)
            {
                System.out.println("Error RecieverThread: " + e.getMessage());
            }

        }

    }
}
