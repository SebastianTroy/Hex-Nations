package hexNations.windows;

import hexNations.Main;
import hexNations.network.Server;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TLabel;
import tComponents.components.TMenu;

public class HostCustomisationWindow extends RenderableObject
	{
		// @formatter:off
		private TMenu menu;
		private final TLabel ipLabel = new TLabel("192.168.***.***");
		private final TButton beginButton = new TButton("Begin"){@Override public final void pressed(){changeRenderableObject(new GameWindow());}};
		// @formatter:on

		@Override
		protected final void initiate()
			{
				try
					{
						// Get our IP address to make joining this game easier
						InetAddress addr = InetAddress.getLocalHost();
						ipLabel.setLabelText("Server Running at ip: " + addr.getHostAddress());
						// Instantiate the server so that clients can join as soon as we give them our ip adress
						Main.server = new Server("Server", 2);
					}
				catch (UnknownHostException e)
					{
						e.printStackTrace();
					}
				catch (RemoteException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				ipLabel.setDimensions(Main.canvasWidth, 35);
				add(ipLabel);
				
				// Set up the menu
				menu = new TMenu(0, 0, Main.canvasWidth, Main.canvasHeight, TMenu.VERTICAL);

				add(menu);

				menu.add(beginButton);
				
				// TODO set up a way of tracking and setting players (AI/LAN...)
			}

		@Override
		public void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);
			}
	}