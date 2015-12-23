/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.net.InetAddress;

/**
 *
 * @author José Pinto
 */
public class Ping {

    public static boolean PingTest(String ip) {

        try {
            InetAddress address = InetAddress.getByName(ip);
            boolean reachable = address.isReachable(10000);

            return reachable;

        } catch (Exception e) {
            return false;
        }

    }
}
