package hexNations;

import hexNations.Region.Player;
import hexNations.Region.Tile;
import hexNations.Region.InfoPop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.utils.events.TActionEvent;

public class Server extends RenderableObject implements Constants
	{
		private double frameTimer = 0;
		private int frame = 0;

		// Game buttons
		private byte cursor = CAPTURE;

		private final TButton capture = new TButton(500, 530, Images.icons2[CAPTURE])
			{
				@Override
				public void pressed()
					{
						cursor = CAPTURE;
					}
			};
		private final TButton surrender = new TButton(575, 510, Images.icons2[SURRENDER])
			{
				@Override
				public void pressed()
					{
						cursor = SURRENDER;
					}
			};
		private final TButton tower = new TButton(650, 530, Images.icons2[BUILDTOWER])
			{
				@Override
				public void pressed()
					{
						cursor = TOWER;
					}
			};
		private final TButton fortify = new TButton(725, 510, Images.icons2[FORTIFY])
			{
				@Override
				public void pressed()
					{
						cursor = FORTIFY;
					}
			};

		@Override
		public void initiate()
			{
				for (Player p : Main.region.players)
					if (p.playerNum != NATURE)
						p.calculateLOS();

				add(capture);
				add(surrender);
				add(tower);
				add(fortify);
			}

		@Override
		public void refresh()
			{
				Main.region.players[Main.region.currentPlayer].goToCapital();
			}

		@Override
		public void tick(double secondsPassed)
			{
				frameTimer += secondsPassed;
				if (frameTimer > 0.2)
					{
						frameTimer = 0;
						frame++;
						if (frame > 5)
							frame = 0;
					}

				if (Main.input.getKeyState(KeyEvent.VK_UP))
					Main.region.screenY -= secondsPassed * 100;
				if (Main.input.getKeyState(KeyEvent.VK_DOWN))
					Main.region.screenY += secondsPassed * 100;
				if (Main.input.getKeyState(KeyEvent.VK_LEFT))
					Main.region.screenX -= secondsPassed * 100;
				if (Main.input.getKeyState(KeyEvent.VK_RIGHT))
					Main.region.screenX += secondsPassed * 100;

				for (Player p : Main.region.players)
					if (p.status == PLAYING)
						p.tick();

				for (int i = 0; i < Main.region.players[Main.region.currentPlayer].infoPops.size(); i++)
					{
						InfoPop ip = Main.region.players[Main.region.currentPlayer].infoPops.get(i);
						if (ip.getYD() < 455 - (42 * i))
							ip.moveY(6);
					}

				if (Main.region.players[Main.region.currentPlayer].infoPops.size() > 10)
					Main.region.players[Main.region.currentPlayer].infoPops.remove(0);
			}

		@Override
		public void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				int minX = 0;
				int maxX = Main.region.xRegions;
				int minY = 0;
				int maxY = Main.region.yRegions;

				// Only needed for larger maps
				// Code for tile culling
				// check to see if a hexagon is on the screen
				if (Main.region.screenX > 0)
					minX = (int) Main.region.screenX / 40;
				if (Main.region.screenX < ((Main.region.xRegions - 1) * 40) - 800)
					maxX = (int) ((Main.region.screenX + 800) / 40) + 2;

				if (Main.region.screenY > 30)
					minY = (int) (Main.region.screenY / 30) - 1;
				if (Main.region.screenY < (Main.region.yRegions * 30) - 600)
					maxY = (int) (Main.region.screenY + 600) / 30;

				int centerX = 0;
				int centerY = 0;
				int xOffset = (int) -Main.region.screenX;
				int yOffset = (int) -Main.region.screenY;

				for (int i = minY; i < maxY; i++)
					for (int j = minX; j < maxX; j++)
						{

							if (i % 2 == 0)
								{
									centerX = (j * 40) + (xOffset - 20);
									centerY = (i * 30) + yOffset;
								}
							else
								{
									centerX = (j * 40) + xOffset;
									centerY = (i * 30) + yOffset;
								}

							if (Main.region.players[Main.region.currentPlayer].discovered[j][i] == DISCOVERED)
								{
									g.drawImage(Images.tiles[Main.region.tiles[j][i].meta[ID]][getFrame(Main.region.tiles[j][i].frame)], centerX, centerY, getObserver());

									if (Main.region.tiles[j][i].ownerNum != NATURE)
										g.drawImage(Images.boundaries[COLOURS[Main.region.tiles[j][i].ownerNum]], centerX, centerY, getObserver());

									if (Main.region.tiles[j][i].getBuilding() == TOWER)
										g.drawImage(Images.icons2[2], centerX, centerY, getObserver());
									if (Main.region.tiles[j][i].getBuilding() == FORTIFIED)
										{
											g.drawImage(Images.icons2[5 + Main.region.tiles[j][i].ownerNum], centerX, centerY, getObserver());
											g.drawImage(Images.icons2[4], centerX, centerY, getObserver());
										}
								}
							else if (Main.region.players[Main.region.currentPlayer].discovered[j][i] == OBSCURED)
								{
									g.drawImage(Images.tiles[Main.region.tiles[j][i].meta[ID]][frame], centerX, centerY, getObserver());
									g.drawImage(Images.tiles[FOG_OF_WAR[ID]][frame], centerX, centerY, getObserver());
								}

							Player p = Main.region.players[Main.region.currentPlayer];
							if (p.tileY == i && p.tileX == j)
								{
									if (cursor == CAPTURE)
										g.drawImage(Images.tiles[HASH[ID]][frame], centerX, centerY, getObserver());
									else if (cursor == SURRENDER)
										g.drawImage(Images.icons2[SURRENDER], centerX, centerY, getObserver());
									else if (cursor == BUILDTOWER && Main.region.tiles[j][i].canBuildTower(p.playerNum))
										g.drawImage(Images.icons2[BUILDTOWER], centerX, centerY, getObserver());
									else if (cursor == BUILDTOWER)
										g.drawImage(Images.icons2[NO_BUILDTOWER], centerX, centerY, getObserver());
									else if (cursor == FORTIFY && Main.region.tiles[j][i].canFortify(p.playerNum))
										{
											g.drawImage(Images.icons2[5 + Main.region.currentPlayer], centerX, centerY, getObserver());
											g.drawImage(Images.icons2[FORTIFY], centerX, centerY, getObserver());
										}
									else if (cursor == FORTIFY)
										{
											g.drawImage(Images.icons2[5 + Main.region.currentPlayer], centerX, centerY, getObserver());
											g.drawImage(Images.icons2[NO_FORTIFY], centerX, centerY, getObserver());
										}
								}
						}

				Main.region.players[Main.region.currentPlayer].renderGui(g);
				// Draws background to fortify button
				g.drawImage(Images.icons2[5 + Main.region.currentPlayer], 725, 510, getObserver());
				// draws gui
				g.drawString("Capture", 504, 525);
				g.drawString("Surrender", 570, 565);
				g.drawString("Build Tower", 637, 525);
				g.drawString("Fortify", 731, 565);

				for (int i = 0; i < Main.region.players[Main.region.currentPlayer].infoPops.size(); i++)
					{
						InfoPop ip = Main.region.players[Main.region.currentPlayer].infoPops.get(i);
						if (ip.type == LOST || (ip.player == Main.region.currentPlayer && ip.type == FAILED) || (ip.player == Main.region.currentPlayer && ip.type == ATTACKED))
							ip.render(g);
					}
			}

		private int getFrame(int modifier)
			{
				return (frame + modifier) % 5;
			}

		private boolean isMouseOnMap(int x, int y)
			{
				int mapX = (int) (x + Main.region.screenX);
				int mapY = (int) (y + Main.region.screenY);

				if (mapX < 61 || mapX > (Main.region.xRegions * 40) - 61)
					return false;

				if (mapY < 41 || mapY > (Main.region.yRegions * 30) - 41)
					return false;

				return true;
			}

		private Tile getSelectedTile(int x, int y)
			{
				int mouseX = (int) (x + Main.region.screenX);
				int mouseY = (int) (y + Main.region.screenY);

				int row = (int) mouseY / 30;
				int column = (row % 2 != 0 ? (int) (mouseX / 40) : (int) (mouseX + 20) / 40);

				int lineY = mouseY - (row * 30);
				int lineX = (row % 2 != 0 ? mouseX - (column * 40) : mouseX - (column * 40) + 20);

				if (lineY + (lineX / 2) < 10) // LEFT triangle
					mouseY -= 20;

				else if (lineY - (lineX / 2) < -10) // RIGHT triangle
					mouseY -= 20;

				Player p = Main.region.players[Main.region.currentPlayer];

				p.tileY = (int) mouseY / 30;
				p.tileX = (p.tileY % 2 != 0 ? (int) (mouseX / 40) : (int) (mouseX + 20) / 40);

				return Main.region.tiles[p.tileX][p.tileY];
			}

		@Override
		public void mouseMoved(MouseEvent me)
			{
				if (isMouseOnMap(me.getX(), me.getY()))
					{
						Player p = Main.region.players[Main.region.currentPlayer];
						Tile t = getSelectedTile(me.getX(), me.getY());

						int price = 0;

						if (cursor == CAPTURE)
							price = t.isConnectedToCapital(p.playerNum) && t.meta[CAPTURABLE] == TRUE ? t.priceToCapture(p.playerNum) : 0;

						else if (cursor == SURRENDER)
							;

						else if (cursor == BUILDTOWER)
							price = t.ownerNum == p.playerNum && t.isConnectedToCapital(p.playerNum) ? t.priceToBuild(TOWER) : 0;

						else if (cursor == FORTIFY)
							price = t.ownerNum == p.playerNum && t.isConnectedToCapital(p.playerNum) ? t.priceToBuild(FORTIFIED) : 0;

						Main.region.players[Main.region.currentPlayer].currentPrice = price;
					}
			}

		@Override
		public void mousePressed(MouseEvent me)
			{
				Player p = Main.region.players[Main.region.currentPlayer];

				if (p.status == PLAYING)
					if (me.getButton() == 1)
						{
							if (cursor == CAPTURE)
								Main.region.tiles[p.tileX][p.tileY].attackTile(Main.region.currentPlayer);

							else if (cursor == SURRENDER)
								Main.region.tiles[p.tileX][p.tileY].giveToNature(p.playerNum);

							else if (cursor == BUILDTOWER)
								if (Main.region.tiles[p.tileX][p.tileY].canBuildTower(p.playerNum))
									{
										Main.region.tiles[p.tileX][p.tileY].build(TOWER);
										p.useResourcePoint(Main.region.tiles[p.tileX][p.tileY].priceToBuild(TOWER));
										p.calculateLOS();
									}

							if (cursor == FORTIFY)
								if (Main.region.tiles[p.tileX][p.tileY].canFortify(p.playerNum))
									{
										Main.region.tiles[p.tileX][p.tileY].build(FORTIFIED);
										p.useResourcePoint(Main.region.tiles[p.tileX][p.tileY].priceToBuild(FORTIFIED));
										p.calculateLOS();
									}
						}
					else if (me.getButton() == MouseEvent.BUTTON3)
						{
							Main.region.screenX = 40 * (p.tileX - 10);
							Main.region.screenY = 30 * (p.tileY - 7.5f);
						}

			}

		@Override
		public void keyPressed(KeyEvent ke)
			{
				switch (ke.getKeyCode())
					{
						case (KeyEvent.VK_H):
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
						case (KeyEvent.VK_0):
							Main.region.currentPlayer = NATURE;
							break;
						case (KeyEvent.VK_1):
							Main.region.currentPlayer = PLAYER_ONE;
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
						case (KeyEvent.VK_2):
							Main.region.currentPlayer = PLAYER_TWO;
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
						case (KeyEvent.VK_3):
							if (Main.region.numPlayers > 2)
								Main.region.currentPlayer = PLAYER_THREE;
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
						case (KeyEvent.VK_4):
							if (Main.region.numPlayers > 3)
								Main.region.currentPlayer = PLAYER_FOUR;
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
						case (KeyEvent.VK_5):
							if (Main.region.numPlayers > 4)
								Main.region.currentPlayer = PLAYER_FIVE;
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
						case (KeyEvent.VK_6):
							if (Main.region.numPlayers == 6)
								Main.region.currentPlayer = PLAYER_SIX;
							Main.region.players[Main.region.currentPlayer].goToCapital();
							break;
					}
			}

		@Override
		public void tActionEvent(TActionEvent event)
			{
				Player p = Main.region.players[Main.region.currentPlayer];

				for (InfoPop pop : p.infoPops)
					{
						if (event.getSource() == pop)
							{
								p.goToTile(pop.tileX, pop.tileY);
								p.infoPops.remove(pop);
								return;
							}
					}
			}
	}