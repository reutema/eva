package rmi.bank.c;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AccountImpl extends UnicastRemoteObject implements Account
{
    /**
     * 
     */
    private static final long serialVersionUID = -1969901503094305711L;

    private double money;

    private double creditLimit = Double.MIN_VALUE;

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
        // TODO Auto-generated method stub
        return money;
    }

    @Override
    public synchronized void changeBalance(double value) throws RemoteException, OverdrawAccountException
    {
        if ((money + value) < creditLimit)
        {
            throw new OverdrawAccountException();
        }

        money += value;
    }

    public void setCreditLimit(double creditLimit)
    {
        this.creditLimit = creditLimit;
    }

}
