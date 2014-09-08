package hexNations.windows;

import hexNations.Main;
import hexNations.network.Client;
import hexNations.network.Server;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTextArea;

import tCode.RenderableObject;
import tComponents.TComponent;
import tComponents.components.TButton;
import tComponents.components.TLabel;
import tComponents.components.TMenu;
import tComponents.components.TTextField;
import tools.NetworkTools;
import tools.WindowTools;

/**
 * TODO
 * 
 * @author Sebastian Troy
 */
public class ClientLobby extends RenderableObject
	{
		// Client variables
		private final boolean isHost;
		private Server server;
		private Client client;
		private String playerName;

		// Menu variables
		//@formatter:off
		// This menu will be at the top of the screen
		private TMenu upperMenu;
		private TButton becomeHostButton = new TButton("Host Game"){@Override public void pressed(){changeRenderableObject(new ClientLobby(true));}};
		private TButton joinGameButton = new TButton("Join Game"){@Override public void pressed(){server.closeServer(""); changeRenderableObject(new ClientLobby(false));}};
		private TButton getLocalIPButton = new TButton("IP: LAN game"){@Override public void pressed(){WindowTools.informationWindow(NetworkTools.getLocalIP(), "Local IP address");}};
		private TButton getExternalIPButton = new TButton("IP: Internet game"){@Override public void pressed(){WindowTools.informationWindow(NetworkTools.getExternalIp(), "External IP address");}};
		private TButton mainMenuButton = new TButton("Main Menu"){@Override public void pressed(){if (server != null) server.closeServer(""); changeRenderableObject(new MainMenu());}};

		// Player variables
		private ArrayList<Player> players = new ArrayList<Player>();

		// Chat variables
		private TMenu messageDisplay;
		private TTextField messageinput;

		// Game Variables

		// @formatter:on
		public ClientLobby(boolean isHost)
			{
				this.isHost = isHost;
			}

		public ClientLobby(boolean isHost, String playerName)
			{
				this.isHost = isHost;
				this.playerName = playerName;
			}

		@Override
		public void initiate()
			{
				if (playerName == null)
					// Chose a name for the player, to be used in chat as well as their game
					playerName = WindowTools.getUserStringWindow("Please choose a player name", "Player#");

				// Initiate the client/server model
				if (isHost)
					{
						// Start a server for people to connect to
						server = new Server();
						client = new Client("localhost", playerName);
						// TODO
						/*
						 * Start Server, Create a human player, ask for name, tell user they are hosting and what their ip is (using new NetworkTools)
						 */
					}
				else
					{
						boolean keepSearching = true;
						do
							{
								// obtain the ip address to connect to
								String ipString = WindowTools.getUserStringWindow("Pleast input the ip address shown by \n the game you wish to connect to", "e.g. 80.299.40.239");
								// If doesn't want to join a game, make them a host instead
								if (!ipString.matches("e.g. 80.299.40.239"))
									{
										// Attempt to make the connection
										client = new Client(ipString, playerName);
									}
								else
									keepSearching = false;
							}
						while (keepSearching && !client.isConnected());
					}
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// Add the upper menu to the lobby
				upperMenu = new TMenu(0, 0, (int) (Main.canvasWidth * (2.0 / 3.0)), 50, TMenu.HORIZONTAL);
				upperMenu.setBorderSize(5);
				add(upperMenu);

				// Add a button allowing the player to toggle whether they are hosting or joining a game
				upperMenu.add(isHost ? joinGameButton : becomeHostButton);
				upperMenu.add(mainMenuButton);
				if (isHost)
					{
						upperMenu.add(getLocalIPButton);
						upperMenu.add(getExternalIPButton);
					}
				upperMenu.add(mainMenuButton);
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				// Add the chat section to the lobby
				messageDisplay = new TMenu((int) (Main.canvasWidth * (2.0 / 3.0)), 20, (int) (Main.canvasWidth * (1.0 / 3.0)) - 20, 300, TMenu.VERTICAL);
				messageDisplay.setBackgroundColour(Color.WHITE);
				messageDisplay.setTComponentAlignment(TMenu.ALIGN_START);
				messageDisplay.setBorderSize(0);
				messageDisplay.setTComponentSpacing(0);
				add(messageDisplay);
				messageinput = new TTextField(messageDisplay.getXI() - 1, messageDisplay.getHeightI() + messageDisplay.getYI(), messageDisplay.getWidthI() + 1, 20, "Click here to type");
				add(messageinput);
			}

		@Override
		public void tick(double secondsPassed)
			{
				// If the user has failed to join a server, make them a host
				if (client == null || !client.isConnected())
					{
						changeRenderableObject(new ClientLobby(true, playerName));
						return;
					}

				// Get any messages sent since last tick
				for (String str : client.chatClient.getMessages())
					{
						messageDisplay.add(new Message(str), false);
						messageDisplay.setScrollBarScrollPercent(100);
					}
			}

		@Override
		public void render(Graphics2D g)
			{
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, Main.canvasWidth, Main.canvasHeight);
			}

		@Override
		public void mousePressed(MouseEvent e)
			{
				for (Player p : players)
					p.mousePressed(e.getX(), e.getY());
			}

		@Override
		public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						client.chatClient.sendMessage(messageinput.getText());
						messageinput.clearText();
					}
			}

		/**
		 * One per player joined to lobby
		 * 
		 * @author Sebastian Troy
		 */
		private class Player extends TComponent
			{
				private boolean isHuman, isReady = false;
				private String playerName;
				private Color colour;

				private Player(boolean isHuman, String playerName)
					{
						this.isHuman = isHuman;
						isReady = !isHuman;

						this.playerName = playerName;
					}

				@Override
				public void render(Graphics2D g)
					{

					}

				private final void mousePressed(int x, int y)
					{
						/*
						 * TODO change name toggle between human and AI (Local Human, Online Human, AI Easy, AI Economic...) Change player colour
						 */
					}

				@Override
				protected void addedToComponent()
					{}

				@Override
				protected void removedFromComponent()
					{}
			}

		private class Message extends TComponent
			{
				private final String ip, user;
				private String[] message;

				private Message(String message)
					{
						String[] subMessages = message.split(":", 3);
						ip = subMessages[0];
						user = subMessages[1];
						message = subMessages[2];

						setWidth(messageDisplay.getWidthD() - 20);

						FontMetrics fm = getObserver().getFontMetrics(getObserver().getFont());

						this.message = new String[(fm.stringWidth(message) / (getWidthI() - 25)) + 1];

						int[] index = new int[this.message.length + 1];
						index[0] = 0;
						for (int i = 1; i < this.message.length; i++)
							{
								int tempIndex = index[i - 1];
								double length = 0;
								while (length < getWidthD() - 25)
									{
										length = fm.stringWidth(message.substring(index[i - 1], tempIndex));
										tempIndex++;
									}
								if (message.charAt(tempIndex + 1) == ' ')
									tempIndex++;
								else if (message.charAt(tempIndex - 1) == ' ')
									tempIndex--;
								index[i] = tempIndex;
							}
						index[index.length - 1] = message.length() - 1;

						for (int i = 0; i < this.message.length; i++)
							{
								this.message[i] = message.substring(index[i], index[i + 1]);
							}

						setHeight(20 + (20 * this.message.length));
					}

				@Override
				public void render(Graphics2D g)
					{
						g.setColor(Color.BLACK);
						g.drawString(ip + ":" + user + ":", getXI() + 5, getYI() + 16);
						for (int i = 0; i < this.message.length; i++)
							g.drawString(message[i], getXI() + 10, getYI() + 32 + (16 * i));

						g.setColor(Color.BLACK);
						g.drawLine(getXI() + 5, getYI() + getHeightI(), getXI() + getWidthI() - 10, getYI() + getHeightI());
					}

				@Override
				protected void addedToComponent()
					{}

				@Override
				protected void removedFromComponent()
					{}
			}
	}