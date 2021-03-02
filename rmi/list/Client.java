package rmi.list;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Client
{

    public Client(int rmiPort) throws MalformedURLException, RemoteException, NotBoundException
    {

        Append ap = (Append) LocateRegistry.getRegistry(rmiPort).lookup(Server.OBJECTNAME);
        List list = new List();
        ap.append(list);

        list.print();

    }

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
    {
        new Client(10000);
    }

}
