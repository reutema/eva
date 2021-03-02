package rmi.bank.c;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Client
{

    public Client(int rmiPort, String[] field) throws AccessException, RemoteException, NotBoundException, OverdrawAccountException
    {
        if (field.length < 2)
        {
            throw new IllegalArgumentException("<l - lesen / s - schreiben> <konto> <wenn s -- value>");
        }

        int number = Integer.valueOf(field[1]);
        if (number < 0 || number >= 100)
        {
            throw new IllegalArgumentException();
        }

        Bank bank = (Bank) LocateRegistry.getRegistry(rmiPort).lookup(Server.OBJECTNAMEFORC);

        int accountNumber = Integer.valueOf(field[1]);
        Account account = bank.getAccount(accountNumber);

        if (field[0].equals("l"))
        {
            // read/lesen

            System.out.println(account.readBalance());

        }
        else if (field[0].equals("s"))
        {
            // write/schreiben

            double value = Double.valueOf(field[2]);
            account.changeBalance(value);
        }
    }

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
    {
    }

}
