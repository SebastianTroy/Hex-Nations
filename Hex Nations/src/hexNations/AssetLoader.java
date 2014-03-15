package hexNations;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tCode.LoadingScreen;
import tools.WindowTools;

/**
 * The {@link AssetLoader} Class loads from file and holds images in the form of {@link BufferedImage}'s
 * <p>
 * Declare and load any images you wish to use, in your program, in this class.
 * 
 * @author Sebastian Troy
 */
public class AssetLoader extends LoadingScreen
	{
		private static final long serialVersionUID = 1L;

		/**
		 * the directory for the images folder
		 */
		static final String imageDirectory = "/assets";

		// Gameplay images
		public static BufferedImage[][] tiles;
		public static BufferedImage[] boundaries;
		public static BufferedImage[] icons2;

		// Gui
		public static BufferedImage gui;
		public static BufferedImage[] infoPop;

		// Menu's
		public static BufferedImage[] menuButtons;
		public static BufferedImage[] helpSlides;
		public static BufferedImage begin;

		/**
		 * When an instance of the {@link AssetLoader} class is created, it automatically loads all of the Images needed for the program
		 */
		public AssetLoader(BufferedImage loadingScreenLogo)
			{
				super(loadingScreenLogo);
			}

		@Override
		protected void loadAll()
			{				
				try
					{
						setPercentLoaded(0);
						setCurrentTaskString("Loading Sprite Sheets");
						
						Class<AssetLoader> imageClass = AssetLoader.class;

						BufferedImage tileSheet = ImageIO.read(imageClass.getResource(imageDirectory + "/tiles.png"));
						BufferedImage boundarySheet = ImageIO.read(imageClass.getResource(imageDirectory + "/Boundaries.png"));
						BufferedImage menuButtonSheet = ImageIO.read(imageClass.getResource(imageDirectory + "/menuButtons.png"));
						BufferedImage iconsSheet = ImageIO.read(imageClass.getResource(imageDirectory + "/icons.png"));
						BufferedImage helpSheet = ImageIO.read(imageClass.getResource(imageDirectory + "/help.png"));
						BufferedImage infoPopSheet = ImageIO.read(imageClass.getResource(imageDirectory + "/infoPop.png"));

						setPercentLoaded(50);
						setCurrentTaskString("Extracting Images");

						gui = ImageIO.read(imageClass.getResource(imageDirectory + "/GUI.jpeg"));
						begin = ImageIO.read(imageClass.getResource(imageDirectory + "/begin.png"));

						infoPop = new BufferedImage[3];
						for (int i = 0; i < 3; i++)
							infoPop[i] = infoPopSheet.getSubimage(i * 40, 0, 40, 40);

						menuButtons = new BufferedImage[3];
						for (int i = 0; i < 3; i++)
							menuButtons[i] = menuButtonSheet.getSubimage(0, i * 200, 800, 200);

						helpSlides = new BufferedImage[8];
						for (int i = 0; i < 8; i++)
							helpSlides[i] = helpSheet.getSubimage(i * 200, 0, 200, 200);

						tiles = new BufferedImage[19][6];
						for (int i = 0; i < 19; i++)
							for (int j = 0; j < 6; j++)
								tiles[i][j] = tileSheet.getSubimage(i * 40, j * 40, 40, 40);

						icons2 = new BufferedImage[12];
						for (int i = 0; i < 12; i++)
							icons2[i] = iconsSheet.getSubimage(i * 40, 0, 40, 40);

						boundaries = new BufferedImage[6];
						for (int i = 0; i < 6; i++)
							boundaries[i] = boundarySheet.getSubimage(i * 40, 0, 40, 40);
						
						setPercentLoaded(100);
						setCurrentTaskString("Done");
					}
				catch (IOException IOe)
					{
						WindowTools.debugWindow("IO exception at loadImages");
						IOe.printStackTrace();
					}
			}
	}