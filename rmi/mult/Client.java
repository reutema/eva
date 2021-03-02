package rmi.mult;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Client
{

    private Multiply multiply;

    public Client(int rmiPort) throws MalformedURLException, RemoteException, NotBoundException
    {

        this.multiply = (Multiply) LocateRegistry.getRegistry(rmiPort).lookup(Server.OBJECTNAME);

        multiply(0, 10);

    }

    private void multiply(int from, int to) throws RemoteException
    {
        for (int i = from; i < to; i++)
        {
            for (int j = from; j < to; j++)
            {
                System.out.println(multiply.mult(i, j));
            }
        }
    }

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
    {
        new Client(10000);
    }

}
