package rmi.mult;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Multiply extends Remote
{

    public int mult(int a, int b) throws RemoteException;

}
