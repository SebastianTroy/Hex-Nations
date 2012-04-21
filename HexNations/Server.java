package HexNations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import HexNations.Region.Player;
import HexNations.Region.Tile;
import HexNations.Region.infoPop;
import TroysCode.Constants;
import TroysCode.RenderableObject;
import TroysCode.hub;
import TroysCode.T.TButton;
import TroysCode.T.TScrollEvent;

public class Server extends RenderableObject implements Constants
	{
		private static final long serialVersionUID = 1L;

		private byte upDown = NO_KEY;
		private byte leftRight = NO_KEY;

		private byte frameTimer = 0;
		private byte frame = 0;

		// Game buttons
		private byte cursor = CAPTURE;

		private final TButton capture = new TButton(500, 530, hub.images.icons2[CAPTURE]);
		private final TButton surrender = new TButton(575, 510, hub.images.icons2[SURRENDER]);
		private final TButton tower = new TButton(650, 530, hub.images.icons2[BUILDTOWER]);
		private final TButton fortify = new TButton(725, 510, hub.images.icons2[FORTIFY]);

		@Override
		public void initiate()
			{
				for (Player p : hub.region.players)
					if (p.playerNum != NATURE)
						p.calculateLOS();
				
				addTComponent(capture);
				addTComponent(surrender);
				addTComponent(tower);
				addTComponent(fortify);
			}

		@Override
		public void refresh()
			{
				hub.region.players[hub.region.currentPlayer].goToCapital();
			}

		@Override
		public void tick()
			{
				calculateFrame();

				if (upDown != NO_KEY)
					if (upDown == UP)
						hub.region.screenY -= 6;
					else if (upDown == DOWN)
						hub.region.screenY += 6;

				if (leftRight != NO_KEY)
					if (leftRight == LEFT)
						hub.region.screenX -= 6;
					else if (leftRight == RIGHT)
						hub.region.screenX += 6;

				for (Player p : hub.region.players)
					if (p.status == PLAYING)
						p.tick();

				for (int i = 0; i < hub.region.players[hub.region.currentPlayer].infoPops.size(); i++)
					{
						infoPop ip = hub.region.players[hub.region.currentPlayer].infoPops.get(i);
						if (ip.getY() < 455 - (42 * i))
							ip.moveY(6);
					}

				if (hub.region.players[hub.region.currentPlayer].infoPops.size() > 10)
					hub.region.players[hub.region.currentPlayer].infoPops.remove(0);
			}

		@Override
		public void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				int minX = 0;
				int maxX = hub.region.xRegions;
				int minY = 0;
				int maxY = hub.region.yRegions;

				// Only needed for larger maps
				// Code for tile culling
				// check to see if a hexagon is on the screen
				if (hub.region.screenX > 0)
					minX = (int) hub.region.screenX / 40;
				if (hub.region.screenX < ((hub.region.xRegions - 1) * 40) - 800)
					maxX = (int) ((hub.region.screenX + 800) / 40) + 2;

				if (hub.region.screenY > 30)
					minY = (int) (hub.region.screenY / 30) - 1;
				if (hub.region.screenY < (hub.region.yRegions * 30) - 600)
					maxY = (int) (hub.region.screenY + 600) / 30;

				int centerX = 0;
				int centerY = 0;
				int xOffset = (int) -hub.region.screenX;
				int yOffset = (int) -hub.region.screenY;

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

							if (hub.region.players[hub.region.currentPlayer].discovered[j][i] == DISCOVERED)
								{
									g.drawImage(hub.images.tiles[hub.region.tiles[j][i].meta[ID]][getFrame(hub.region.tiles[j][i].frame)], centerX, centerY,
											hub.renderer);

									if (hub.region.tiles[j][i].ownerNum != NATURE)
										g.drawImage(hub.images.boundaries[COLOURS[hub.region.tiles[j][i].ownerNum]], centerX, centerY, hub.renderer);

									if (hub.region.tiles[j][i].getBuilding() == TOWER)
										g.drawImage(hub.images.icons2[2], centerX, centerY, hub.renderer);
									if (hub.region.tiles[j][i].getBuilding() == FORTIFIED)
										{
											g.drawImage(hub.images.icons2[5 + hub.region.tiles[j][i].ownerNum], centerX, centerY, hub.renderer);
											g.drawImage(hub.images.icons2[4], centerX, centerY, hub.renderer);
										}
								}
							else if (hub.region.players[hub.region.currentPlayer].discovered[j][i] == OBSCURED)
								{
									g.drawImage(hub.images.tiles[hub.region.tiles[j][i].meta[ID]][frame], centerX, centerY, hub.renderer);
									g.drawImage(hub.images.tiles[FOG_OF_WAR[ID]][frame], centerX, centerY, hub.renderer);
								}

							Player p = hub.region.players[hub.region.currentPlayer];
							if (p.tileY == i && p.tileX == j)
								{
									if (cursor == CAPTURE)
										g.drawImage(hub.images.tiles[HASH[ID]][frame], centerX, centerY, hub.renderer);
									else if (cursor == SURRENDER)
										g.drawImage(hub.images.icons2[SURRENDER], centerX, centerY, hub.renderer);
									else if (cursor == BUILDTOWER && hub.region.tiles[j][i].canBuildTower(p.playerNum))
										g.drawImage(hub.images.icons2[BUILDTOWER], centerX, centerY, hub.renderer);
									else if (cursor == BUILDTOWER)
										g.drawImage(hub.images.icons2[NO_BUILDTOWER], centerX, centerY, hub.renderer);
									else if (cursor == FORTIFY && hub.region.tiles[j][i].canFortify(p.playerNum))
										{
											g.drawImage(hub.images.icons2[5 + hub.region.currentPlayer], centerX, centerY, hub.renderer);
											g.drawImage(hub.images.icons2[FORTIFY], centerX, centerY, hub.renderer);
										}
									else if (cursor == FORTIFY)
										{
											g.drawImage(hub.images.icons2[5 + hub.region.currentPlayer], centerX, centerY, hub.renderer);
											g.drawImage(hub.images.icons2[NO_FORTIFY], centerX, centerY, hub.renderer);
										}
								}
						}

				hub.region.players[hub.region.currentPlayer].renderGui(g);
				// Draws background to fortify button
				g.drawImage(hub.images.icons2[5 + hub.region.currentPlayer], 725, 510, hub.renderer);
				// draws gui
				g.drawString("Capture", 504, 525);
				g.drawString("Surrender", 570, 565);
				g.drawString("Build Tower", 637, 525);
				g.drawString("Fortify", 731, 565);

				for (int i = 0; i < hub.region.players[hub.region.currentPlayer].infoPops.size(); i++)
					{
						infoPop ip = hub.region.players[hub.region.currentPlayer].infoPops.get(i);
						if (ip.type == LOST || (ip.player == hub.region.currentPlayer && ip.type == FAILED)
								|| (ip.player == hub.region.currentPlayer && ip.type == ATTACKED))
							ip.render(g);
					}
			}

		private void calculateFrame()
			{
				frameTimer++;
				if (frameTimer == 15)
					{
						frameTimer = 0;
						frame++;
						if (frame > 5)
							frame = 0;
					}
			}

		private int getFrame(byte mod)
			{
				int frame = this.frame;

				frame += mod;

				if (frame > 5)
					frame -= 6;

				return frame;
			}

		private boolean isMouseOnMap(int x, int y)
			{
				int mapX = (int) (x + hub.region.screenX);
				int mapY = (int) (y + hub.region.screenY);

				if (mapX < 61 || mapX > (hub.region.xRegions * 40) - 61)
					return false;

				if (mapY < 41 || mapY > (hub.region.yRegions * 30) - 41)
					return false;

				return true;
			}

		private Tile getSelectedTile(int x, int y)
			{
				int mouseX = (int) (x + hub.region.screenX);
				int mouseY = (int) (y + hub.region.screenY);

				int row = (int) mouseY / 30;
				int column = (row % 2 != 0 ? (int) (mouseX / 40) : (int) (mouseX + 20) / 40);

				int lineY = mouseY - (row * 30);
				int lineX = (row % 2 != 0 ? mouseX - (column * 40) : mouseX - (column * 40) + 20);

				if (lineY + (lineX / 2) < 10) // LEFT triangle
					mouseY -= 20;

				else if (lineY - (lineX / 2) < -10) // RIGHT triangle
					mouseY -= 20;

				Player p = hub.region.players[hub.region.currentPlayer];

				p.tileY = (int) mouseY / 30;
				p.tileX = (p.tileY % 2 != 0 ? (int) (mouseX / 40) : (int) (mouseX + 20) / 40);

				return hub.region.tiles[p.tileX][p.tileY];
			}

		@Override
		public void mouseMoved(MouseEvent me)
			{
				if (isMouseOnMap(me.getX(), me.getY()))
					{
						Player p = hub.region.players[hub.region.currentPlayer];
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

						hub.region.players[hub.region.currentPlayer].currentPrice = price;
					}
			}

		@Override
		public void mousePressed(MouseEvent me)
			{
				Player p = hub.region.players[hub.region.currentPlayer];

				if (p.status == PLAYING)
					if (me.getButton() == 1)
						{
							if (cursor == CAPTURE)
								hub.region.tiles[p.tileX][p.tileY].attackTile(hub.region.currentPlayer);

							else if (cursor == SURRENDER)
								hub.region.tiles[p.tileX][p.tileY].giveToNature(p.playerNum);

							else if (cursor == BUILDTOWER)
								if (hub.region.tiles[p.tileX][p.tileY].canBuildTower(p.playerNum))
									{
										hub.region.tiles[p.tileX][p.tileY].build(TOWER);
										p.useResourcePoint(hub.region.tiles[p.tileX][p.tileY].priceToBuild(TOWER));
										p.calculateLOS();
									}

							if (cursor == FORTIFY)
								if (hub.region.tiles[p.tileX][p.tileY].canFortify(p.playerNum))
									{
										hub.region.tiles[p.tileX][p.tileY].build(FORTIFIED);
										p.useResourcePoint(hub.region.tiles[p.tileX][p.tileY].priceToBuild(FORTIFIED));
										p.calculateLOS();
									}
						}
					else if (me.getButton() == MouseEvent.BUTTON3)
						{
							hub.region.screenX = 40 * (p.tileX - 10);
							hub.region.screenY = 30 * (p.tileY - 7.5f);
						}

			}

		@Override
		public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == 72) // 'H key'
					{
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}
				if (ke.getKeyCode() == 48) // '0 key'
					{
						hub.region.currentPlayer = NATURE;
						return;
					}
				if (ke.getKeyCode() == 49) // '1 key'
					{
						hub.region.currentPlayer = PLAYER_ONE;
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}
				if (ke.getKeyCode() == 50) // '2 key'
					{
						hub.region.currentPlayer = PLAYER_TWO;
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}
				if (ke.getKeyCode() == 51) // '3 key'
					{
						if (hub.region.numPlayers > 2)
							hub.region.currentPlayer = PLAYER_THREE;
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}
				if (ke.getKeyCode() == 52) // '4 key'
					{
						if (hub.region.numPlayers > 3)
							hub.region.currentPlayer = PLAYER_FOUR;
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}
				if (ke.getKeyCode() == 53) // '5 key'
					{
						if (hub.region.numPlayers > 4)
							hub.region.currentPlayer = PLAYER_FIVE;
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}
				if (ke.getKeyCode() == 54) // '6 key'
					{
						if (hub.region.numPlayers == 6)
							hub.region.currentPlayer = PLAYER_SIX;
						hub.region.players[hub.region.currentPlayer].goToCapital();
						return;
					}

				if (ke.getKeyCode() == 38 || ke.getKeyCode() == 87) // '^ key'
					{
						upDown = UP;
						return;
					}
				else if (ke.getKeyCode() == 40 || ke.getKeyCode() == 83) // 'v
																			// key'
					{
						upDown = DOWN;
						return;
					}
				if (ke.getKeyCode() == 37 || ke.getKeyCode() == 65) // '< key'
					{
						leftRight = LEFT;
						return;
					}
				else if (ke.getKeyCode() == 39 || ke.getKeyCode() == 68) // '>
																			// key'
					{
						leftRight = RIGHT;
						return;
					}
			}

		@Override
		public void keyReleased(KeyEvent ke)
			{
				if (ke.getKeyCode() == 38) // '^ key'
					{
						if (upDown == UP)
							upDown = NO_KEY;
						return;
					}
				else if (ke.getKeyCode() == 40) // 'v key'
					{
						if (upDown == DOWN)
							upDown = NO_KEY;
						return;
					}
				if (ke.getKeyCode() == 37) // '< key'
					{
						if (leftRight == LEFT)
							leftRight = NO_KEY;
						return;
					}
				else if (ke.getKeyCode() == 39) // '> key'
					{
						if (leftRight == RIGHT)
							leftRight = NO_KEY;
						return;
					}
			}

		@Override
		protected void actionPerformed(ActionEvent event)
			{
				Player p = hub.region.players[hub.region.currentPlayer];

				for (infoPop pop : p.infoPops)
					{
						if (event.getSource() == pop)
							{
								p.goToTile(pop.tileX, pop.tileY);
								p.infoPops.remove(pop);
								return;
							}
					}

				if (event.getSource() == capture)
					cursor = CAPTURE;
				else if (event.getSource() == surrender)
					cursor = SURRENDER;
				else if (event.getSource() == tower)
					cursor = BUILDTOWER;
				else if (event.getSource() == fortify)
					cursor = FORTIFY;
			}

		@Override
		protected void mouseReleased(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseDragged(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseWheelMoved(MouseWheelEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyTyped(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseClicked(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseEntered(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseExited(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void programGainedFocus(WindowEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void programLostFocus(WindowEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void frameResized(ComponentEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void tScrollBarScrolled(TScrollEvent event)
			{
				// TODO Auto-generated method stub

			}
	}