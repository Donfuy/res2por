/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Multicast;

import Tools.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

/**
 *
 * @author Pinto
 */
public class demo {
    public static void main(String[] args) throws SocketException, IOException {
        //new MulticastServer().run();
        
        MulticastClient cliente = new MulticastClient();
        Menu menu = new Menu();
        Thread tclient = new Thread(cliente);
        
        try {
            new ListHandler().getPath();
        } catch (IOException | ClassNotFoundException ex) {
            new ListHandler().setPath();
        }
        
        Thread tmenu = new Thread(menu);
        tclient.start(); //inicia a thread cliente 
        tmenu.start();
        
//        new MulticastClient().run();
        
    }
}
