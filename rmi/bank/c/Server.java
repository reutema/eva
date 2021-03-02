package rmi.bank.c;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{

    public static final String OBJECTNAMEFORAANDB = "Konto";

    public static final String OBJECTNAMEFORC = "Bank";

    public Server(int rmiPort) throws RemoteException, MalformedURLException, AlreadyBoundException
    {

        BankImpl bank = new BankImpl();

        LocateRegistry.getRegistry(rmiPort).bind(OBJECTNAMEFORC, bank);

    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        new Server(6000);
    }

}
