package rmi.list;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AppendImpl extends UnicastRemoteObject implements Append
{

    /**
     * 
     */
    private static final long serialVersionUID = 6854047681769422766L;

    public AppendImpl() throws RemoteException
    {

    }

    @Override
    public void append(ListInterface list) throws RemoteException
    {
        list.append(4711);
    }

}
