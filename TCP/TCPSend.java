/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import Tools.ListHandler;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPSend implements Runnable {

    private final String filename;
    private final int port;
    private final Socket socket;
  
  
    public TCPSend(String filename, Socket socket, int port) {
        this.filename = filename;
        this.port = port;
        this.socket = socket;
    }

    @Override
    public void run() {
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            try {
                System.out.println("Accepted connection : " + socket);
                // send file
                File myFile = new File (ListHandler.getPATH_FILENAME() + filename);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = socket.getOutputStream();
                System.out.println("Sending " + filename + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("Done.");
            } finally {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (socket!=null) socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//http://www.rgagnon.com/javadetails/java-0542.html