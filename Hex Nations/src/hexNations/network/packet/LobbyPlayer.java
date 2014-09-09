package hexNations.network.packet;

import java.awt.Color;
import java.io.Serializable;

/**
 * This class is used to represent all of the information that is needed to know
 * 
 * @author Sebastian Troy
 */
public class LobbyPlayer implements Serializable
	{
		private static final long serialVersionUID = 1L;

		public final long uniqueID;
		public final String playerName;
		public final Color playerColour;
		public final boolean isHuman, isReady;

		/**
		 * Creates an instance of {@link LobbyPlayer} which signals that this player has left the game.
		 * 
		 * @param id TODO
		 */
		public LobbyPlayer(long id)
			{
				uniqueID = id;
				playerName = null;
				isHuman = false;
				isReady = false;
				playerColour = null;

			}

		/**
		 * Used to update all players with data about a single player
		 * 
		 * @param id TODO
		 * @param name TODO
		 * @param colour TODO
		 * @param ready TODO
		 */
		public LobbyPlayer(long id, String name, Color colour, boolean human, boolean ready)
			{
				uniqueID = id;
				playerName = name;
				isHuman = human;
				isReady = ready;
				playerColour = colour;
			}
	}