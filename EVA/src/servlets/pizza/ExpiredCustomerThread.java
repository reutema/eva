package servlets.pizza;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiredCustomerThread extends Thread
{

    private ConcurrentHashMap<String, Customer> map;

    private int[] timeToWait;

    public ExpiredCustomerThread(ConcurrentHashMap<String, Customer> map, int... timeToWait)
    {
        this.map = map;
        this.timeToWait = timeToWait;

        // Thread config
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run()
    {

        int i = 0;

        while (true)
        {
            try
            {
                sleep(timeToWait[i]);
                i = (i + 1) % timeToWait.length;

                Enumeration<String> keys = map.keys();

                while (keys.hasMoreElements())
                {
                    String key = keys.nextElement();
                    Customer customer = map.get(key);
                    if (customer.getCookieExpireDate() < System.currentTimeMillis())
                    {
                        map.remove(key);
                    }
                }

            }
            catch (Exception e)
            {
            }
        }

    }

}
