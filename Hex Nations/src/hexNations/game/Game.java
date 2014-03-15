package hexNations.game;

import java.rmi.RemoteException;

import hexNations.network.RemoteMethods;

public class Game implements RemoteMethods
	{
		public Game()
			{
				
			}

		@Override
		public void addClient(RemoteMethods client) throws RemoteException
			{
				System.out.println("Server only method called in client");
			}

		@Override
		public void sendTestMessage(String message) throws RemoteException
			{
				System.out.println("Client has recieved Messgae");
			}

		@Override
		public void checkAlive() throws RemoteException
			{}
	}