package com.eglee.game.graphics;

public class Font
{
	private static String chars =	"" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,!?\'\"-+=/\\%()<>:;    ";

	public static void render(String message, Screen screen, int x, int y, int color, int scale)
	{
		message = message.toUpperCase();
		
		for (int i = 0; i < message.length(); i++)
		{
			int charIndex = chars.indexOf(message.charAt(i));
			if (charIndex >= 0) screen.render(x + i * 8, y, charIndex + 30 * 32, color, 0x00, scale);
		}
	}
}
