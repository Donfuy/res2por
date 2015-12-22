/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import TCP.TCPClient;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Pinto
 */
public class MessageHandler {
    public static void askFile(String filename, String ip) throws IOException {
        Socket cliSocket = new Socket(ip, 6000);
        ObjectOutputStream ooS = new ObjectOutputStream(cliSocket.getOutputStream());
        ServerSocket serv = new ServerSocket(0);
        Object[] info = {filename,serv.getLocalPort()};
        ooS.writeObject(info);
        ooS.close();
        
        TCPClient tcpc = new TCPClient(filename,ip,serv.getLocalPort());
        Thread tTCPCli = new Thread(tcpc);
        tTCPCli.start();
    }
}
