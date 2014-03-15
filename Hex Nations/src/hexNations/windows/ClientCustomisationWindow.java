package hexNations.windows;

import hexNations.Main;
import hexNations.network.RemoteMethods;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TLabel;
import tComponents.components.TMenu;
import tComponents.components.TTextField;

public class ClientCustomisationWindow extends RenderableObject
	{
		private TMenu menu;
		private TLabel ipInstructionsLabel = new TLabel("Please enter the IP adress shown on the host's screen");
		private TTextField ipField = new TTextField(0, 0, 300, 35, 15);
		private TButton beginButton = new TButton("Begin")
			{
				@Override
				public void pressed()
					{
						try
							{
								Main.server = (RemoteMethods) Naming.lookup("Server");
								Main.server.checkAlive();
							}
						catch (MalformedURLException e)
							{
								e.printStackTrace();
							}
						catch (RemoteException e)
							{
								e.printStackTrace();
							}
						catch (NotBoundException e)
							{
								e.printStackTrace();
							}
					}
			};

		@Override
		protected final void initiate()
			{
				menu = new TMenu(0, 0, Main.canvasWidth, Main.canvasHeight, TMenu.VERTICAL);
				ipInstructionsLabel.setBackgroundColour(Color.CYAN);

				menu.add(ipInstructionsLabel, false);
				menu.add(ipField, false);
				menu.add(beginButton);

				add(menu);
			}

		@Override
		protected final void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Main.canvasWidth, Main.canvasHeight);
			}
	}