package hexNations;

import hexNations.network.Server;
import hexNations.region.Region;
import hexNations.windows.GameWindow;
import hexNations.windows.MenuWindow;

import java.io.IOException;

import javax.imageio.ImageIO;

import tCode.TCode;
import tools.Rand;

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
		 * This object represents the game being played, it will be calculated separately both on the Server and Client side as every player's actions are first
		 * processed by the {@link Server}
		 */
		public static GameWindow game;

		public static Region region;

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

				programName = "HexNations";
				versionNumber = "2.0_alpha";
				
				try
					{
						new AssetLoader(ImageIO.read(getClass().getResource(AssetLoader.imageDirectory + "/tiles.png"))).loadResources();
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
				
				// Set a random icon from the types of tiles available in game
				frame.addIconImage(AssetLoader.tiles[Rand.int_(0, AssetLoader.tiles.length)][0]);

				begin(new MenuWindow());
			}
	}
