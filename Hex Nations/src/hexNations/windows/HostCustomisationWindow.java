package hexNations.windows;

import hexNations.AssetLoader;
import hexNations.Main;
import hexNations.game.Region;
import hexNations.network.RemoteMethods;
import hexNations.network.Server;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import tCode.RenderableObject;
import tComponents.components.TButton;

public class HostCustomisationWindow extends RenderableObject
	{

		private final TButton begin = new TButton(650, 50, AssetLoader.begin)
			{
				@Override
				public final void pressed()
					{
//						Main.region = new Region();
//						changeRenderableObject(new GameWindow());
						try
							{
								Main.server = new Server("Server", 2);
								RemoteMethods rm = (RemoteMethods) Naming.lookup("Server");

							}
						catch (RemoteException e)
							{
								e.printStackTrace();
							}
						catch (MalformedURLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						catch (NotBoundException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
			};

		String ipAdress = "";

		@Override
		protected final void initiate()
			{
				try
					{
						// Get our IP address to make joining this game easier
						InetAddress addr = InetAddress.getLocalHost();
						ipAdress = addr.getHostAddress();
					}
				catch (UnknownHostException e)
					{
						e.printStackTrace();
					}

				add(begin);
			}

		@Override
		public void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.WHITE);
				g.drawString(ipAdress, 50, 300);
			}
	}