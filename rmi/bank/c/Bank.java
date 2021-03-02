package rmi.bank.c;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote
{

    public Account getAccount(int numb) throws RemoteException;

}
