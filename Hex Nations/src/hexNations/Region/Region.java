package hexNations.Region;

import hexNations.Constants;
import hexNations.Main;

import java.awt.image.BufferedImage;

public class Region implements Constants
	{
		public Tile[][] tiles;
		public int xRegions;
		public int yRegions;

		public byte numPlayers;
		public Player[] players;

		public byte currentPlayer = PLAYER_ONE;

		public float screenX = 0;
		public float screenY = 0;

		private BufferedImage map;

		public byte[][] mapLOSValues;

		// Percent coverage of map
		public byte GRASS_PERCENT;
		public byte FOREST_PERCENT;
		public byte SAND_PERCENT;
		public byte WATER_MARSH_PERCENT;

		public static final byte MEADOW_PERCENT = 2;
		public static final byte MINE_PERCENT = 2;
		public static final byte VILLAGE_PERCENT = 2;

		public Region(byte players, int mapSize, byte mountainPercent, byte forestPercent, byte desertPercent, byte waterPercent)
			{
				numPlayers = players;
				xRegions = yRegions = (int) Math.sqrt(mapSize * numPlayers);
				mapLOSValues = new byte[xRegions][yRegions];

				GRASS_PERCENT = (byte) (100 - mountainPercent);
				FOREST_PERCENT = forestPercent;
				SAND_PERCENT = desertPercent;
				WATER_MARSH_PERCENT = waterPercent;
			}

		public void prepareRegion()
			{
				setPlayers();

				newMap();

				setOwnership();

				setMapLOSValues();
			}

		private void setPlayers()
			{
				players = new Player[numPlayers + 1];
				for (byte i = 0; i < numPlayers + 1; i++)
					{
						players[i] = new Player(i);
					}
			}

		private void newMap()
			{
				map = MapGenerator.newMap(xRegions, yRegions);

				tiles = new Tile[xRegions][yRegions];

				for (int i = 0; i < xRegions; i++)
					for (int j = 0; j < yRegions; j++)
						{
							tiles[i][j] = new Tile(i, j, getMetaFromImage(i, j));
						}
			}

		private void setMapLOSValues()
			{
				// create array of bytes, one byte for each tile
				for (int i = 0; i < Main.region.xRegions; i++)
					for (int j = 0; j < Main.region.yRegions; j++)
						{
							mapLOSValues[i][j] = Main.region.tiles[i][j].meta[BLOCK];

							// declare boundary water blocks
							// as viewed
							if (i == 0 || j == 0 || i == Main.region.xRegions - 1 || j == Main.region.yRegions - 1)
								mapLOSValues[i][j] = 0;
						}
			}

		private byte[] getMetaFromImage(int i, int j)
			{
				if (map.getRGB(i, j) == GRASS_MAP.getRGB())
					return GRASS;

				if (map.getRGB(i, j) == MEADOW_MAP.getRGB())
					return MEADOW;

				if (map.getRGB(i, j) == WATER_MAP.getRGB())
					return WATER;

				if (map.getRGB(i, j) == SAND_MAP.getRGB())
					return SAND;

				if (map.getRGB(i, j) == MOUNTAIN_MAP.getRGB())
					return MOUNTAIN;

				if (map.getRGB(i, j) == FOREST_MAP.getRGB())
					return FOREST;

				if (map.getRGB(i, j) == VILLAGE_MAP.getRGB())
					return VILLAGE;

				if (map.getRGB(i, j) == FARM_MAP.getRGB())
					return FARMLAND;

				if (map.getRGB(i, j) == ORE_MAP.getRGB())
					return ORE;

				if (map.getRGB(i, j) == MARSH_MAP.getRGB())
					return MARSH;

				if (map.getRGB(i, j) == MANGROVE_MAP.getRGB())
					return MANGROVE;

				if (map.getRGB(i, j) == P1_MAP.getRGB())
					return P1;
				if (map.getRGB(i, j) == P2_MAP.getRGB())
					return P2;
				if (map.getRGB(i, j) == P3_MAP.getRGB())
					return P3;
				if (map.getRGB(i, j) == P4_MAP.getRGB())
					return P4;
				if (map.getRGB(i, j) == P5_MAP.getRGB())
					return P5;
				if (map.getRGB(i, j) == P6_MAP.getRGB())
					return P6;

				return WATER;
			}

		private void setOwnership()
			{
				for (int i = 0; i < xRegions; i++)
					for (int j = 0; j < yRegions; j++)
						{
							if (tiles[i][j].meta[ID] == P1[ID])
								{
									players[PLAYER_ONE].setCapital(i, j);
									tiles[i][j].ownerNum = PLAYER_ONE;
								}

							else if (tiles[i][j].meta[ID] == P2[ID])
								{
									players[PLAYER_TWO].setCapital(i, j);
									tiles[i][j].ownerNum = PLAYER_TWO;
								}

							else if (tiles[i][j].meta[ID] == P3[ID])
								{
									players[PLAYER_THREE].setCapital(i, j);
									tiles[i][j].ownerNum = PLAYER_THREE;
								}

							else if (tiles[i][j].meta[ID] == P4[ID])
								{
									players[PLAYER_FOUR].setCapital(i, j);
									tiles[i][j].ownerNum = PLAYER_FOUR;
								}

							else if (tiles[i][j].meta[ID] == P5[ID])
								{
									players[PLAYER_FIVE].setCapital(i, j);
									tiles[i][j].ownerNum = PLAYER_FIVE;
								}

							else if (tiles[i][j].meta[ID] == P6[ID])
								{
									players[PLAYER_SIX].setCapital(i, j);
									tiles[i][j].ownerNum = PLAYER_SIX;
								}
						}
			}
	}
