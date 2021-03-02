package rmi.bank.b;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server
{

    public static final String OBJECTNAMEFORAANDB = "Konto";

    public Server(int rmiPort) throws RemoteException, MalformedURLException, AlreadyBoundException
    {

        for (int i = 0; i < 100; i++)
        {
            String objectName = OBJECTNAMEFORAANDB + i;
            AccountImpl account = new AccountImpl();
            account.setCreditLimit(0);
            LocateRegistry.getRegistry(rmiPort).bind(objectName, account);
        }
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException
    {
        new Server(6000);
    }

}
