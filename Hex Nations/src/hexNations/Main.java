package hexNations;

import hexNations.windows.MainMenu;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tCode.TCode;
import tools.WindowTools;

/**
 * @author Sebastian Troy
 */
public class Main extends TCode
	{
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
						begin(new AssetLoader(ImageIO.read(getClass().getResource(AssetLoader.imageDirectory + "/tiles.png"))), new MainMenu());
					}
				catch (IOException e)
					{
						if (!DEBUG)
							{
								WindowTools.debugWindow("Couldn't find the loading screen logo.");
								// Try again with a blank image
								begin(new AssetLoader(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)), new MainMenu());
							}
						else
							e.printStackTrace();
					}
			}
	}
