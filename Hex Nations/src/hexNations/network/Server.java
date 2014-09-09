package hexNations.network;

import hexNations.network.packet.LobbyPlayer;
import hexNations.network.packet.Packet;
import hexNations.network.packet.Packet.Type;
import hexNations.windows.Lobby;
import tools.ColTools;
import tools.server.TServer;
import tools.server.chat.ChatServer;

/**
 * 
 * @author Sebastian Troy
 */
public class Server extends TServer<Packet>
	{
		static final int PORT = 10901;

		public final Lobby lobby;
		public final ChatServer chat = new ChatServer();

		public Server(Lobby lobby)
			{
				super(PORT);
				this.lobby = lobby;
			}

		public final void closeServer(String message)
			{
				chat.closeServer(true);
				this.closeServer();
			}

		/**
		 * @param uniqueID
		 *            - The unique ID of the sender (should always be the same...)
		 * @param packet
		 */
		public final void send(long uniqueID, Packet packet)
			{
				sendToAll(uniqueID, packet);
			}

		@Override
		protected boolean clientConnected(long uniqueID)
			{
				// create a new player for the client and send the details to everyone
				lobby.data.players.add(new LobbyPlayer(uniqueID, "Player" + lobby.data.players.size(), ColTools.randColour(170), true, false));
				send(0L, lobby.data);
	
				// Only let more players join if there is room
				return (clients.size() < lobby.maxPlayersSlider.getValue());
			}

		@Override
		protected void clientDisconnected(long uniqueID)
			{
				// notify all of the leaving player
				for (int i = 0; i < lobby.data.players.size(); i++)
					if (lobby.data.players.get(i).uniqueID == uniqueID)
						{
							lobby.data.players.remove(i);
							send(0L, lobby.data);
							break;
						}
			}

		@Override
		protected void processObject(long senderID, Packet object)
			{
				// If the object is a lobby packet we simply want to send it to everyone
				if (object.type == Type.LOBBY_DATA)
					{
						sendToAll(senderID, object);
						return;
					}
				//else add to a LinkedBlockingList of packets which the main game controlling thread can chug through in its own time
			}
	}