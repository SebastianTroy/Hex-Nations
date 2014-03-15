package hexNations.network;

import hexNations.windows.GameWindow;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import remoteObject.TRemoteObject;

/**
 * This class is responsible for receiving actions from every player in the game and then passing them to every instance of {@link GameWindow} on the server and
 * client sides.
 * <p>
 * This class passes out actions in the order that it receives them and ensures that every player's instance of {@link GameWorld} is updated in the same way, in
 * the same order, thus maintaining continuity.
 * 
 * @author Sebastian Troy
 */
public class Server extends TRemoteObject implements RemoteMethods
	{
		private static final long serialVersionUID = 1L;

		private RemoteMethods[] clientGames;

		public Server(String serverName, int numPlayers) throws RemoteException
			{
				super(serverName);
				clientGames = new RemoteMethods[numPlayers];
			}

		@Override
		public void addClient(RemoteMethods client) throws RemoteException
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void sendTestMessage(String message) throws RemoteException
			{
				for (int i = 0; i < clientGames.length; i++)
					getClient(i).sendTestMessage(message);
			}

		@Override
		public void checkAlive() throws RemoteException
			{
				System.out.println("Pinged by Client");
			}

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		/**
		 * Used to retrieve stubs for client game instances. Guarantees a valid stub reference.
		 * <p>
		 * TODO check if this is at all necessary, ever.
		 * 
		 * @param playerNumber
		 * @return
		 */
		private final RemoteMethods getClient(int playerNumber)
			{
				try
					{
						// See if the reference is still valid
						clientGames[playerNumber].checkAlive();
					}
				catch (RemoteException e1)
					{
						System.out.println("Stub reference was no longer valid, this method is required");
						try
							{
								clientGames[playerNumber] = (RemoteMethods) Naming.lookup("gameclient" + playerNumber);
							}
						catch (MalformedURLException | RemoteException | NotBoundException e2)
							{
								e2.printStackTrace();
								System.out.println("Client impossible to re-lookup...");
							}
					}
				return clientGames[playerNumber];
			}
	}
