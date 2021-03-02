package rmi.mult;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MultiplyImpl extends UnicastRemoteObject implements Multiply
{

    /**
     * 
     */
    private static final long serialVersionUID = -4267435847288165787L;

    public MultiplyImpl() throws RemoteException
    {
        super();
    }

    @Override
    public int mult(int a, int b) throws RemoteException
    {
        return a * b;
    }

}
