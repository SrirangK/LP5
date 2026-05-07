import java.rmi.*;

public interface ServerIntf extends Remote{
    public double Powert(double num1) throws RemoteException;
}