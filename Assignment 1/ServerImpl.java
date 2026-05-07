import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf{
    public ServerImpl() throws RemoteException{

    }
    
    // this function changes according to the problem statement. Refer the Assignment_1.pdf
    // to know what exactly to put inside the function. Just change the retun statement.
    public double Powert(double num1) throws RemoteException{
        return Math.pow(2,num1);
    }
}