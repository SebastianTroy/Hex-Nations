package hexNations.windows;

import hexNations.Main;
import hexNations.game.MultiPlayerGame;
import hexNations.network.Client;
import hexNations.network.Server;
import hexNations.network.packet.LobbyData;
import hexNations.network.packet.LobbyPlayer;
import hexNations.network.packet.LobbyPlayerUpdate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import tCode.RenderableObject;
import tComponents.TComponent;
import tComponents.components.TButton;
import tComponents.components.TLabel;
import tComponents.components.TMenu;
import tComponents.components.TSlider;
import tComponents.utils.events.TScrollEvent;
import tools.NetworkTools;
import tools.WindowTools;
import tools.server.chat.TChatBox;

/**
 * TODO
 * 
 * @author Sebastian Troy
 */
public class Lobby extends RenderableObject
	{
		private Server server = MultiPlayerGame.server;

		// Client variables
		private final boolean isHost;
		private Client client;
		public String playerName;

		// GUI ~~~~~~~~~~ @formatter:off
		// This menu will be at the top of the screen
		private TMenu upperMenu;
		private final TButton becomeHostButton = new TButton("Host Game"){@Override public void pressed(){changeRenderableObject(new Lobby(true));}};
		private final TButton joinGameButton = new TButton("Join Game"){@Override public void pressed(){changeRenderableObject(new Lobby(false));}};
		private final TButton getLocalIPButton = new TButton("IP: LAN game"){@Override public void pressed(){WindowTools.informationWindow(NetworkTools.getLocalIP(), "Local IP address");}};
		private final TButton getExternalIPButton = new TButton("IP: Internet game"){@Override public void pressed(){WindowTools.informationWindow(NetworkTools.getExternalIp(), "External IP address");}};
		private final TButton mainMenuButton = new TButton("Main Menu"){@Override public void pressed(){ changeRenderableObject(new MainMenu());}};
		// Player
		private TMenu playersMenu;
		private Player[] playerComponents;
		// World Generation
		private TMenu gameVariablesMenu;
		public final TSlider maxPlayersSlider = new TSlider(TSlider.HORIZONTAL, 2, 10);
		public final TSlider mapSizeSlider = new TSlider(TSlider.HORIZONTAL, 25, 100);
		private final TButton readyButton = new TButton("Begin"){boolean ready = false; @Override public void pressed(){ready=!ready;setLabel(ready? "I'm not Ready!":"Begin");/*TODO begin game*/}};
		// Chat
		private TChatBox chatBox;
		
		// Player & world generation data ~~~~~~~~~~ @formatter:on
		public LobbyData data = new LobbyData();
		private boolean dataUpdated = true;

		public Lobby(boolean isHost)
			{
				this.isHost = isHost;
			}

		public Lobby(boolean isHost, String playerName)
			{
				this.isHost = isHost;
				this.playerName = playerName;
			}

		@Override
		public void initiate()
			{
				// Initiate the client/server model
				if (isHost)
					{
						// Start a server for people to connect to
						if (server != null)
							server.closeServer("");
						server = new Server(this);
						client = new Client("localhost", this);

					}
				else
					{
						// connect to a server TODO make this nicer / optional as opposed to forcing it perhaps a text field and go button
						// in the main menu?
						boolean keepSearching = true;
						do
							{
								// obtain the ip address to connect to
								String ipString = WindowTools.getUserStringWindow("Pleast input the ip address shown by \n the game you wish to connect to", "e.g. 80.299.40.239");
								// If doesn't want to join a game, make them a host instead
								if (!ipString.matches("e.g. 80.299.40.239"))
									{
										// Attempt to make the connection
										client = new Client(ipString, this);
									}
								else
									keepSearching = false;
							}
						while (keepSearching && !client.isConnected());
					}

				// Prepare the user interface
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// Add the upper menu to the lobby
				upperMenu = new TMenu(0, 0, (int) (Main.canvasWidth * (2.0 / 3.0)), 50, TMenu.HORIZONTAL);
				upperMenu.setBorderSize(5);
				add(upperMenu);

				// Add a button allowing the player to toggle whether they are hosting or joining a game
				upperMenu.add(mainMenuButton);
				upperMenu.add(isHost ? joinGameButton : becomeHostButton);
				if (isHost)
					{
						upperMenu.add(getLocalIPButton);
						upperMenu.add(getExternalIPButton);
					}
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// Add the chat section to the lobby
				chatBox = new TChatBox((int) (Main.canvasWidth * (2.0 / 3.0)), 20, (int) (Main.canvasWidth * (1.0 / 3.0)) - 20, 300, client.chatClient);
				add(chatBox);
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// Add the menu which will contain the details of the current players
				playersMenu = new TMenu(5, upperMenu.getHeightD() + 5, (int) (Main.canvasWidth * (2.0 / 3.0)) - 10, Main.canvasHeight - (upperMenu.getHeightD() + 5) - 10, TMenu.VERTICAL);
				add(playersMenu);
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// Add the game variables section to the lobby
				// The menu
				gameVariablesMenu = new TMenu((int) (Main.canvasWidth * (2.0 / 3.0)), chatBox.getYI() + chatBox.getHeightI(), chatBox.getWidthI(), Main.canvasHeight
						- (chatBox.getYI() + chatBox.getHeightI()), TMenu.VERTICAL);
				add(gameVariablesMenu);
				// The num players slider and label
				gameVariablesMenu.add(new TLabel("Maximum Players"), false);
				gameVariablesMenu.add(maxPlayersSlider);
				// The map size slider and label
				gameVariablesMenu.add(new TLabel("Map Tiles per Player"), false);
				gameVariablesMenu.add(mapSizeSlider);
				// The Ready to begin button
				gameVariablesMenu.add(readyButton);
			}

		@Override
		public final void tick(double secondsPassed)
			{
				// If the user has failed to join a server, make them a host TODO this is inelegant... this object should never be
				// initialised without a valid client...
				if (client == null || !client.isConnected())
					{
						changeRenderableObject(new Lobby(true, playerName));
						return;
					}

				// If we have got new information from the server
				if (dataUpdated)
					{
						// Update the GUI
						// Update the playerComponents
						playerComponents = new Player[data.players.size()];
						playersMenu.clearTComponents();
						int i = 0;
						for (LobbyPlayer p : data.players)
							{
								if (p.uniqueID == client.getUniqueID())
									client.setPlayerName(p.playerName);
								playerComponents[i] = new Player(p.uniqueID, p.isHuman, p.playerName, p.playerColour, p.isReady);
								playersMenu.add(playerComponents[i]);
								i++;
							}

						// Update sliders
						maxPlayersSlider.setValue((int) data.maxPlayerNum);
						mapSizeSlider.setValue((int) data.mapTilesPerPlayer);
						dataUpdated = false;
					}
			}

		@Override
		public final void render(Graphics2D g)
			{
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, Main.canvasWidth, Main.canvasHeight);
			}

		/**
		 * 
		 * @param data
		 *            - packet recieved from the server, .
		 */
		public final void updateLobbyData(LobbyData data)
			{
				// ~~~~~~ Deal with the player data ~~~~~~
				this.data = data;

				// Tell the lobby to check for up to date data
				dataUpdated = true;
			}

		public void updateLobbyData(LobbyPlayer player)
			{
				for (int i = 0; i < data.players.size(); i++)
					if (data.players.get(i).uniqueID == player.uniqueID)
						{
							data.players.remove(i);
							data.players.add(i, player);
							break;
						}
				server.send(0L, data);
			}

		@Override
		public final void mousePressed(MouseEvent e)
			{
				for (Player p : playerComponents)
					p.mousePressed(e.getX(), e.getY());
			}

		@Override
		public final void tScrollEvent(TScrollEvent e)
			{
				if (e.getScrollType() == TScrollEvent.FINAL_VALUE)
					{
						Object source = e.getSource();
						if (source == maxPlayersSlider)
							{
								client.chatClient.sendMessage("~~ Max Players" + (isHost ? " SET: " : " REQUEST: ") + (int) e.getScrollValue() + " ~~ ");
								if (isHost)
									{
										server.setAcceptClients(true);
										data.maxPlayerNum = (int) maxPlayersSlider.getValue();
									}
								maxPlayersSlider.setValue(data.maxPlayerNum);
							}
						else if (source == mapSizeSlider)
							{
								client.chatClient.sendMessage("~~ Map Size" + (isHost ? " SET: " : " REQUEST: ") + (int) e.getScrollValue() + " ~~ ");
								if (isHost)
									data.mapTilesPerPlayer = (int) mapSizeSlider.getValue();
								mapSizeSlider.setValue(data.mapTilesPerPlayer);
							}

						if (isHost)
							server.send(client.getUniqueID(), data);
					}
			}

		/**
		 * One per player joined to lobby
		 * 
		 * @author Sebastian Troy
		 */
		private class Player extends TComponent
			{
				private final long uniqueID;
				private final boolean isHuman, isReady;
				private final String playerName;
				private final Color colour;

				private Player(long uniqueID, boolean isHuman, String playerName, Color colour, boolean isReady)
					{
						this.uniqueID = uniqueID;
						this.isHuman = isHuman;
						this.isReady = isReady;
						this.playerName = playerName;
						this.colour = colour;
					}

				@Override
				public void render(Graphics2D g)
					{
						g.setColor(Color.CYAN);
						g.fillRect(getXI(), getYI(), getWidthI(), getHeightI());

						g.setColor(colour);
						g.fillRect(getXI() + 5, getYI() + 5, getHeightI() - 10, getHeightI() - 10);

						g.setColor(Color.BLACK);
						g.drawString((isHuman ? "Human" : "AI") + "   :   " + playerName, getXI() + (getHeightI() - 10) + 120, getYI() + ((getHeightI() / 2)) + 8);
					}

				private final void mousePressed(int x, int y)
					{
						/*
						 * TODO change name toggle between human and AI (Local Human, Online Human, AI Easy, AI Economic...) Change player
						 * colour
						 */
						// Name cannot be set to ""
					}

				@Override
				protected void addedToComponent()
					{}

				@Override
				protected void removedFromComponent()
					{}
			}
	}