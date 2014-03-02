package hexNations;

import hexNations.Region.Region;
import tCode.TCode;

/**
 * The {@link Main} Class Contains the main method.
 * <p>
 * This Class also contains the instances of {@link Frame}, {@link Renderer}, {@link InputReciever} and {@link Images}, which are required for Troy's Code to
 * work.
 * 
 * @author Sebastian Troy
 */
public class Main extends TCode
	{
		/**
		 * This is the name of the folder containing the images used by Troy's Code. It must contain all of the default texture files, however their contents
		 * may be modified.
		 */
		public static String textureFolderName = "default";

		public static Server background;

		public static Region region;

		/**
		 * Program Entry
		 */
		public static void main(String[] args)
			{
				new Images();
				
				new Main(800, 600, true, false);
			}

		public Main(int width, int height, boolean framed, boolean resizable)
			{
				super(width, height, framed, resizable);			
				
				DEBUG = true;
				
				programName = "HexNations";
				versionNumber = "2.0_alpha";
				
				begin(new MainMenu());
			}
	}
