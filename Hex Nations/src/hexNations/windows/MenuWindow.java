package hexNations.windows;

import hexNations.AssetLoader;
import hexNations.Main;
import tCode.RenderableObject;
import tComponents.components.TButton;
import tComponents.components.TMenu;

public class MenuWindow extends RenderableObject
	{
		TMenu mainMenu;

		// @formatter:off
		TButton createGameButton = new TButton(0, 0, AssetLoader.menuButtons[0]){ @Override public void pressed(){changeRenderableObject(new GameCustomisationWindow());}};
		// TODO make icon for button
		TButton joinGameButton = new TButton(0, 0, AssetLoader.menuButtons[0]){ @Override public void pressed(){changeRenderableObject(new GameCustomisationWindow());}};
		TButton options = new TButton(0, 0, AssetLoader.menuButtons[1]){	@Override public void pressed(){;/* TODO make options menu?  hub.renderer.changeRenderableObject(hub.options);*/}};
		TButton help = new TButton(0, 0, AssetLoader.menuButtons[2]){ @Override public void pressed(){changeRenderableObject(new HelpWindow());}};
		// @formatter:on

		@Override
		public void initiate()
			{
				mainMenu = new TMenu(0, 0, Main.frameWidth, Main.frameHeight, TMenu.VERTICAL);

				add(mainMenu);
				mainMenu.add(createGameButton, false);
				// TODO add "Join Game" to menu
				mainMenu.add(options, false);
				mainMenu.add(help, false);

				mainMenu.setBorderSize(0);
				mainMenu.setTComponentSpacing(0);
			}
	}