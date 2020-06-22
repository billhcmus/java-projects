package vn.zalopay.hack.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by thuyenpt Date: 2020-04-02
 */
public class ServerRMIHandler extends UnicastRemoteObject implements RemoteInterface {
  public ServerRMIHandler() throws RemoteException {
    super();
  }
  @Override
  public int sum(int num1, int num2) {
    System.out.println(String.format("Server handle sum num1=%d, num2=%d", num1, num2));
    return num1 + num2;
  }
}
