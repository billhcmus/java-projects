package vn.zalopay.hack.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by thuyenpt Date: 2020-04-02
 */
public interface RemoteInterface extends Remote {
  int sum(int num1, int num2) throws RemoteException;
}
