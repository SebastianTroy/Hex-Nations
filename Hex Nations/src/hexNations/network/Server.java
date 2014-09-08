package hexNations.network;

import tools.server.ChatServer;
import tools.server.TServer;

/**
 * 
 * @author Sebastian Troy
 */
public class Server extends TServer
	{
		static final int PORT = 10901;

		public final ChatServer chat = new ChatServer();

		public Server()
			{
				super(PORT);
			}

		public final void closeServer(String message)
			{
				chat.closeServer(true);
				this.closeServer();
			}
	}