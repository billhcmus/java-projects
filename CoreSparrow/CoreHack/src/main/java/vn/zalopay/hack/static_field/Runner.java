package vn.zalopay.hack.static_field;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by thuyenpt
 * Date: 2020-04-01
 */
public class Runner {
  public static void main(String[] args) {
    Showable show1 = new Foo();
    Showable show2 = new Bar();
    show1.changeId(100);

    show1.show();
    show2.show();

    getCurrentIp();
  }

  private static void getCurrentIp() {
    try(final DatagramSocket socket = new DatagramSocket()){
      socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
      String ip = socket.getLocalAddress().getHostAddress();
      System.out.println(ip);
    } catch (UnknownHostException | SocketException e) {
      e.printStackTrace();
    }
  }
}
