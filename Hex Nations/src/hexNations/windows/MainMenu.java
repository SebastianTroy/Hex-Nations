package hexNations.windows;

import hexNations.Main;

import java.awt.Graphics2D;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TMenu;
import tools.NetworkTools;

public class MainMenu extends RenderableObject
	{
		private TMenu mainMenu;

		// @formatter:off
		private final TButton createGameButton = new TButton("Start Game") {@Override public void pressed(){changeRenderableObject(new Lobby(true));}};
		private final TButton joinGameButton = new TButton("Join Game"){@Override public void pressed(){changeRenderableObject(new Lobby(false));}};
		private final TButton options = new TButton("Options") {@Override public void pressed(){changeRenderableObject(new Options());}};
		private final TButton help = new TButton("Help") {@Override public void pressed(){NetworkTools.openInBrowser("http://troydev.proggle.net/projects-hex-nations.php");}};
		// @formatter:on

		@Override
		public void initiate()
			{
				mainMenu = new TMenu(100, 0, Main.canvasWidth - 200, Main.canvasHeight, TMenu.VERTICAL);

				mainMenu.add(createGameButton);
				mainMenu.add(joinGameButton);
				mainMenu.add(options);
				mainMenu.add(help);
				add(mainMenu);
			}

		@Override
		public void render(Graphics2D g)
			{
				g.clearRect(0, 0, Main.canvasWidth, Main.canvasHeight);
			}

		/**
		 * Represents an existing game that has been discovered on the LAN.
		 * 
		 * @author Sebastian Troy
		 */
		private class ExistingGame extends TButton
			{
				ExistingGame()
					{
						super("");
					}
			}
	}