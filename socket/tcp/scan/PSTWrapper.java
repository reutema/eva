package socket.tcp.scan;

import java.net.InetAddress;
import java.util.ArrayList;

public class PSTWrapper
{

    private PortScanner psThread;

    private InetAddress address;

    private int minPort, maxPort;

    private ArrayList<Integer> listOfOpenPorts;

    public PSTWrapper(InetAddress address, int minPort, int maxPort)
    {
        this.address = address;
        this.minPort = minPort;
        this.maxPort = maxPort;

        this.listOfOpenPorts = new ArrayList<Integer>();
        this.psThread = new PortScanner(address, minPort, maxPort, listOfOpenPorts);

    }

    public PortScanner getPsThread()
    {
        return psThread;
    }

    public InetAddress getAddress()
    {
        return address;
    }

    public int getMinPort()
    {
        return minPort;
    }

    public int getMaxPort()
    {
        return maxPort;
    }

    public ArrayList<Integer> getListOfOpenPorts()
    {
        return listOfOpenPorts;
    }

}
