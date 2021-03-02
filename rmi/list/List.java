package rmi.list;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class List extends UnicastRemoteObject implements ListInterface, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -3873743772953376485L;

    private ListItem first, last;

    private int size;

    public List() throws RemoteException
    {

    }

    @Override
    public void append(int i)
    {
        if (first == null)
        {
            first = new ListItem(i);
            last = first;
        }
        else
        {
            last.setNext(new ListItem(i));
            last = last.getNext();
        }
        size++;
    }

    @Override
    public void print()
    {
        ListItem item = first;
        while (item != null)
        {
            System.out.print(item.getValue() + " ");
            item = item.getNext();
        }
        System.out.println();
    }

    @Override
    public ListItem getFirstListItem()
    {
        return first;
    }

    @Override
    public int size()
    {
        return size;
    }

}
