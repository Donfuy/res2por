/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import TCP.TCPReceive;
import TCP.TCPSend;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pinto
 */
public class MessageHandler implements Runnable {
    public static void askFile(String filename, String ip) throws IOException {
        Socket cliSocket = new Socket(ip, 6000);
        ObjectOutputStream ooS = new ObjectOutputStream(cliSocket.getOutputStream());
        ServerSocket serv = new ServerSocket(0);
        int randport = serv.getLocalPort();
        Object[] info = {filename,randport};
        ooS.writeObject(info);
        ooS.close();
        
        TCPReceive tcpc = new TCPReceive(filename,ip,randport);
        Thread tTCPReceive = new Thread(tcpc);
        tTCPReceive.start();
    }
    
    public static void receiveRequest() throws IOException, ClassNotFoundException {
        ServerSocket serv = new ServerSocket(6000);
        Socket socket = serv.accept();
        ObjectInputStream oiS = new ObjectInputStream(socket.getInputStream());
        Object[] info = (Object[])oiS.readObject();
        oiS.close();

        TCPSend tcps = new TCPSend((String)info[0],(int)info[1],socket.getRemoteSocketAddress().toString());
        Thread tTCPSend = new Thread(tcps);
        tTCPSend.start();
    }

    @Override
    public void run() {
        try {
            receiveRequest();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
