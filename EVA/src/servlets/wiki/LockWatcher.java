package servlets.wiki;

import javax.servlet.http.HttpSession;

public class LockWatcher extends Thread
{
    private HttpSession session;

    private WikiArticle article;

    private int timeToSleep;

    public LockWatcher(HttpSession session, WikiArticle article, int secondsToSleep)
    {
        this.session = session;
        this.article = article;
        this.timeToSleep = secondsToSleep * 1000;

        this.setDaemon(true);
        this.start();
    }

    public void run()
    {
        long finishTime = System.currentTimeMillis() + timeToSleep;
        long remainingTime = 0;
        while ((remainingTime = finishTime - System.currentTimeMillis()) > 0)
        {

            try
            {
                sleep(remainingTime);
            }
            catch (Exception e)
            {
            }

        }
        article.removeSessionLock(session);
        try
        {
            session.invalidate();
        }
        catch (Exception e)
        {

        }

    }

}
