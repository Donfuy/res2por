/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Pinto
 */
public class MessageHandler {
    public static void askFile(String filename) throws IOException {
        ServerSocket socket = new ServerSocket(0);
        Object[] info = {filename, socket.getLocalPort()};
        
        
    }
}
