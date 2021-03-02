package socket.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSocket implements AutoCloseable
{
    private Socket socket;

    private BufferedReader istream;

    private BufferedWriter ostream;

    public TCPSocket(String serverAddress, int serverPort) throws UnknownHostException, IOException
    {
        socket = new Socket(serverAddress, serverPort);
        initializeStreams();
    }

    public TCPSocket(Socket socket) throws IOException
    {
        this.socket = socket;
        initializeStreams();
    }

    public void sendLine(String s) throws IOException
    {
        ostream.write(s);
        ostream.newLine();
        ostream.flush();
    }

    public String receiveLine() throws IOException
    {
        return istream.readLine();
    }

    @Override
    public void close() throws IOException
    {
        socket.close();
    }

    private void initializeStreams() throws IOException
    {
        ostream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        istream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}