package hexNations.game;

import java.rmi.RemoteException;

import tools.WindowTools;
import hexNations.Main;
import hexNations.network.RemoteMethods;

public class Game implements RemoteMethods
	{
		public Game()
			{
				try
					{
						Main.server.addClient(this);
					}
				catch (RemoteException e)
					{
						if (!Main.DEBUG)
							WindowTools.debugWindow("Failed to Connect to Server");
						e.printStackTrace();
					}
			}

		@Override
		public void addClient(RemoteMethods client) throws RemoteException
			{
				System.out.println("Server only method called in client");
			}

		@Override
		public void sendTestMessage(String message) throws RemoteException
			{
				System.out.println("Server Says: " + message);
			}

		@Override
		public void checkAlive() throws RemoteException
			{}
	}