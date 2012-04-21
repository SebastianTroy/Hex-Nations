package HexNations.Region;

import java.awt.image.BufferedImage;

import TroysCode.T.TButton;

public class infoPop extends TButton
	{
		private static final long serialVersionUID = 1L;

		public byte type;
		public byte player;

		public int tileX;
		public int tileY;

		public infoPop(double x, double y, BufferedImage image, byte type, byte player, int tileX, int tileY)
			{
				super(x, y, image);
				this.type = type;
				this.player = player;
				this.tileX = tileX;
				this.tileY = tileY;
			}

	}