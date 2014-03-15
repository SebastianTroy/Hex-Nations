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

							}
					};
				joinGameButton = new TButton(0, 0, AssetLoader.menuButtons[1])
					{
						@Override
						public void pressed()
							{
								changeRenderableObject(new ClientCustomisationWindow());

							}
					};
				help = new TButton(0, 0, AssetLoader.menuButtons[2])
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
				mainMenu.add(help, false);

				mainMenu.setBorderSize(0);
				mainMenu.setTComponentSpacing(0);
			}
	}