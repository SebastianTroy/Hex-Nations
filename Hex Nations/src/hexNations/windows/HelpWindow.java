package hexNations.windows;

import hexNations.AssetLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import tCode.RenderableObject;

public class HelpWindow extends RenderableObject
	{
		private int slide;
		private double delay;

		@Override
		public void refresh()
			{
				slide = 0;
				delay = 5;
			}

		@Override
		public void tick(double secondsPassed)
			{
				delay -= secondsPassed;
				if (delay < 0)
					{
						delay = 5;
						nextSlide();
					}
			}

		@Override
		protected void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);

				g.drawImage(AssetLoader.helpSlides[slide], 300, 200, getObserver());
			}

		private void nextSlide()
			{
				slide++;

				if (slide >= AssetLoader.helpSlides.length)
					changeRenderableObject(new MenuWindow());
			}

		@Override
		public void mousePressed(MouseEvent me)
			{
				nextSlide();
			}

		@Override
		public void keyPressed(KeyEvent ke)
			{
				nextSlide();
			}
	}