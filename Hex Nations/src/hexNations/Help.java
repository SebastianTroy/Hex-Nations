package hexNations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import tCode.RenderableObject;

public class Help extends RenderableObject
	{
		private byte slide;
		private byte delay;

		@Override
		public void refresh()
			{
				slide = 0;
				delay = 120;
			}

		@Override
		public void tick(double secondsPassed)
			{
				if (delay > 0)
					delay--;
			}

		@Override
		protected void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);
				
				g.drawImage(Images.helpSlides[slide], 300, 200, getObserver());
			}

		private void nextSlide()
			{
				if (slide == 7 && delay == 0)
					changeRenderableObject(new MainMenu());

				if (delay == 0)
					{
						slide++;
						delay = 40;
					}
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
