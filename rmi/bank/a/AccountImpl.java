package rmi.bank.a;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountImpl extends UnicastRemoteObject implements Account
{
    /**
     * 
     */
    private static final long serialVersionUID = -1969901503094305711L;

    private double money;

    public AccountImpl() throws RemoteException
    {
        this(0);
    }

    public AccountImpl(double startValue) throws RemoteException
    {
        this.money = startValue;
    }

    @Override
    public synchronized double readBalance() throws RemoteException
    {
        return money;
    }

    @Override
    public synchronized void changeBalance(double value) throws RemoteException
    {
        money += value;
    }

}
