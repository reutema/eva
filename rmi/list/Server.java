package rmi.list;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{

    public static final String OBJECTNAME = "AppendServer";

    public Server(int rmiPort) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        AppendImpl appendImpl = new AppendImpl();

        LocateRegistry.getRegistry(rmiPort).bind(OBJECTNAME, appendImpl);
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        new Server(6000);
    }

}
