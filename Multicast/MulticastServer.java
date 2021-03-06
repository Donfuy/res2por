/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;
import java.net.*;
import Tools.*;
import java.io.IOException;

/**
 *
 * @author xfce
 */
public class MulticastServer implements Runnable {

    @Override
    public void run() {
        int port = 5000;
        String group ="225.4.5.6";
        
        try {   
            MulticastSocket s;
            s = new MulticastSocket();    
            ListHandler handler = new ListHandler();
      
            // Cria ficheiro globalList contendo só a sua lista local
            try {
                ListHandler.getGlobalFileList();
            } catch (IOException ex) {
                handler.localToGlobalFileList(); 
            }
 
            // Transforma a lista local num array de bytes para poder ser
            // transferido num packet.
            byte[] list;

            list = handler.getByteFileList();

        
            // Manda a lista para todo o grupo  
            DatagramPacket packet;

            packet = new DatagramPacket(list, list.length, InetAddress.getByName(group), port);
            s.send(packet);
            s.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }   
}    

