package hexNations.network.packet;

public class LobbyPlayerUpdate extends Packet
	{
		private static final long serialVersionUID = 1L;

		public final LobbyPlayer player;
		
		public LobbyPlayerUpdate(LobbyPlayer player)
			{
				super(Type.LOBBY_PLAYER_UPDATE);
				this.player = player;
			}
	}