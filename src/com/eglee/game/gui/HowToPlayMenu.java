package com.eglee.game.gui;

import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Font;
import com.eglee.game.graphics.Screen;

public class HowToPlayMenu extends Menu
{

	private Menu parent;

	public HowToPlayMenu(Menu parent) {
		this.parent = parent;
	}

	public void tick() {

		
		if (input.action.isPressed()) {
			game.setMenu(parent);
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
		
		
	
		Font.render("Get the bees to chase you", screen, 3 * 8 + 4, 3 * 8, Colors.get(-1, 333, 333, 542), 1);
		Font.render("with the arrow keys or", screen, 3 * 8 + 4, 4 * 8, Colors.get(-1, 333, 333, 542), 1);
		Font.render("wasd, space to dash.", screen, 3 * 8 + 4, 5 * 8, Colors.get(-1, 333, 333, 542), 1);
		Font.render("try to lead them into", screen, 3 * 8 + 4, 6 * 8, Colors.get(-1, 333, 333, 542), 1);
		Font.render("thorn bushes to meet their", screen, 3 * 8 + 4, 7 * 8, Colors.get(-1, 333, 333, 542), 1);
		Font.render("doom! bees ignore you if", screen, 3 * 8 + 4, 8 * 8, Colors.get(-1, 333, 333, 542), 1);
		Font.render("in water.", screen, 3 * 8 + 4, 9 * 8, Colors.get(-1, 333, 333, 542), 1);
	}
}
