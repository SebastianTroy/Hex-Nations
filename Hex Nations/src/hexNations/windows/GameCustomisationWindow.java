package hexNations.windows;

import hexNations.AssetLoader;
import hexNations.Main;
import hexNations.region.Region;

import java.awt.Color;
import java.awt.Graphics2D;

import tCode.RenderableObject;
import tComponents.components.TButton;

public class GameCustomisationWindow extends RenderableObject
	{
		// @formatter:off
		private final TButton begin = new TButton(650, 50, AssetLoader.begin){@Override public final void pressed(){Main.region = new Region();changeRenderableObject(new GameWindow());}};
		// @formatter:on

		@Override
		public void render(Graphics2D g)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 800, 600);
			}
	}