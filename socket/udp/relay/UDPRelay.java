package socket.udp.relay;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class UDPRelay
{
    // private static ConcurrentHashMap<, V>

    /**
     * Methode zum Starten des UDP-Relays.
     * 
     * @param inSocket
     *            {@link DatagramSocket}, über das Datagramme zum Weiterleiten
     *            empfangen werden sollen.
     * @param addressToRelayTo
     *            Adresse des Rechners, an den die über inSocket empfagenen
     *            Datagramme weitergeleitet werden sollen.
     * @param portToRelayTo
     *            Portnummer, an die die über inSocket empfagenen Datagramme
     *            weitergeleitet werden sollen.
     * @param controlReader
     *            {@link Reader}, von dem gelesen werden soll, ob ein über
     *            inSocket empfangenes Datagramm weitergeleitet oder verworfen
     *            werden soll.
     * @throws IOException
     *             Wird ausgelöst, wenn es bei der UDP-Kommunikation zu einem
     *             Ein-/Ausgabefehler kommt.
     */
    public static void runUDPRelay(DatagramSocket inSocket, InetAddress addressToRelayTo, int portToRelayTo, Reader controlReader) throws IOException
    {
        // Hier ergänzen...

        int maxBytes = 100;
        // ConcurrentLinkedQueue<DatagramPacket> queue = new
        // ConcurrentLinkedQueue<DatagramPacket>();

        // new RecieverThread(inSocket, maxBytes, queue);

        // new UserInputThread(inSocket, addressToRelayTo, portToRelayTo,
        // controlReader, queue, maxBytes);

        ConcurrentHashMap<SocketAddress, CommunicationThread> map = new ConcurrentHashMap<SocketAddress, CommunicationThread>();

        byte[] buffer = new byte[maxBytes];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);

        while (true)
        {

            inSocket.receive(request);

            InetAddress srcAddress = request.getAddress();
            int srcPort = request.getPort();

            // gets request from queue
            // DatagramPacket request = queue.poll();
            System.out.println("\nRequest from: " + srcAddress + ":" + srcPort + ", do you want to forward it? [j/n]");

            // read user input for deleting, no action if 'j', break if '!',
            // else redirect to server
            try
            {

                char[] cBuffer = new char[100];
                controlReader.read(cBuffer);
                // char input = (char) controlReader.read();
                char input = cBuffer[0];
                System.out.println("input = " + input);

                // when input is 'j', continue without sending to server
                if (input == '!')
                {
                    System.out.println("shutdown");
                    break;
                }
                else if (input != 'j')
                {
                    System.out.println("request deleted");
                    continue;
                }

            }
            catch (IOException e)
            {
                System.out.println("ERROR UDPRelay, Reader: " + e.getMessage());
            }
            System.out.println("sending request...");

            // creates new CommunicationThread-Object for send request; receive
            // and send answer

            SocketAddress srcSocketAddress = request.getSocketAddress();

            if (!map.containsKey(srcSocketAddress))
            {
                System.out.println("add client to list");

                CommunicationThread cThread = new CommunicationThread(inSocket, srcAddress, srcPort, addressToRelayTo, portToRelayTo, maxBytes, controlReader);
                map.put(srcSocketAddress, cThread);
            }

            map.get(srcSocketAddress).sendToServer(request);

            System.out.println("\n");

        }

        System.out.println("shutdown UDPRelay...");

    }

    /**
     * Hauptprogramm. Als Kommandozeilenargumente sollen die Portnummer für den
     * Empfang von Datagrammen sowie Adresse und Port für die Weiterleitung der
     * empfangenen Datagramme angegeben werden.
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("starting UDPRelay-Server");
        if (args.length != 3)
        {
            throw new IllegalArgumentException("usage: java " + "socket.udp.Relay " + "<src port> <server> <dst port server>");
        }

        int inPort = Integer.parseInt(args[0]);
        InetAddress addressToRelayTo = InetAddress.getByName(args[1]);
        int portToRelayTo = Integer.parseInt(args[2]);
        Reader controlReader = new InputStreamReader(System.in);
        try (DatagramSocket dgSocket = new DatagramSocket(inPort))
        {
            System.out.println("ip-address: " + dgSocket.getLocalAddress() + ", port: " + dgSocket.getLocalPort());
            UDPRelay.runUDPRelay(dgSocket, addressToRelayTo, portToRelayTo, controlReader);
        }
    }
}
