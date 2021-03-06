/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;

import Tools.ListHandler;
import java.io.IOException;
import java.net.*;
import Tools.Util;

/**
 *
 * @author xfce
 */
public class MulticastClient implements Runnable {

    @Override
    public void run() {
        int port = 5000;
        String group = "225.4.5.6";
        
        new MulticastServer().run();
        
        try {
            MulticastSocket s = new MulticastSocket(port);

            s.joinGroup(InetAddress.getByName(group));

            byte buf[] = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            int i = 0;
            while(i == 0) {
                s.receive(packet);
                if (!packet.getAddress().toString().substring(1).equals(Util.GetIp())) {
                    new MulticastServer().run();
                    String[] recvList = Util.byteArrayToStringArray(packet.getData());
                    ListHandler.addToGlobalFileList(recvList, packet.getAddress().toString());
                }
            }
            s.leaveGroup(InetAddress.getByName(group));
            s.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
