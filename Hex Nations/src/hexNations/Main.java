package hexNations;

import hexNations.network.RemoteMethods;
import hexNations.network.Server;
import hexNations.windows.GameWindow;
import hexNations.windows.MenuWindow;

import javax.imageio.ImageIO;

import tCode.TCode;
import tools.WindowTools;

/**
 * @author Sebastian Troy
 */
public class Main extends TCode
	{
		/**
		 * This is the name of the folder containing the images used by Troy's Code. It must contain all of the default texture files, however their contents
		 * may be modified.
		 */
		public static String textureFolderName = "default";

		/**
		 * This object represents the game being played, it will be calculated separately both on the Server and Client side as every player's actions are
		 * processed via the {@link Server} and passed to all Clients in order.
		 */
		public static GameWindow game;

		/**
		 * This object is our server which accepts remote method calls
		 */
		public static RemoteMethods server;
		

		/**
		 * Program Entry
		 */
		public static void main(String[] args)
			{
				new Main(800, 600, true, false);
			}

		public Main(int width, int height, boolean framed, boolean resizable)
			{
				super(width, height, framed, resizable);

				DEBUG = true;
				FORCE_SINGLE_THREAD = true;

				programName = "HexNations";
				versionNumber = "2.0_alpha";

				try
					{
						begin(new AssetLoader(ImageIO.read(getClass().getResource(AssetLoader.imageDirectory + "/tiles.png"))), new MenuWindow());
					}
				catch (Exception e)
					{
						if (!DEBUG)
							WindowTools.debugWindow("Couldn't begin the program.");
						else
							e.printStackTrace();
					}
			}
	}
