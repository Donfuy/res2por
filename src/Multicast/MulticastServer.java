/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;
import java.net.*;
import Tools.*;

/**
 *
 * @author xfce
 */
public class MulticastServer implements Runnable {

    @Override
    public void run() {
        // port definido como 0 significa que escolhe uma porta que não esteja
        // ocupada automaticamente
        int port = 5000;
        String group ="225.4.5.6";
        String ip="192.168.59.5";
        
        try {
            InetAddress ip2 = InetAddress.getByName(ip);        
            MulticastSocket s;
            s = new MulticastSocket();    
            ListHandler handler = new ListHandler();
      
            // Cria ficheiro globalList contendo só a sua lista local
            handler.localToGlobalFileList();  
            // Transforma a lista local num array de bytes para poder ser
            // transferido num packet.
            byte[] list;

            list = handler.getByteFileList();

        
            // Manda a lista para todo o grupo  
            DatagramPacket packet;

            packet = new DatagramPacket(list, list.length, InetAddress.getByName(group), port);
            s.send(packet);
            s.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }   
}    

