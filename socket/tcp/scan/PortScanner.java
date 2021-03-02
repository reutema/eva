package socket.tcp.scan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PortScanner extends Thread
{
    private InetAddress address;

    private int minPort, maxPort;

    private ArrayList<Integer> openPorts;

    public PortScanner(InetAddress address, int minPort, int maxPort, ArrayList<Integer> openPorts)
    {
        this.address = address;
        this.minPort = minPort;
        this.maxPort = maxPort;
        this.openPorts = openPorts;

        this.start();
    }

    @Override
    public void run()
    {

        for (int port = minPort; port <= maxPort; port++)
        {

            try (Socket socket = new Socket(address, port))
            {

                // System.out.println(Thread.currentThread().getName() + ", " +
                // port + ", connected = " + socket.isConnected());
                openPorts.add(port);

            }
            catch (IOException e)
            {
                // System.out.println("port " + port + " is closed");
            }

        }

    }

    public static void main(String[] args)
    {

        int parallelism = 5;
        if (args.length < 3)
        {
            System.out.println("parameters: <ip address> <min port> <max port> <parallelism (standard 5)>");
            return;
        }
        else if (args.length > 3)
        {
            parallelism = Integer.valueOf(args[3]);
        }

        try
        {
            InetAddress ip = InetAddress.getByName(args[0]);
            int minP = Integer.valueOf(args[1]);
            int maxP = Integer.valueOf(args[2]);
            int numberOfPortsPerThread = (maxP - minP) / parallelism;

            PSTWrapper[] listOfPSTWrapper = new PSTWrapper[parallelism];
            int minPort = minP;
            int maxPort;

            for (int i = 0; i < parallelism; i++)
            {

                maxPort = minPort + numberOfPortsPerThread;

                listOfPSTWrapper[i] = new PSTWrapper(ip, minPort, maxPort);

                minPort = maxPort + 1;
            }

            StringBuilder sb = new StringBuilder("open ports for " + ip + " in range from " + minP + " to " + maxP);
            for (int i = 0; i < parallelism; i++)
            {
                try
                {
                    listOfPSTWrapper[i].getPsThread().join();
                    listOfPSTWrapper[i].getListOfOpenPorts().forEach(e -> sb.append(e + ", "));
                }
                catch (InterruptedException e1)
                {
                    e1.printStackTrace();
                }
            }

            System.out.println(sb.toString());

        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        System.out.println("PortScanner finished");
    }

}
