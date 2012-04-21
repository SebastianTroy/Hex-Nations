package HexNations.Region;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import TroysCode.Constants;
import TroysCode.hub;

public class Player implements Constants
	{
		public ArrayList<infoPop> infoPops = new ArrayList<infoPop>();

		public byte playerNum;
		public byte status = PLAYING;
		public byte playerType = CPU;
		public AI ai = new AI(this);

		private int resourcePoints = 50000;
		public int resPerTick = P1[PRODUCTIVITY];
		public int capacity = P1[CAPACITY];

		public int currentPrice = 0;
		public int currentTileProductivity = 0;
		public int tileX = 0;
		public int tileY = 0;

		private int capX;
		private int capY;

		public byte[][] discovered = new byte[hub.region.xRegions][hub.region.yRegions];

		public Player(byte playerNum)
			{
				this.playerNum = playerNum;
				if (playerNum == NATURE)
					{
						for (int x = 0; x < hub.region.xRegions; x++)
							for (int y = 0; y < hub.region.yRegions; y++)
								discovered[x][y] = DISCOVERED;
						playerType = HUMAN;
					}
				else
					for (int x = 0; x < hub.region.xRegions; x++)
						for (int y = 0; y < hub.region.yRegions; y++)
							discovered[x][y] = UNDISCOVERED;
			}

		public void setCapital(int x, int y)
			{
				capX = x;
				capY = y;
				discovered[x][y] = DISCOVERED;
			}

		public void goToCapital()
			{
				hub.region.screenY = 30 * (capY - 7.5f);

				if (capY % 2 != 0)
					hub.region.screenX = (40 * (capX - 10));
				else
					hub.region.screenX = (40 * (capX - 10) - 20);
			}

		public void goToTile(int x, int y)
			{
				hub.region.screenY = 30 * (y - 7.5f);

				if (y % 2 != 0)
					hub.region.screenX = (40 * (x - 10));
				else
					hub.region.screenX = (40 * (x - 10) - 20);
			}

		public void tick()
			{
				if (status == PLAYING)
					if (resourcePoints + resPerTick < capacity * 100)
						resourcePoints += resPerTick;
					else if (resourcePoints < capacity * 100)
						resourcePoints = capacity * 100;
				if (playerType == CPU)
					ai.tick();
			}

		public boolean useResourcePoint(int level)
			{
				if (resourcePoints >= level * INCREMENT)
					{
						resourcePoints -= level * INCREMENT;
						return true;
					}
				else
					return false;
			}

		public void giveResources(byte resources)
			{
				resourcePoints += resources * INCREMENT;
			}

		public int getResourcePoints()
			{
				return resourcePoints;
			}

		public void renderGui(Graphics g)
			{
				g.drawImage(hub.images.gui, 0, 500, hub.renderer);
				g.setColor(Color.BLACK);
				float resPoints = resourcePoints / 1000f;
				g.drawString("" + resPoints, 30, 540);
				g.drawString(" / " + capacity / 10f, 70, 540);
				if (hub.DEBUG)
					g.drawString("" + resPerTick, 30, 560);

				g.drawString("" + currentPrice, 200, 540);
			}

		public void checkVictoryStatus(byte playerNum)
			{
				if (playerNum == NATURE)
					status = SURRENDERED;
				else
					status = DEFEATED;

				for (int i = 0; i < hub.region.xRegions; i++)
					for (int j = 0; j < hub.region.yRegions; j++)
						if (hub.region.tiles[i][j].ownerNum == this.playerNum && hub.region.tiles[i][j].meta[ID] > 11)
							status = PLAYING;

				if (status != PLAYING && this.playerNum != NATURE)
					loseGame();
			}

		private void loseGame()
			{
				for (Player p : hub.region.players)
					p.newInfoPop(playerNum, LOST, capX, capY);
				for (int x = 0; x < hub.region.xRegions; x++)
					for (int y = 0; y < hub.region.yRegions; y++)
						{
							discovered[x][y] = DISCOVERED;
							if (hub.region.tiles[x][y].ownerNum == playerNum)
								hub.region.tiles[x][y].giveToNature(playerNum);
						}
			}

		public void calculateLOS()
			{
				Tile[][] tiles = hub.region.tiles;
				byte[][] finalValues = new byte[hub.region.xRegions][hub.region.yRegions];
				byte[][] viewValues = hub.region.mapLOSValues.clone();

				for (int i = 0; i < hub.region.xRegions; i++)
					for (int j = 0; j < hub.region.yRegions; j++)
						if (discovered[i][j] != UNDISCOVERED)
							finalValues[i][j] = OBSCURED;
						else
							finalValues[i][j] = UNDISCOVERED;

				for (int X = 0; X < hub.region.xRegions; X++)
					for (int Y = 0; Y < hub.region.yRegions; Y++)
						{
							if (hub.region.tiles[X][Y].ownerNum == playerNum && hub.region.tiles[X][Y].isConnectedToCapital(playerNum))
								{
									viewValues = hub.region.mapLOSValues.clone();
									for (int z = 0; z < viewValues.length; z++)
										viewValues[z] = viewValues[z].clone();

									// declare starting tile view range (this
									// one)
									viewValues[X][Y] = (byte) (tiles[X][Y].meta[VIEW] + tiles[X][Y].getBuilding());

									// declare search for line of site
									// unfinished
									boolean stillSearching = true;

									while (stillSearching)
										{
											// unless there are still tiles to
											// be
											// checked will
											// terminalte loop and return false
											stillSearching = false;

											// run through array of bytes to see
											// if any
											// need
											// checking
											for (int i = 0; i < hub.region.xRegions; i++)
												for (int j = 0; j < hub.region.yRegions; j++)
													{
														if (viewValues[i][j] > 0 && viewValues[i][j] != VIEWED)
															{
																// unchecked
																// tile found
																// so declare
																// still
																// searching
																stillSearching = true;

																// for each
																// neighboring
																// tile, add
																// view range to
																// range
																// of this tile
																// if it is not
																// VIEWED

																// Check left
																// and right
																if (viewValues[i + 1][j] < 0)
																	viewValues[i + 1][j] = (byte) (viewValues[i + 1][j] + viewValues[i][j]);
																if (viewValues[i - 1][j] < 0)
																	viewValues[i - 1][j] = (byte) (viewValues[i - 1][j] + viewValues[i][j]);
																// check above
																// and below
																// (if was a
																// grid)
																if (viewValues[i][j + 1] < 0)
																	viewValues[i][j + 1] = (byte) (viewValues[i][j + 1] + viewValues[i][j]);
																if (viewValues[i][j - 1] < 0)
																	viewValues[i][j - 1] = (byte) (viewValues[i][j - 1] + viewValues[i][j]);

																if (j % 2 != 0)
																	{
																		// check
																		// above
																		// and
																		// below
																		// (odd
																		// lines)
																		if (viewValues[i + 1][j + 1] < 0)
																			viewValues[i + 1][j + 1] = (byte) (viewValues[i + 1][j + 1] + viewValues[i][j]);
																		if (viewValues[i + 1][j - 1] < 0)
																			viewValues[i + 1][j - 1] = (byte) (viewValues[i + 1][j - 1] + viewValues[i][j]);
																	}
																else
																	{
																		// check
																		// above
																		// and
																		// below
																		// (even
																		// lines)
																		if (viewValues[i - 1][j - 1] < 0)
																			viewValues[i - 1][j - 1] = (byte) (viewValues[i - 1][j - 1] + viewValues[i][j]);
																		if (viewValues[i - 1][j + 1] < 0)
																			viewValues[i - 1][j + 1] = (byte) (viewValues[i - 1][j + 1] + viewValues[i][j]);
																	}

																// change state
																// so not
																// checked
																// again next
																// loop
																viewValues[i][j] = VIEWED;

																if (viewValues[i][j] > 0)
																	finalValues[i][j] = DISCOVERED;

															}
													}
										}
								}
						}
				for (int i = 0; i < hub.region.xRegions; i++)
					for (int j = 0; j < hub.region.yRegions; j++)
						discovered[i][j] = finalValues[i][j];
			}

		public void newInfoPop(byte player, byte type, int x, int y)
			{
				infoPops.add(new infoPop(750, 0, hub.images.infoPop[type], type, player, x, y));
			}
	}