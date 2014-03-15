package hexNations.network;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMethods extends Remote
	{
		public void testSendMessate(String message);
		
		/**
		 * This method can be called to see if the stub reference for this Object is still valid.
		 * <p>
		 * If it isn't then a new stub will need to be retrieved from via {@link Naming#lookup(String)}
		 * 
		 * @throws RemoteException - If the Stub reference used to call this remote method is no longer valid
		 */
		public void checkAlive() throws RemoteException;
	}
