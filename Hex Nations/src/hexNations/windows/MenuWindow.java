package hexNations.windows;

import hexNations.AssetLoader;
import hexNations.Main;
import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TMenu;

public class MenuWindow extends RenderableObject
	{
		TMenu mainMenu;

		TButton createGameButton;
		TButton joinGameButton;
		TButton options;
		TButton help;

		@Override
		public void initiate()
			{
				createGameButton = new TButton(0, 0, AssetLoader.menuButtons[0])
					{
						@Override
						public void pressed()
							{
								changeRenderableObject(new HostCustomisationWindow());

								/*
								 * -Create custom game
								 * -Set up the server
								 * -Add the players
								 * -Prepare the game
								 */
							}
					};
				joinGameButton = new TButton(0, 0, AssetLoader.menuButtons[2])
					{
						@Override
						public void pressed()
							{
								changeRenderableObject(new ClientCustomisationWindow());
								
								/*
								 * -join the server
								 * -Add the player to the server
								 * -Prepare the game
								 */
							}
					};
				options = new TButton(0, 0, AssetLoader.menuButtons[1])
					{
						@Override
						public void pressed()
							{
								;/* TODO make options menu? hub.renderer.changeRenderableObject(hub.options); */
							}
					};
				help = new TButton(0, 0, AssetLoader.menuButtons[3])
					{
						@Override
						public void pressed()
							{
								changeRenderableObject(new HelpWindow());
							}
					};

				mainMenu = new TMenu(0, 0, Main.frameWidth, Main.frameHeight, TMenu.VERTICAL);

				add(mainMenu);
				mainMenu.add(createGameButton, false);
				mainMenu.add(joinGameButton, false);
				mainMenu.add(options, false);
				mainMenu.add(help, false);

				mainMenu.setBorderSize(0);
				mainMenu.setTComponentSpacing(0);
			}
	}