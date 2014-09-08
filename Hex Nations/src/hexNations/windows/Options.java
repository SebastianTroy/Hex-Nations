package hexNations.windows;

import java.awt.Graphics2D;

import tCode.RenderableObject;

/**
 * TODO
 *
 * @author Sebastian Troy
 */
public class Options extends RenderableObject
	{
		@Override
		public void initiate()
			{}

		@Override
		public void tick(double secondsPassed)
			{
				changeRenderableObject(new MainMenu());
			}

		@Override
		public void render(Graphics2D g)
			{

			}
	}