package HexNations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import HexNations.Region.Region;
import TroysCode.RenderableObject;
import TroysCode.Tools;
import TroysCode.hub;
import TroysCode.T.TButton;
import TroysCode.T.TScrollEvent;
import TroysCode.T.TSlider;

public class NewGameCreator extends RenderableObject
	{
		private static final long serialVersionUID = 1L;

		private final byte playerScroll = 0;
		private final byte mapScroll = 1;
		private final byte mountain = 2;
		private final byte forest = 3;
		private final byte desert = 4;
		private final byte water = 5;

		private final TSlider[] sliders = { new TSlider(40, 65, TSlider.HORIZONTAL, 450, 1), new TSlider(40, 150, TSlider.HORIZONTAL, 450, 1),
				new TSlider(40, 235, TSlider.HORIZONTAL, 450, 1), new TSlider(40, 320, TSlider.HORIZONTAL, 450, 1),
				new TSlider(40, 405, TSlider.HORIZONTAL, 450, 1), new TSlider(40, 490, TSlider.HORIZONTAL, 450, 1) };

		private final TButton begin = new TButton(650, 50, hub.images.begin);

		private byte players = 2;
		private int mapSize = 100;
		private byte mountainPercent = 10;
		private byte forestPercent = 10;
		private byte desertPercent = 0;
		private byte waterPercent = 5;

		@Override
		public void initiate()
			{
				for (TSlider s : sliders)
					addTComponent(s);

				addTComponent(begin);
			}

		@Override
		public void refresh()
			{
				updateValues();
			}

		@Override
		public void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.WHITE);
				g.drawString("Number of players", 40, 60);
				g.drawString(players + " players", 500, 80);

				g.drawString("Map size - per player", 40, 145);
				g.drawString(mapSize + " tiles per player", 500, 165);

				g.drawString("Mountain Coverage", 40, 230);
				g.drawString(mountainPercent + "%", 500, 250);

				g.drawString("Forest Coverage", 40, 315);
				g.drawString(forestPercent + "%", 500, 335);

				g.drawString("Desert Coverage", 40, 400);
				g.drawString(desertPercent + "%", 500, 420);

				g.drawString("Water coverage", 40, 485);
				g.drawString(waterPercent + "%", 500, 505);
			}

		private void updateValues()
			{
				players = (byte) (sliders[playerScroll].getSliderPercent(0) * 0.04 + 2);

				mapSize = (int) (sliders[mapScroll].getSliderPercent(0) * 3.5 + 250);

				mountainPercent = (byte) (sliders[mountain].getSliderPercent(0) * 0.42 + 10);

				forestPercent = (byte) (sliders[forest].getSliderPercent(0) * 0.28 + 15);

				desertPercent = (byte) (sliders[desert].getSliderPercent(0) * 0.2 + 0);

				waterPercent = (byte) (sliders[water].getSliderPercent(0) * 0.18 + 5);
			}

		@Override
		public void tScrollBarScrolled(TScrollEvent event)
			{
				updateValues();
			}

		@Override
		protected void tick()
			{
				// TODO Auto-generated method stub

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
		protected void mouseMoved(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mouseWheelMoved(MouseWheelEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void actionPerformed(ActionEvent event)
			{
				if (event.getSource() == begin)
					{
						hub.region = new Region(players, mapSize, mountainPercent, forestPercent, desertPercent, waterPercent);
						hub.region.prepareRegion();
						changeRenderableObject(hub.background);
					}
			}

		@Override
		protected void keyPressed(KeyEvent event)
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void keyReleased(KeyEvent event)
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
		protected void mousePressed(MouseEvent event)
			{
				// TODO Auto-generated method stub

			}
	}
