package vn.zalopay.hack.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by thuyenpt Date: 2020-04-02
 */
public class ServerRunner {
  public static void main(String[] args) {
    try {
      ServerRMIHandler handler = new ServerRMIHandler();
      LocateRegistry.createRegistry(1099);
      Naming.rebind("rmi://localhost/sum", handler);
      System.out.println("Server listening at port " + 1099);
    } catch (RemoteException | MalformedURLException e) {
      e.printStackTrace();
    }
  }
}
