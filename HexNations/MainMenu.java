package HexNations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import TroysCode.Constants;
import TroysCode.RenderableObject;
import TroysCode.hub;
import TroysCode.T.TButton;
import TroysCode.T.TMenu;
import TroysCode.T.TScrollEvent;

public class MainMenu extends RenderableObject implements Constants
	{
		private static final long serialVersionUID = 1L;

		TMenu mainMenu = new TMenu(0, 0, hub.frame.startWidth, hub.frame.startHeight, TMenu.VERTICAL);

		TButton newGame = new TButton(0, 0, hub.images.menuButtons[0]);
		TButton options = new TButton(0, 0, hub.images.menuButtons[1]);
		TButton help = new TButton(0, 0, hub.images.menuButtons[2]);

		TButton[] buttons = { newGame, options, help };

		@Override
		public void initiate()
			{
				addTComponent(mainMenu);
				mainMenu.addTButton(newGame, false);
				mainMenu.addTButton(options, false);
				mainMenu.addTButton(help, false);
				
				mainMenu.setBorderSize(0);
				mainMenu.setButtonSpacing(0);
			}

		@Override
		public void refresh()
			{
			}

		@Override
		public void renderObject(Graphics g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);
			}

		@Override
		protected void actionPerformed(ActionEvent event)
			{
				if (event.getSource() == newGame)
					changeRenderableObject(hub.gameCreator);

				else if (event.getSource() == options)
					;// TODO make options menu?
						// hub.renderer.changeRenderableObject(hub.options);

				else if (event.getSource() == help)
					changeRenderableObject(hub.help);
			}

		@Override
		protected void tick()
			{
				// TODO Auto-generated method stub

			}

		@Override
		protected void mousePressed(MouseEvent event)
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
		public void tScrollBarScrolled(TScrollEvent event)
			{
				// TODO Auto-generated method stub

			}
	}
