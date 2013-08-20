package com.eglee.game.gui;


import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Font;
import com.eglee.game.graphics.Screen;
//import com.mojang.ld22.sound.Sound;

public class TitleMenu extends Menu {
	private int selected = 0;

	private static final String[] options = { "Start game", "How to play"};
	private long lastIterationTime; //ms since last update
	private int updateDelay;



	
	public TitleMenu() {
		
		
		
		lastIterationTime = System.currentTimeMillis();
		updateDelay = 120;
	}

	public void tick() {
		//System.out.println(selected);
		if ((System.currentTimeMillis() - lastIterationTime) >= (updateDelay))
		{
			lastIterationTime = System.currentTimeMillis();
			
			
			if (input.up.isPressed() ) 
			{
				selected = selected - 1;
				System.out.println("selected - 1: " + selected);
			}
			if (input.down.isPressed())
			{
				selected = selected + 1;
				System.out.println("selected + 1: " + selected);
			}
		
			int len = options.length;
			if (selected >= len) 
			{selected = selected - len;
				System.out.println("selected - leng: " + selected);
			}
		
			if (selected < 0) selected = selected + len;
			{
				System.out.println("selected + leng: " + selected);
			}

		} //past delay time, so update

		

		if (input.action.isPressed() || input.menu.isPressed())
		{
			System.out.println("space pressed " + selected);
			if (selected == 0) game.setMenu(null);
			if (selected == 1) game.setMenu(new HowToPlayMenu(this));
	
		}
	}

	public void render(Screen screen) {
		screen.clear(0);
		
		//render background sky
		int bgHeight = 18;
		int bgWidth = 32;
		int skyColor = Colors.get(-1, 125, 135, 555);
		//int xo = (screen.width - w * 8) / 2;
		//int yo = 24;
		
		for (int y = 0; y < bgHeight; y++) {
			for (int x = 0; x < bgWidth; x++) {
				screen.render(x * 8, y * 8, x + (y + 32) * 32, skyColor, 0, 1);
			}
		}
		
		
		//render left bear
		int leftBearHeight = 4;
		int leftBearWidth = 4;
		int leftBearColor = Colors.get(-1, 210, 432, 543);
		int leftBearOffsetX = ((screen.width - leftBearWidth * 8) / 2) - 64;
		int leftBearOffsetY = screen.height - 56;
					
		for (int y = 0; y < leftBearHeight; y++) {
			for (int x = 0; x < leftBearWidth; x++) {
				screen.render(leftBearOffsetX + x * 8, leftBearOffsetY + y * 8, (x + 3 )+ (y + 54) * 32, leftBearColor, 0, 1);
			}
		}
		
		
		//render right bear
		int bearHeight = 4;
		int bearWidth = 3;
		int bearColor = Colors.get(-1, 210, 432, 543);
		int bearOffsetX = ((screen.width - bearWidth * 8) / 2) + 64;
		int bearOffsetY = screen.height - 57;
				
		for (int y = 0; y < bearHeight; y++) {
			for (int x = 0; x < bearWidth; x++) {
				screen.render(bearOffsetX + x * 8, bearOffsetY + y * 8, x + (y + 54) * 32, bearColor, 0, 1);
			}
		}
		
		
		//render grass foreground
		int grassHeight = 4;
		int grassWidth = 32;
		int grassColor = Colors.get(-1, 232, 343, 453);
		int grassOffsetY = screen.height - 32;
		//int xo = (screen.width - w * 8) / 2;
		//int yo = 24;
		
		for (int y = 0; y < grassHeight; y++) {
			for (int x = 0; x < grassWidth; x++) {
				screen.render(x * 8, grassOffsetY + y * 8, x + (y + 50) * 32, grassColor, 0, 1);
			}
		}
		
		
		
		
		int h = 3;
		int w = 16;
		int titleColor = Colors.get(-1, 321, 532, 542);
		int xo = (screen.width - w * 8) / 2;
		int yo = 24;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				screen.render(xo + x * 8, yo + y * 8, x + (y + 6) * 32, titleColor, 0, 1);
			}
		}
		

		for (int i = 0; i < 2; i++) {
			String msg = options[i];
			int col = Colors.get(-1, 222, 222, 321);
			if (i == selected) {
				col = Colors.get(-1, 555, 555, 542);
			}
			Font.render(msg, screen, (screen.width - msg.length() * 8) / 2, (8 + i) * 8, col, 1);
		}

		Font.render("(WASD/Arrowkeys, SPACE)", screen, 0, screen.height - 8, Colors.get(-1, 223, 223, 554), 1);
	}
}