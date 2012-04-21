package HexNations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import TroysCode.RenderableObject;
import TroysCode.hub;
import TroysCode.T.TScrollEvent;

public class Help extends RenderableObject
	{
		private byte slide;
		private byte delay;

		@Override
		public void initiate()
			{
			}

		@Override
		public void refresh()
			{
				slide = 0;
				delay = 120;
			}

		@Override
		public void tick()
			{
				if (delay > 0)
					delay--;
			}

		@Override
		public void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);
				
				g.drawImage(hub.images.helpSlides[slide], 300, 200, hub.renderer);
			}

		private void nextSlide()
			{
				if (slide == 7 && delay == 0)
					changeRenderableObject(hub.mainMenu);

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
		public void tScrollBarScrolled(TScrollEvent event)
			{
				// TODO Auto-generated method stub
				
			}
	}
