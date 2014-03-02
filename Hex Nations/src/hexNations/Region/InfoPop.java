package hexNations.Region;

import java.awt.image.BufferedImage;

import tComponents.components.TButton;

public class InfoPop extends TButton
	{
		public byte type;
		public byte player;

		public int tileX;
		public int tileY;

		public InfoPop(double x, double y, BufferedImage image, byte type, byte player, int tileX, int tileY)
			{
				super(x, y, image);
				this.type = type;
				this.player = player;
				this.tileX = tileX;
				this.tileY = tileY;
			}
	}