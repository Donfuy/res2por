/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Run;

import Multicast.MulticastClient;
import Tools.*;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;

/**
 *
 * @author Pinto
 */
public class Run {
    public static void main(String[] args) throws SocketException, IOException {
        //new MulticastServer().Run();
                
        File globalDeleter = new File(ListHandler.getGLOBAL_LIST_FILENAME());
        globalDeleter.delete();
        globalDeleter = new File(ListHandler.getPATH_FILENAME());
        globalDeleter.delete();
        
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

        globalDeleter.deleteOnExit();
        globalDeleter = new File(ListHandler.getGLOBAL_LIST_FILENAME());
        globalDeleter.deleteOnExit();
//        new MulticastClient().Run();
        
    }
}
