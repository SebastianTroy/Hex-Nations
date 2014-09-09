package hexNations.network.packet;

import java.util.ArrayList;

public class LobbyData extends Packet
	{
		private static final long serialVersionUID = 1L;

		public ArrayList<LobbyPlayer> players = new ArrayList<LobbyPlayer>();
		public int maxPlayerNum = 2, mapTilesPerPlayer = 40;


		public LobbyData()
			{
				super(Type.LOBBY_DATA);
			}
	}