package rmi.bank.c;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class BankImpl extends UnicastRemoteObject implements Bank
{

    /**
     * 
     */
    private static final long serialVersionUID = 8823427610234327109L;

    private ArrayList<Account> accounts = new ArrayList<Account>();

    public BankImpl() throws RemoteException
    {
        for (int i = 0; i < 100; i++)
        {
            addAccount(new AccountImpl());
        }
    }

    @Override
    public Account getAccount(int numb) throws RemoteException, IllegalArgumentException
    {
        if (numb < 0 || numb >= accounts.size())
        {
            throw new IllegalArgumentException();
        }

        return accounts.get(numb);
    }

    public void addAccount(Account account)
    {
        this.accounts.add(account);
    }
}
