package vn.zalopay.hack.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by thuyenpt Date: 2020-04-02
 */
public class ClientRunner {
  public static void main(String[] args) {
    try {
      Remote lookup = Naming.lookup("rmi://localhost/sum");
      RemoteInterface remoteInterface = (RemoteInterface) lookup;
      int sum = remoteInterface.sum(5, 5);
      System.out.println("Result: " + sum);

    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      e.printStackTrace();
    }
  }
}
