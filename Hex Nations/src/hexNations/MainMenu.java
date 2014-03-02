package hexNations;

import java.awt.Color;
import java.awt.Graphics2D;

import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TMenu;

public class MainMenu extends RenderableObject implements Constants
	{
		TMenu mainMenu;

		TButton newGame = new TButton(0, 0, Images.menuButtons[0])
			{
				@Override
				public void pressed()
					{
						changeRenderableObject(new NewGameCreator());
					}
			};
		TButton options = new TButton(0, 0, Images.menuButtons[1])
			{
				@Override
				public void pressed()
					{
						;// TODO make options menu?
							// hub.renderer.changeRenderableObject(hub.options);
					}
			};
		TButton help = new TButton(0, 0, Images.menuButtons[2])
			{
				@Override
				public void pressed()
					{
						changeRenderableObject(new Help());
					}
			};

		TButton[] buttons = { newGame, options, help };

		@Override
		public void initiate()
			{
				mainMenu = new TMenu(0, 0, Main.frameWidth, Main.frameHeight, TMenu.VERTICAL);

				add(mainMenu);
				mainMenu.add(newGame, false);
				mainMenu.add(options, false);
				mainMenu.add(help, false);

				mainMenu.setBorderSize(0);
				mainMenu.setTComponentSpacing(0);
			}

		@Override
		public void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);
			}
	}