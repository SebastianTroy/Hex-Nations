package TroysCode;

import java.awt.Color;

/**
 * This interface contains variables with a constant value. These constants can
 * be reached in any Class, as long as they <code> implement Constants</code>.
 * 
 * @author Sebastian Troy
 */
public interface Constants
	{
		/*
		 * This class allows the declaration of constants. I often use constants
		 * to refer to objects in an array, for example: ""arrayOfColours[0]""
		 * refers to the first member of that array, but that could be any
		 * colour. If I have the constant ""public static final byte BLUE = 0""
		 * and call ""arrayOfColours[BLUE]"" I then have an idea of what it is
		 * I'm actually accesing.
		 */
		public static final byte TRUE = -1;
		public static final byte FALSE = -2;
		public static final byte NULL = -3;
		public static final byte MAXIMUM = 127;

		// ##############
		// InfoPop types
		public static final byte ATTACKED = 0;
		public static final byte FAILED = 1;
		public static final byte LOST = 2;
		
		// ##############
		// Player values
		public static final int INCREMENT = 1000;

		public static final byte PLAYING = 0;
		public static final byte DEFEATED = 1;
		public static final byte SURRENDERED = 2;
		
		public static final byte HUMAN = 0;
		public static final byte CPU = 1;	
		
		public static final byte GROW = 1;
		public static final byte DEFEND = 2;		
		public static final byte ATTACK = 3;		

		// ###############
		// Tile Data

		// tile buildings
		public static final byte NONE = 0;
		public static final byte TOWER = 5;
		public static final byte FORTIFIED = 2;

		public static final byte TOWERPRICE = 8;
		public static final byte FORTIFYPRICE = 14;

		// connected to capital related
		public static final byte UNCONNECTED = 0;
		public static final byte UNCHECKED = 1;
		public static final byte CHECKED = 2;
		public static final byte VIEWED = 100;

		// line of sight related
		public static final byte UNDISCOVERED = 0;
		public static final byte DISCOVERED = 1;
		public static final byte OBSCURED = 2;

		// references for meta[]
		public static final byte ID = 0;
		public static final byte BASELEVEL = 1;
		public static final byte CAPTURABLE = 2;
		public static final byte PRODUCTIVITY = 3;
		public static final byte RESOURCES = 4;
		public static final byte CAPACITY = 5;
		public static final byte VIEW = 6;
		public static final byte BLOCK = 7;
		public static final byte WEIGHT = 8;

		// Metadata fot tile types
		public static final byte GRASS[] = { 0, 4, TRUE, 0, 1, 10, 4, -1, -3 };
		public static final byte MOUNTAIN[] = { 1, 23, TRUE, -8, 5, 20, 8, -3,  -9};
		public static final byte WATER[] = { 2, NULL, FALSE, 0, 0, 0, 0, -1, -100 };
		public static final byte FOREST[] = { 3, 9, TRUE, 1, 5, 15, 5, -2, -1 };
		public static final byte ORE[] = { 4, 25, TRUE, 12, 8, 95, 9, -3, 0 };
		public static final byte SAND[] = { 5, 2, TRUE, 0, 0, 5, 4, -1, -2 };
		public static final byte VILLAGE[] = { 6, 16, TRUE, 4, 5, 70, 6, -1, 0 };
		public static final byte FARMLAND[] = { 7, 8, TRUE, 2, 3, 58, 5, -1, 0 };
		public static final byte MARSH[] = { 8, 16, TRUE, -6, 0, 5, 4, -1, -7 };
		public static final byte MANGROVE[] = { 9, 17, TRUE, 1, 3, 5, 4, -2, -2 };
		public static final byte MEADOW[] = { 10, 4, TRUE, 0, 1, 10, 4, -1, -2 };
		public static final byte P1[] = { 12, 50, TRUE, 10, NULL, 124, 7, -4, 0 };
		public static final byte P2[] = { 13, 50, TRUE, 10, NULL, 124, 7, -4, 0 };
		public static final byte P3[] = { 14, 50, TRUE, 10, NULL, 124, 7, -4, 0 };
		public static final byte P4[] = { 15, 50, TRUE, 10, NULL, 124, 7, -4, 0 };
		public static final byte P5[] = { 16, 50, TRUE, 10, NULL, 124, 7, -4, 0 };
		public static final byte P6[] = { 17, 50, TRUE, 10, NULL, 124, 7, -4, 0 };
		// Used as references for textures
		public static final byte FOG_OF_WAR[] = { 11 };
		public static final byte HASH[] = { 18 };

		// Player Colour Data
		public static final byte COLOURS[] = { 0, 0, 1, 2, 3, 4, 5 };

		// Owner
		public static final byte NATURE = 0;
		public static final byte PLAYER_ONE = 1;
		public static final byte PLAYER_TWO = 2;
		public static final byte PLAYER_THREE = 3;
		public static final byte PLAYER_FOUR = 4;
		public static final byte PLAYER_FIVE = 5;
		public static final byte PLAYER_SIX = 6;

		// ###############
		// Key data

		public static final byte NO_KEY = 0;
		public static final byte UP = 1;
		public static final byte DOWN = 2;
		public static final byte LEFT = 3;
		public static final byte RIGHT = 4;

		// ###############
		// Mouse states
		public static final byte CAPTURE = 0;
		public static final byte SURRENDER = 1;
		public static final byte BUILDTOWER = 2;
		public static final byte NO_BUILDTOWER = 3;
		public static final byte FORTIFY = 4;
		public static final byte NO_FORTIFY = 5;

		// ###############
		// Map Constants

		// Colours
		public static final Color GRASS_MAP = new Color(0, 80, 0);
		public static final Color MEADOW_MAP = new Color(60, 80, 0);
		public static final Color MOUNTAIN_MAP = new Color(80, 80, 80);
		public static final Color WATER_MAP = new Color(10, 10, 110);
		public static final Color FOREST_MAP = new Color(0, 50, 0);
		public static final Color VILLAGE_MAP = new Color(150, 150, 150);
		public static final Color FARM_MAP = new Color(0, 120, 0);
		public static final Color SAND_MAP = new Color(230, 230, 0);
		public static final Color ORE_MAP = new Color(80, 90, 90);
		public static final Color MARSH_MAP = new Color(0, 50, 150);
		public static final Color MANGROVE_MAP = new Color(2, 60, 10);

		public static final Color P1_MAP = new Color(0, 1, 0);
		public static final Color P2_MAP = new Color(0, 2, 0);
		public static final Color P3_MAP = new Color(0, 3, 0);
		public static final Color P4_MAP = new Color(0, 4, 0);
		public static final Color P5_MAP = new Color(0, 5, 0);
		public static final Color P6_MAP = new Color(0, 6, 0);

	}
