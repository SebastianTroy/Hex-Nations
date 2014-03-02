package hexNations;

import hexNations.Region.Region;

import java.awt.Color;
import java.awt.Graphics2D;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TSlider;
import tComponents.utils.events.TScrollEvent;

public class NewGameCreator extends RenderableObject
	{
		private final byte playerScroll = 0;
		private final byte mapScroll = 1;
		private final byte mountain = 2;
		private final byte forest = 3;
		private final byte desert = 4;
		private final byte water = 5;

		private final TSlider[] sliders = { new TSlider(40, 65, 450, TSlider.HORIZONTAL), new TSlider(40, 150, 450, TSlider.HORIZONTAL), new TSlider(40, 235, 450, TSlider.HORIZONTAL),
				new TSlider(40, 320, 450, TSlider.HORIZONTAL), new TSlider(40, 405, 450, TSlider.HORIZONTAL), new TSlider(40, 490, 450, TSlider.HORIZONTAL) };

		private final TButton begin = new TButton(650, 50, Images.begin)
			{
				@Override
				public final void pressed()
					{
						Main.region = new Region(players, mapSize, mountainPercent, forestPercent, desertPercent, waterPercent);
						Main.region.prepareRegion();
						changeRenderableObject(new Server());
					}
			};

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
					add(s);

				add(begin);
			}

		@Override
		public void refresh()
			{
				players = (byte) (sliders[playerScroll].getPercent(0) * 0.04 + 2);

				mapSize = (int) (sliders[mapScroll].getPercent(0) * 3.5 + 250);

				mountainPercent = (byte) (sliders[mountain].getPercent(0) * 0.42 + 10);

				forestPercent = (byte) (sliders[forest].getPercent(0) * 0.28 + 15);

				desertPercent = (byte) (sliders[desert].getPercent(0) * 0.2 + 0);

				waterPercent = (byte) (sliders[water].getPercent(0) * 0.18 + 5);
			}

		@Override
		public void render(Graphics2D g)
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

		@Override
		public void tScrollEvent(TScrollEvent event)
			{
				refresh();
			}
	}