package hexNations.Region;

import tools.RandTools;
import hexNations.Constants;
import hexNations.Main;

public class AI implements Constants
	{
		Player p;
		private byte[][] reachable = new byte[Main.region.xRegions][Main.region.yRegions];

		private byte pathValue = 0;
		private byte[][] mapValues = new byte[Main.region.xRegions][Main.region.yRegions];
		private byte[][] path = new byte[Main.region.xRegions][Main.region.yRegions];

		private byte objective = GROW;

		public AI(Player player)
			{
				p = player;

				for (int i = 0; i < Main.region.xRegions; i++)
					for (int j = 0; j < Main.region.yRegions; j++)
						reachable[i][j] = FALSE;
			}

		public void tick()
			{
//				switch (objective)
//					{
//					case GROW:
//						grow();
//						break;
//					case DEFEND:
//						defend();
//						break;
//					case ATTACK:
//						attack();
//						break;
//					}
			}

		private void grow()
			{
				for (int i = 0; i < Main.region.xRegions; i++)
					for (int j = 0; j < Main.region.yRegions; j++)
						if (reachable[i][j] == TRUE)
							if (Main.region.tiles[i][j].meta[ID] == FARMLAND[ID] || Main.region.tiles[i][j].meta[ID] == VILLAGE[ID]
									|| Main.region.tiles[i][j].meta[ID] == FOREST[ID])
								if (RandTools.randPercent() > 99)
									{
										Main.region.tiles[i][j].attackTile(p.playerNum);
										return;
									}
				calculateReach();

				if (pathValue < 0)
					{
						for (int i = 0; i < Main.region.xRegions; i++)
							for (int j = 0; j < Main.region.yRegions; j++)
								if (path[i][j] == pathValue)
									{
										Main.region.tiles[i][j].attackTile(p.playerNum);
										if (Main.region.tiles[i][j].ownerNum == p.playerNum)
											pathValue++;
									}
					}
				else
					{
						for (int i = 0; i < Main.region.xRegions; i++)
							for (int j = 0; j < Main.region.yRegions; j++)
								if (p.discovered[i][j] != UNDISCOVERED)
									if (Main.region.tiles[i][j].meta[PRODUCTIVITY] > 1)
										{
											//calculatePathTo(i, j);
											return;
										}
					}
			}

		private void attack()
			{

			}

		private void defend()
			{

			}

//		private void calculatePathTo(int x, int y)
//			{
//				Tile[][] t = hub.region.tiles;
//				byte[][] pathValues = new byte[hub.region.xRegions][hub.region.yRegions];
//
//				for (int X = 0; X < hub.region.xRegions; X++)
//					for (int Y = 0; Y < hub.region.yRegions; Y++)
//						{
//							if (hub.region.tiles[X][Y].ownerNum == p.playerNum && hub.region.tiles[X][Y].isConnectedToCapital(p.playerNum))
//								{
//									// create array of bytes, one for each tile
//									for (int i = 0; i < hub.region.xRegions; i++)
//										for (int j = 0; j < hub.region.yRegions; j++)
//											{
//												if (hub.region.tiles[i][j].ownerNum == p.playerNum)
//													pathValues[i][j] = hub.region.tiles[i][j].meta[10];
//												else
//													pathValues[i][j] = hub.region.tiles[i][j].meta[WEIGHT];
//											}
//
//									// declare starting tile view range (this
//									// one)
//									pathValues[x][y] = (byte) 100;
//
//									// declare search for line of site
//									// unfinished
//									boolean stillSearching = true;
//
//									while (stillSearching)
//										{
//											// unless there are still tiles to
//											// be
//											// checked will
//											// terminalte loop and return false
//											stillSearching = false;
//
//											// run through array of bytes to see
//											// if any
//											// need
//											// checking
//											for (int i = 0; i < hub.region.xRegions; i++)
//												for (int j = 0; j < hub.region.yRegions; j++)
//													{
//														if (pathValues[i][j] > 0 && pathValues[i][j] != VIEWED)
//															{
//																// unchecked
//																// tile found
//																// so declare
//																// still
//																// searching
//																stillSearching = true;
//
//																// for each
//																// neighboring
//																// tile, add
//																// view range to
//																// range
//																// of this tile
//																// if it is not
//																// VIEWED
//
//																// Check left
//																// and right
//																if (pathValues[i + 1][j] < 0)
//																	pathValues[i + 1][j] = (byte) (pathValues[i + 1][j] + pathValues[i][j]);
//																if (pathValues[i - 1][j] < 0)
//																	pathValues[i - 1][j] = (byte) (pathValues[i - 1][j] + pathValues[i][j]);
//																// check above
//																// and below
//																// (if was a
//																// grid)
//																if (pathValues[i][j + 1] < 0)
//																	pathValues[i][j + 1] = (byte) (pathValues[i][j + 1] + pathValues[i][j]);
//																if (pathValues[i][j - 1] < 0)
//																	pathValues[i][j - 1] = (byte) (pathValues[i][j - 1] + pathValues[i][j]);
//
//																if (j % 2 != 0)
//																	{
//																		// check
//																		// above
//																		// and
//																		// below
//																		// (odd
//																		// lines)
//																		if (pathValues[i + 1][j + 1] < 0)
//																			pathValues[i + 1][j + 1] = (byte) (pathValues[i + 1][j + 1] + pathValues[i][j]);
//																		if (pathValues[i + 1][j - 1] < 0)
//																			pathValues[i + 1][j - 1] = (byte) (pathValues[i + 1][j - 1] + pathValues[i][j]);
//																	}
//																else
//																	{
//																		// check
//																		// above
//																		// and
//																		// below
//																		// (even
//																		// lines)
//																		if (pathValues[i - 1][j - 1] < 0)
//																			pathValues[i - 1][j - 1] = (byte) (pathValues[i - 1][j - 1] + pathValues[i][j]);
//																		if (pathValues[i - 1][j + 1] < 0)
//																			pathValues[i - 1][j + 1] = (byte) (pathValues[i - 1][j + 1] + pathValues[i][j]);
//																	}
//
//																// change state
//																// so not
//																// checked
//																// again next
//																// loop
//																pathValues[i][j] = VIEWED;
//
//																if (pathValues[i][j] > 0)
//																	;// finalValues[i][j]
//																		// =
//																		// DISCOVERED;
//																for (int a = 0; a < hub.region.xRegions; a++)
//																	for (int b = 0; b < hub.region.yRegions; b++)
//																		{
//																			if (pathValues[a][b] > 10);
//																		}
//															}
//													}
//										}
//								}
//						}
//			}

		private void calculateReach()
			{
				for (int i = 0; i < Main.region.xRegions; i++)
					for (int j = 0; j < Main.region.yRegions; j++)
						if (p.discovered[i][j] == DISCOVERED)
							if (Main.region.tiles[i][j].isConnectedToCapital(p.playerNum) && Main.region.tiles[i][j].ownerNum != p.playerNum)
								reachable[i][j] = TRUE;
							else
								reachable[i][j] = FALSE;
			}
	}
