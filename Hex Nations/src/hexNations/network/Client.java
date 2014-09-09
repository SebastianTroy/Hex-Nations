package hexNations.network;

import hexNations.network.packet.LobbyData;
import hexNations.network.packet.LobbyPlayerUpdate;
import hexNations.network.packet.Packet;
import hexNations.network.packet.Packet.Type;
import hexNations.windows.Lobby;
import tools.server.TClient;
import tools.server.chat.ChatClient;

public class Client extends TClient<Packet>
	{
		public final ChatClient chatClient;
		private final Lobby lobby;

		public Client(String hostAddress, Lobby lobby)
			{
				super(hostAddress, Server.PORT);
				this.lobby = lobby;

				if (isConnected())
					chatClient = new ChatClient(hostAddress, "");
				else
					chatClient = null;
			}
		
		public final void setPlayerName(String name)
		{
			chatClient.setClientName(name);
			
		}

		@Override
		protected void processObject(long senderID, Packet packet, boolean personal)
			{
				// If the data was sent from
				if (packet.type == Type.LOBBY_DATA)
					// Cast the packet as a lobbydata packet
					lobby.updateLobbyData((LobbyData) packet);
				else if (packet.type == Type.LOBBY_PLAYER_UPDATE)
					// Cast the packet as a lobbyplayerupdate packet
					lobby.updateLobbyData(((LobbyPlayerUpdate) packet).player);
			}

		@Override
		protected void serverDisconnected()
			{}

		@Override
		protected void kickedFromServer(String reason)
			{}
	}