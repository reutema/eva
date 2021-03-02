package rmi.list;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ListInterface extends Remote
{

    public void append(int element) throws RemoteException;

    public void print() throws RemoteException;

    public ListItem getFirstListItem() throws RemoteException;

    public int size() throws RemoteException;

}
