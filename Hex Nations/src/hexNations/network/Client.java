package hexNations.network;

import tools.server.ChatClient;
import tools.server.TClient;

public class Client extends TClient<Packet>
	{
		public final ChatClient chatClient;

		public Client(String hostAddress, String playerName)
			{
				super(hostAddress, Server.PORT);
				if (isConnected())
					chatClient = new ChatClient(hostAddress, playerName);
				else
					chatClient = null;
			}

		@Override
		protected void processObject(Packet object)
			{
				// TODO Auto-generated method stub
			}
	}