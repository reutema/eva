package servlets.wiki;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

public class WikiArticle
{

    private String titleOfArticle;

    private StringBuffer article;

    private HttpSession sessionLock;

    private int timeout;

    public WikiArticle(String title, int timeoutInSeconds)
    {
        this.titleOfArticle = title;
        this.timeout = timeoutInSeconds;
        this.article = new StringBuffer();
    }

    public synchronized String getTitle()
    {
        return titleOfArticle;
    }

    public synchronized void setTitle(HttpSession session, String title)
    {
        if (isSessionLock(session))
        {
            this.titleOfArticle = title;
        }
    }

    public synchronized StringBuffer getArticleBuffer()
    {
        return article;
    }

    public synchronized String getArticle()
    {
        return article.toString();
    }

    public synchronized void printArticle(PrintWriter output)
    {
        String[] lines = article.toString().split("\n");
        for (String line : lines)
        {
            output.println("<p>" + line + "</p");
        }
    }

    public synchronized void setArticle(HttpSession session, String articleAsString)
    {
        if (isSessionLock(session))
        {
            this.article = new StringBuffer(articleAsString);
        }
    }

    public synchronized void setSessionLock(HttpSession session)
    {
        if (this.sessionLock == null)
        {
            this.sessionLock = session;
            new LockWatcher(session, this, timeout);
        }
    }

    public synchronized void removeSessionLock(HttpSession session)
    {
        if (isSessionLock(session))
        {
            this.sessionLock = null;
            // new LockWatcher(session, this, 0);
        }
    }

    public synchronized boolean isSessionLock(HttpSession session2)
    {
        return sessionLock != null && this.sessionLock.equals(session2);
    }
}
