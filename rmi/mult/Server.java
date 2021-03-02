package rmi.mult;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{

    public static final String OBJECTNAME = "MultiplyServer";

    public Server(int rmiPort) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        MultiplyImpl multiplyImpl = new MultiplyImpl();
        LocateRegistry.getRegistry(rmiPort).bind(OBJECTNAME, multiplyImpl);
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        new Server(6000);
    }

}
