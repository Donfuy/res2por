package Tools;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Util{

    /**
     * Finds a local, non-loopback, IPv4 address
     * 
     * @return The first non-loopback IPv4 address found, or
     *         <code>null</code> if no such addresses found
     * @throws SocketException
     *            If there was a problem querying the network
     *            interfaces
     */
    public static InetAddress getLocalAddress() throws SocketException
    {
      Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
      while( ifaces.hasMoreElements() )
      {
        NetworkInterface iface = ifaces.nextElement();
        Enumeration<InetAddress> addresses = iface.getInetAddresses();

        while( addresses.hasMoreElements() )
        {
          InetAddress addr = addresses.nextElement();
          if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
          {
            return addr;
          }
        }
      }

      return null;
    }
  
    public static String GetIp() throws SocketException{
        Util ip = new Util();

          InetAddress addr;

          addr=getLocalAddress();
          String Ip=addr.toString();
          String ipfinal= Ip.substring(1);

          return ipfinal;
    }
  
    /** Transforms a byte array into a string array
     * 
     * @param recvBuf
     * @return String[] taken from the byte array.
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static String[] byteArrayToStringArray(byte[] recvBuf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream baiS = new ByteArrayInputStream(recvBuf);
        ObjectInputStream oiS = new ObjectInputStream(new BufferedInputStream(baiS));
        String[] sArray = (String[]) oiS.readObject();

        return sArray;
}
}
