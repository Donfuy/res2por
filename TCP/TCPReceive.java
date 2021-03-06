/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

/**
 *
 * @author zeze
 */
import Tools.ListHandler;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPReceive implements Runnable {
    public final static int MAX_FILE_SIZE = 6022386;  // file size temporary hard coded
                                                    // should be bigger than the file to be downloaded

    private final String filename;
    private final int port;
  
    public TCPReceive(String filename, int port) {
        this.filename = filename;
        this.port = port;
    }

    @Override
    public void run() {
        int bytesRead;
        int current;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ServerSocket servsock;
        Socket sock = null;
        try {
            servsock = new ServerSocket(port);
            sock = servsock.accept();
            System.out.println("Connecting...");

            // receive file
            byte [] mybytearray  = new byte [MAX_FILE_SIZE];

            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(ListHandler.getPath() + filename);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                  is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + filename
                + " downloaded (" + current + " bytes read)");
        } catch (IOException | ClassNotFoundException ex) {
              Logger.getLogger(TCPReceive.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fos != null && bos != null && sock != null) {
                try {
                    fos.close();
                    bos.close();
                    sock.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPReceive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}