package hexNations.Region;

import hexNations.Constants;
import hexNations.Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import tools.NumTools;
import tools.Rand;

public class MapGenerator implements Constants
	{
		private static BufferedImage map;
		private static Graphics g;

		private static int width;
		private static int height;

		private static int percent;

		public static BufferedImage newMap(int xSize, int ySize)
			{
				width = xSize;
				height = ySize;

				percent = (width * height) / 100;

				createImage();
				drawToImage();
				// Used for calculations in game balancing
				getPercentageCoverage();

				return map;
			}

		private static void createImage()
			{
				map = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				g = map.getGraphics();
			}

		private static void drawToImage()
			{
				drawMountains();
				drawDesertRegions();
				drawGrassRegions();
				drawVillages();
				drawForests();
				drawLakes();
				drawFarmland();
				placeCapitals();

				// Create boarder around map
				g.setColor(WATER_MAP);
				g.drawRect(0, 0, width - 1, height - 1);
				g.drawRect(1, 1, width - 3, height - 3);
			}

		private static void drawMountains()
			{
				g.setColor(MOUNTAIN_MAP);
				g.fillRect(0, 0, width, height);

				int numMineHexs = 0;
				while (Region.MINE_PERCENT * percent > numMineHexs)
					{
						int x = Rand.int_(0, width);
						int y = Rand.int_(0, height);

						int sizeX = 1;
						int sizeY = 1;

						if (Rand.percent() > 80)
							sizeX++;
						else if (Rand.percent() > 80)
							sizeY++;

						g.setColor(ORE_MAP);
						g.fillRect(x, y, sizeX, sizeY);

						numMineHexs = 0;
						for (int pixX = 0; pixX < width; pixX++)
							for (int pixY = 0; pixY < height; pixY++)
								{
									if (map.getRGB(pixX, pixY) == ORE_MAP.getRGB())
										numMineHexs++;
								}
					}
			}

		private static void drawDesertRegions()
			{
				int numSandHexs = 0;
				while (Main.region.SAND_PERCENT * percent > numSandHexs)
					{
						int x = Rand.int_(0, width);
						int y = Rand.int_(0, height);
						int sizeX = Rand.int_(1, 5);
						int sizeY = Rand.int_(1, 5);

						g.setColor(SAND_MAP);
						g.fillOval(x, y, sizeX, sizeY);

						numSandHexs = 0;
						for (int pixX = 0; pixX < width; pixX++)
							for (int pixY = 0; pixY < height; pixY++)
								{
									if (map.getRGB(pixX, pixY) == SAND_MAP.getRGB())
										numSandHexs++;
								}
					}
			}

		private static void drawGrassRegions()
			{
				int numGrassHexs = 0;
				while (Main.region.GRASS_PERCENT * percent > numGrassHexs)
					{
						int x = Rand.int_(0, width);
						int y = Rand.int_(0, height);
						int sizeX = Rand.int_(2, 9);
						int sizeY = Rand.int_(2, 9);

						g.setColor(GRASS_MAP);
						g.fillOval(x, y, sizeX, sizeY);

						numGrassHexs = 0;
						for (int pixX = 0; pixX < width; pixX++)
							for (int pixY = 0; pixY < height; pixY++)
								{
									if (map.getRGB(pixX, pixY) == GRASS_MAP.getRGB() || map.getRGB(pixX, pixY) == MEADOW_MAP.getRGB())
										numGrassHexs++;
								}
					}
				for (int pixX = 0; pixX < width; pixX++)
					for (int pixY = 0; pixY < height; pixY++)
						if (Rand.percent() < Region.MEADOW_PERCENT)
							{
								g.setColor(MEADOW_MAP);
								g.fillRect(pixX, pixY, 1, 1);
							}
			}

		private static void drawForests()
			{
				int numForestHexs = 0;
				while (Main.region.FOREST_PERCENT * percent > numForestHexs)
					{
						int x = Rand.int_(0, width);
						int y = Rand.int_(0, height);
						int radius = Rand.int_(4, 11);

						g.setColor(FOREST_MAP);
						for (int i = x; i < x + radius; i++)
							for (int j = y; j < y + radius; j++)
								{
									if (i >= 0 && j >= 0 && i < width && j < height)
										if (map.getRGB(i, j) != MOUNTAIN_MAP.getRGB() && map.getRGB(i, j) != WATER_MAP.getRGB())
											if (Rand.float_(0, radius) < radius - NumTools.distance(i, j, x, y))
												g.fillRect(i, j, 1, 1);
								}

						numForestHexs = 0;
						for (int pixX = 0; pixX < width; pixX++)
							for (int pixY = 0; pixY < height; pixY++)
								{
									if (map.getRGB(pixX, pixY) == FOREST_MAP.getRGB())
										numForestHexs++;
								}
					}
			}

		private static void drawVillages()
			{
				int numVillageHexs = 0;
				while (Region.VILLAGE_PERCENT * percent > numVillageHexs)
					{
						int x = Rand.int_(0, width);
						int y = Rand.int_(0, height);

						g.setColor(VILLAGE_MAP);
						g.fillRect(x, y, 1, 1);

						numVillageHexs = 0;
						for (int pixX = 0; pixX < width; pixX++)
							for (int pixY = 0; pixY < height; pixY++)
								{
									if (map.getRGB(pixX, pixY) == VILLAGE_MAP.getRGB())
										numVillageHexs++;
								}
					}
			}

		private static void drawFarmland()
			{
				for (int pixX = 0; pixX < width; pixX++)
					for (int pixY = 0; pixY < height; pixY++)
						if (map.getRGB(pixX, pixY) == VILLAGE_MAP.getRGB())
							{
								int radius = Rand.int_(2, 3);

								g.setColor(FARM_MAP);
								for (int i = pixX - radius; i < pixX + radius; i++)
									for (int j = pixY - radius; j < pixY + radius; j++)
										if (i >= 0 && j >= 0 && i < width && j < height)
											if (map.getRGB(i, j) != MOUNTAIN_MAP.getRGB() && map.getRGB(i, j) != WATER_MAP.getRGB())
												if (Rand.float_(0, radius) < radius - NumTools.distance(i, j, pixX, pixY))
													if (map.getRGB(i, j) == GRASS_MAP.getRGB())
														g.fillRect(i, j, 1, 1);

							}
			}

		private static void drawLakes()
			{
				int numWaterAndMarshHexs = 0;
				while (Main.region.WATER_MARSH_PERCENT * percent > numWaterAndMarshHexs)
					{
						boolean isWater = (Rand.percent() > 30 ? true : false);

						int x = Rand.int_(0, width);
						int y = Rand.int_(0, height);
						int numCircles = isWater ? Rand.int_(4, 7) : 10;

						for (int num = 0; num < numCircles; num++)
							if (Rand.percent() > (isWater ? 90 : 60))
								{
									g.setColor(isWater ? SAND_MAP : MANGROVE_MAP);
									g.fillOval(x + Rand.int_(-2, 2), y + Rand.int_(-2, 2), Rand.int_(2, 4), Rand.int_(2, 4));
								}
							else
								{
									g.setColor(isWater ? WATER_MAP : MARSH_MAP);
									g.fillOval(x + Rand.int_(-2, 2), y + Rand.int_(-2, 2), Rand.int_(2, 4), Rand.int_(2, 4));
								}

						numWaterAndMarshHexs = 0;
						for (int pixX = 0; pixX < width; pixX++)
							for (int pixY = 0; pixY < height; pixY++)
								{
									if (map.getRGB(pixX, pixY) == WATER_MAP.getRGB() || map.getRGB(pixX, pixY) == MANGROVE_MAP.getRGB() || map.getRGB(pixX, pixY) == MARSH_MAP.getRGB())
										numWaterAndMarshHexs++;
								}
					}
			}

		private static void placeCapitals()
			{
				int numPlayers = Main.region.numPlayers;

				ArrayList<Color> playerCols = new ArrayList<Color>();
				playerCols.add(P1_MAP);
				playerCols.add(P2_MAP);
				if (numPlayers > 2)
					playerCols.add(P3_MAP);
				if (numPlayers > 3)
					playerCols.add(P4_MAP);
				if (numPlayers > 4)
					playerCols.add(P5_MAP);
				if (numPlayers == 6)
					playerCols.add(P6_MAP);

				int[] xVals = new int[6];
				int[] yVals = new int[6];

				boolean horizontal = Rand.bool();

				xVals[0] = (int) (horizontal ? (width / 8f) : (width / 8f) * 7);
				yVals[0] = (int) (height / 8f);

				xVals[1] = (int) (horizontal ? (width / 8f) * 7 : (width / 8f));
				yVals[1] = (int) ((height / 8f) * 7);

				xVals[2] = (int) (horizontal ? (width / 8f) * 7 : (width / 8f));
				yVals[2] = (int) (height / 8f);

				xVals[3] = (int) (horizontal ? (width / 8f) : (width / 8f) * 7);
				yVals[3] = (int) ((height / 8f) * 7);

				if (numPlayers == 5)
					xVals[4] = yVals[4] = (int) width / 2;
				else
					{
						xVals[4] = (int) (horizontal ? width / 2 : width / 8f);
						yVals[4] = (int) (horizontal ? (height / 8f) * 7 : height / 2);
					}

				xVals[5] = (int) (horizontal ? width / 2 : (width / 8f) * 7);
				yVals[5] = (int) (horizontal ? height / 8f : height / 2);

				Collections.shuffle(playerCols);
				for (int i = 0; i < numPlayers; i++)
					{
						int x = xVals[i];
						int y = yVals[i];

						g.setColor(FARM_MAP);
						if (y % 2 == 0)
							{
								g.fillRect(x - 1, y - 1, 2, 3);
								g.fillRect(x + 1, y, 1, 1);
							}
						else
							{
								g.fillRect(x, y - 1, 2, 3);
								g.fillRect(x - 1, y, 1, 1);
							}

						g.setColor(playerCols.get(i));
						g.fillRect(x, y, 1, 1);
					}
			}

		static float grass = 0;
		static float stone = 0;
		static float farm = 0;
		static float marsh = 0;
		static float mangrove = 0;
		static float sand = 0;
		static float tree = 0;
		static float village = 0;
		static float ore = 0;
		static float water = 0;
		static float capitals = 0;

		static float grassP = 0;
		static float stoneP = 0;
		static float farmP = 0;
		static float marshP = 0;
		static float mangroveP = 0;
		static float sandP = 0;
		static float treeP = 0;
		static float villageP = 0;
		static float oreP = 0;
		static float waterP = 0;
		static float capitalsP = 0;

		private static void getPercentageCoverage()
			{

				for (int x = 0; x < width; x++)
					for (int y = 0; y < height; y++)
						{
							int pix = map.getRGB(x, y);

							if (pix == GRASS_MAP.getRGB() || pix == MEADOW_MAP.getRGB())
								grass++;
							else if (pix == MOUNTAIN_MAP.getRGB())
								stone++;
							else if (pix == FARM_MAP.getRGB())
								farm++;
							else if (pix == MARSH_MAP.getRGB())
								marsh++;
							else if (pix == MANGROVE_MAP.getRGB())
								mangrove++;
							else if (pix == SAND_MAP.getRGB())
								sand++;
							else if (pix == FOREST_MAP.getRGB())
								tree++;
							else if (pix == VILLAGE_MAP.getRGB())
								village++;
							else if (pix == ORE_MAP.getRGB())
								ore++;
							else if (pix == WATER_MAP.getRGB())
								water++;
							else if (pix == P1_MAP.getRGB())
								capitals++;
							else if (pix == P2_MAP.getRGB())
								capitals++;
							else if (pix == P3_MAP.getRGB())
								capitals++;
							else if (pix == P4_MAP.getRGB())
								capitals++;
							else if (pix == P5_MAP.getRGB())
								capitals++;
							else if (pix == P6_MAP.getRGB())
								capitals++;
						}
				float totalTiles = width * height;

				grassP = grass / (totalTiles / 100);
				stoneP = stone / (totalTiles / 100);
				farmP = farm / (totalTiles / 100);
				marshP = marsh / (totalTiles / 100);
				mangroveP = mangrove / (totalTiles / 100);
				sandP = sand / (totalTiles / 100);
				treeP = tree / (totalTiles / 100);
				villageP = village / (totalTiles / 100);
				oreP = ore / (totalTiles / 100);
				waterP = water / (totalTiles / 100);
				capitalsP = capitals / (totalTiles / 100);

				System.out.println("Percentage distribution of tiles over map");
				System.out.println("");
				System.out.println("GRASS:   " + grassP + "%" + "     [ - " + grass + " - ]");
				System.out.println("STONE:   " + stoneP + "%" + "     [ - " + stone + " - ]");
				System.out.println("FARM:    " + farmP + "%" + "     [ - " + farm + " - ]");
				System.out.println("MARSH:   " + marshP + "%" + "     [ - " + marsh + " - ]");
				System.out.println("MANGROVE:" + mangroveP + "%" + "     [ - " + mangrove + " - ]");
				System.out.println("SAND:    " + sandP + "%" + "     [ - " + sand + " - ]");
				System.out.println("FOREST:  " + treeP + "%" + "     [ - " + tree + " - ]");
				System.out.println("VILLAGE: " + villageP + "%" + "     [ - " + village + " - ]");
				System.out.println("ORE:     " + oreP + "%" + "     [ - " + ore + " - ]");
				System.out.println("WATER:   " + waterP + "%" + "     [ - " + water + " - ]");
				System.out.println("CAPITALS:" + capitalsP + "%" + "     [ - " + capitals + " - ]");
				System.out.println("");
				System.out.println("TOTAL:   " + (oreP + mangroveP + stoneP + farmP + sandP + waterP + grassP + marshP + capitalsP + treeP + villageP) + "%" + "     [ - " + totalTiles + " - ]");
				System.out.println("");
				System.out.println("####################");

				// AVERAGE res/second for [-X-] tiles
				getResPerTick(100);
				getResPerTick((int) (totalTiles / 6));

				// "storage" capacity for resource points for [- X -] tiles
				getCapacity(100);
				getCapacity((int) (totalTiles / 6));
			}

		// Assumes 1 capitol owned
		private static void getResPerTick(int numTilesOwned)
			{
				float grassR = ((grassP / 100) * numTilesOwned) * GRASS[PRODUCTIVITY];
				float stoneR = ((stoneP / 100) * numTilesOwned) * MOUNTAIN[PRODUCTIVITY];
				float farmR = ((farmP / 100) * numTilesOwned) * FARMLAND[PRODUCTIVITY];
				float marshR = ((marshP / 100) * numTilesOwned) * MARSH[PRODUCTIVITY];
				float mangroveR = ((mangroveP / 100) * numTilesOwned) * MANGROVE[PRODUCTIVITY];
				float sandR = ((sandP / 100) * numTilesOwned) * SAND[PRODUCTIVITY];
				float treeR = ((treeP / 100) * numTilesOwned) * FOREST[PRODUCTIVITY];
				float villageR = ((villageP / 100) * numTilesOwned) * VILLAGE[PRODUCTIVITY];
				float oreR = ((oreP / 100) * numTilesOwned) * ORE[PRODUCTIVITY];
				float waterR = ((waterP / 100) * numTilesOwned) * WATER[PRODUCTIVITY];
				float capitalsR = P1[PRODUCTIVITY];

				System.out.println("resPerTick per tile type:");
				System.out.println("when " + numTilesOwned + " tiles are owned by player");
				System.out.println("assuming player owns a proportional percent of each tile");
				System.out.println("");
				System.out.println("GRASS:   " + grassR);
				System.out.println("STONE:   " + stoneR);
				System.out.println("FARM:    " + farmR);
				System.out.println("MARSH:   " + marshR);
				System.out.println("MANGROVE:" + mangroveR);
				System.out.println("SAND:    " + sandR);
				System.out.println("FOREST:  " + treeR);
				System.out.println("VILLAGE: " + villageR);
				System.out.println("ORE:     " + oreR);
				System.out.println("WATER:   " + waterR);
				System.out.println("CAPITALS:" + capitalsR);
				System.out.println("");
				float total = oreR + stoneR + farmR + sandR + waterR + grassR + marshR + mangroveR + capitalsR + treeR + villageR;
				System.out.println("TOTAL:   " + total + "        ( " + (total * 60) + " resPerSecond)");
				System.out.println("");
				System.out.println("####################");
			}

		private static void getCapacity(int numTilesOwned)
			{
				float grassC = ((grassP / 100) * numTilesOwned) * GRASS[CAPACITY];
				float stoneC = ((stoneP / 100) * numTilesOwned) * MOUNTAIN[CAPACITY];
				float farmC = ((farmP / 100) * numTilesOwned) * FARMLAND[CAPACITY];
				float marshC = ((marshP / 100) * numTilesOwned) * MARSH[CAPACITY];
				float mangroveC = ((mangroveP / 100) * numTilesOwned) * MANGROVE[CAPACITY];
				float sandC = ((sandP / 100) * numTilesOwned) * SAND[CAPACITY];
				float treeC = ((treeP / 100) * numTilesOwned) * FOREST[CAPACITY];
				float villageC = ((villageP / 100) * numTilesOwned) * VILLAGE[CAPACITY];
				float oreC = ((oreP / 100) * numTilesOwned) * ORE[CAPACITY];
				float waterC = ((waterP / 100) * numTilesOwned) * WATER[CAPACITY];
				float capitalsC = P1[CAPACITY];

				System.out.println("resource holding capacity per tile type:");
				System.out.println("when " + numTilesOwned + " tiles are owned by player");
				System.out.println("assuming player owns a proportional percent of each tile");
				System.out.println("");
				System.out.println("GRASS:   " + grassC);
				System.out.println("STONE:   " + stoneC);
				System.out.println("FARM:    " + farmC);
				System.out.println("MARSH:   " + marshC);
				System.out.println("MANGROVE:" + mangroveC);
				System.out.println("SAND:    " + sandC);
				System.out.println("FOREST:  " + treeC);
				System.out.println("VILLAGE: " + villageC);
				System.out.println("ORE:     " + oreC);
				System.out.println("WATER:   " + waterC);
				System.out.println("CAPITALS:" + capitalsC);
				System.out.println("");
				float total = oreC + stoneC + farmC + sandC + waterC + grassC + marshC + mangroveC + capitalsC + treeC + villageC;
				System.out.println("TOTAL:   " + total);
				System.out.println("");
				System.out.println("####################");
			}

	}
