package hexNations.network.packet;

import java.io.Serializable;

public abstract class Packet implements Serializable
	{
		private static final long serialVersionUID = 1L;

		public static enum Type
			{
				LOBBY_DATA, LOBBY_PLAYER_UPDATE
			}

		public final Type type;

		public Packet(Type type)
			{
				this.type = type;
			}
	}