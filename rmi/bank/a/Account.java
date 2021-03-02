package rmi.bank.a;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Account extends Remote
{
    public double readBalance() throws RemoteException;

    public void changeBalance(double value) throws RemoteException;

}
