package hexNations.Region;

import tools.Rand;
import hexNations.Constants;
import hexNations.Main;

public class Tile implements Constants
	{
		public byte[] meta;
		public byte frame = (byte) Rand.int_(0, 6);

		private int x;
		private int y;

		private byte building = NONE;
		private byte resources;

		public byte ownerNum = NATURE;

		public Tile(int i, int j, byte[] meta)
			{
				this.meta = meta;
				resources = meta[RESOURCES];

				x = i;
				y = j;
			}

		public boolean canBuildTower(byte playerNum)
			{
				if (isConnectedToCapital(playerNum) == false)
					return false;
				if (building != NONE)
					return false;
				if (playerNum != ownerNum)
					return false;
				if (Main.region.players[playerNum].getResourcePoints() < priceToBuild(TOWER) * INCREMENT)
					return false;

				return true;
			}

		public boolean canFortify(byte playerNum)
			{
				if (isConnectedToCapital(playerNum) == false)
					return false;
				if (building != NONE)
					return false;
				if (playerNum != ownerNum)
					return false;
				if (Main.region.players[playerNum].getResourcePoints() < priceToBuild(FORTIFIED) * INCREMENT)
					return false;

				return true;
			}

		public byte priceToBuild(byte building)
			{
				if (building == TOWER)
					return (byte) (building + (meta[BASELEVEL] / 2));
				else
					return (byte) (building + (meta[BASELEVEL] / 2) + 5);
			}

		public void build(byte building)
			{
				this.building = building;
			}

		public byte getBuilding()
			{
				return building;
			}

		public void attackTile(byte playerNum)
			{
				if (meta[CAPTURABLE] == TRUE && priceToCapture(playerNum) != 0 && isConnectedToCapital(playerNum))
					{
						if (Main.region.players[playerNum].useResourcePoint(priceToCapture(playerNum)))
							{
								if (ownerNum != NATURE)
									Main.region.players[ownerNum].newInfoPop(ownerNum, ATTACKED, x, y);

								byte percentNeeded = 0;
								// if tile is owned, small chance attack
								// repelled
								if (ownerNum != NATURE)
									percentNeeded += meta[BASELEVEL] + 8;
								// if tile has tower, medium chance attack
								// is repelled
								if (building == TOWER)
									percentNeeded += 21;

								// if tile is fortified, large chance of
								// attack repelled
								else if (building == FORTIFIED)
									percentNeeded += 32 - (meta[BASELEVEL] / 3);

								// if tile is capitol, very large
								// chance attack is repelled

								if (Rand.percent() > percentNeeded)
									{

										// First deal with previous owner
										Main.region.players[ownerNum].resPerTick -= meta[PRODUCTIVITY];
										Main.region.players[ownerNum].capacity -= meta[CAPACITY];

										// Deal with intermediates
										if (Rand.percent() > 80 || building == FORTIFIED)
											building = NONE;
										ownerNum = playerNum;

										// Then deal with new owner
										Main.region.players[playerNum].giveResources(resources);
										Main.region.players[playerNum].resPerTick += meta[PRODUCTIVITY];
										Main.region.players[playerNum].capacity += meta[CAPACITY];

										// deplete resources
										resources = 0;

										// calculate new LOS
										Main.region.players[playerNum].calculateLOS();

										// if the tile is a capital, check
										// victory conditions
										if (meta[ID] > 11)
											for (Player p : Main.region.players)
												p.checkVictoryStatus(playerNum);
									}
								else
									Main.region.players[playerNum].newInfoPop(playerNum, FAILED, x, y);
							}
					}
			}

		public void giveToNature(byte oldOwnerNum)
			{
				if (isConnectedToCapital(oldOwnerNum))
					{
						if (oldOwnerNum != NATURE)
							{
								// First deal with previous owner
								if (Main.region.players[oldOwnerNum].status == PLAYING)
									{
										Main.region.players[ownerNum].resPerTick -= meta[PRODUCTIVITY];
										Main.region.players[ownerNum].capacity -= meta[CAPACITY];
									}

								// Deal with intermediates
								if (Rand.percent() > 80 || building == FORTIFIED)
									building = NONE;
								ownerNum = NATURE;

								Main.region.players[oldOwnerNum].calculateLOS();

								// if tile is a capital, check victory
								// conditions
								if (meta[ID] > 11)
									for (Player p : Main.region.players)
										p.checkVictoryStatus(NATURE);
							}
					}
			}

		public boolean isConnectedToCapital(byte playerNum)
			{
				if (playerNum == NATURE)
					return false;

				Tile[][] tiles = Main.region.tiles;
				byte[][] state = new byte[Main.region.xRegions][Main.region.yRegions];

				// create array of bytes, one for each tile
				for (int i = 0; i < Main.region.xRegions; i++)
					for (int j = 0; j < Main.region.yRegions; j++)
						state[i][j] = UNCONNECTED;

				// declare current tile needs checking (this one)
				state[x][y] = UNCHECKED;

				// declare search for link to capital unfinished
				boolean stillSearching = true;

				while (stillSearching)
					{
						// unless there are still tiles to be checked will
						// terminalte loop and return false
						stillSearching = false;

						// run through array of bytes to see if any need
						// checking
						for (int i = 0; i < Main.region.xRegions; i++)
							for (int j = 0; j < Main.region.yRegions; j++)
								{
									if (state[i][j] == UNCHECKED)
										{
											// If capital - return true
											if (tiles[i][j].meta[ID] > 11 && tiles[i][j].ownerNum == playerNum)
												return true;
											// else declare still searching
											stillSearching = true;

											// increment state so not checked
											// next loop
											state[i][j] = CHECKED;

											// check neighboring tiles to see if
											// they need to be checked next loop

											// Check left and right
											if (tiles[i + 1][j].ownerNum == playerNum)
												if (state[i + 1][j] == 0)
													state[i + 1][j] = UNCHECKED;
											if (tiles[i - 1][j].ownerNum == playerNum)
												if (state[i - 1][j] == 0)
													state[i - 1][j] = UNCHECKED;
											// check above and below (if was a
											// grid)
											if (tiles[i][j + 1].ownerNum == playerNum)
												if (state[i][j + 1] == 0)
													state[i][j + 1] = UNCHECKED;
											if (tiles[i][j - 1].ownerNum == playerNum)
												if (state[i][j - 1] == 0)
													state[i][j - 1] = UNCHECKED;

											if (j % 2 != 0)
												{
													// check above and below
													// (odd lines)
													if (tiles[i + 1][j + 1].ownerNum == playerNum)
														if (state[i + 1][j + 1] == 0)
															state[i + 1][j + 1] = UNCHECKED;
													if (tiles[i + 1][j - 1].ownerNum == playerNum)
														if (state[i + 1][j - 1] == 0)
															state[i + 1][j - 1] = UNCHECKED;
												}
											else
												{
													// check above and below
													// (even lines)
													if (tiles[i - 1][j - 1].ownerNum == playerNum)
														if (state[i - 1][j - 1] == 0)
															state[i - 1][j - 1] = UNCHECKED;
													if (tiles[i - 1][j + 1].ownerNum == playerNum)
														if (state[i - 1][j + 1] == 0)
															state[i - 1][j + 1] = UNCHECKED;
												}
										}
								}
					}
				return false;
			}

		public int priceToCapture(byte playerNum)
			{
				int points = 0;

				if (ownerNum == playerNum)
					return points;

				if (ownerNum == NATURE)
					points += meta[BASELEVEL];

				else
					{
						points += meta[BASELEVEL];
						points += 4;
					}

				if (building == TOWER)
					points += 7;

				else if (building == FORTIFIED)
					points += 15;

				return points;
			}

		public void changeMeta(byte[] newMeta)
			{
				this.meta = newMeta;
			}
	}
