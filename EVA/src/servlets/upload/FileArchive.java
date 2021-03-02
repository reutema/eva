package servlets.upload;

import java.util.HashMap;

public class FileArchive
{

    private final String seperator = "|/|";

    private HashMap<String, StringBuffer> map;

    public FileArchive()
    {
        map = new HashMap<String, StringBuffer>();
    }

    public synchronized void add(String filename, StringBuffer file)
    {
        if (map.containsKey(filename))
        {
            filename = filename + seperator + file.hashCode();
        }
        map.put(filename, file);
    }

    public synchronized StringBuffer get(String filename)
    {
        return map.get(filename);
    }

    public synchronized String getListOfFiles()
    {
        StringBuffer buffer = new StringBuffer();
        map.forEach((k, v) -> buffer.append(" <a href=\"" + v + "\">" + k + "</a> "));
        return buffer.toString();
    }
}
