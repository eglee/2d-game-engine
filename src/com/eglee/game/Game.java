package com.eglee.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.eglee.game.entity.Bee;
import com.eglee.game.entity.Player;
import com.eglee.game.graphics.Colors;
import com.eglee.game.graphics.Font;
import com.eglee.game.graphics.Screen;
import com.eglee.game.graphics.SpriteSheet;
import com.eglee.game.gui.Menu;
import com.eglee.game.gui.TitleMenu;
import com.eglee.game.level.Level;




public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;// convention 

	public static final int WIDTH = 256; //width of JFrame
	public static final int HEIGHT = WIDTH/16 * 9; //HEIGHT is based on 12:9 aspect ratio
	public static final int SCALE = 3; //for scaling up game size
	public static final String TITLE = "Little Bears";
	public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	
	public JFrame frame; //from Swing API, creates a simple window for Java app		
	
	private Thread thread; //thread for game
	public boolean running = false; //whether game is running or not
	public int updateCount = 0;
	
	private BufferedImage image = new BufferedImage (WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //array for all the pixels, get raster of BufferedImage, rectangular array of pixels, cast into DataBufferInt. Allows us to draw things onto image
	private int[] colors = new int[6*6*6]; //6 different shades for each color, 6 for each RGB
	
	private Screen screen;
	public InputHandler input;
	public Level level;
	public Player player;
	public Bee bee;
	private Font font = new Font();
	public Menu menu;
	
	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null) menu.init(this, input);
	}
	
	public void initialize()
	{
		int index = 0;
		for (int r = 0; r < 6; r++)
		{
			for (int g = 0; g < 6; g++)
			{
				for (int b = 0; b < 6; b++)
				{
					int rr = (r * 255/5); //shades from 0-5
					int gg = (g * 255/5);
					int bb = (b * 255/5);
					
					colors[index++] = rr << 16 | gg << 8 | bb; //2^8 bits of data for blue, green, and red
				} //255 will be reserved for transparent color	
			}	
		}
		
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png")); //sprite sheet to be used by screen
		input = new InputHandler(this);
		
		//level = new Level("/levels/test_level.png");
		//level = new Level ("/levels/water_test_level.png");
		
		//set level
		level = new Level ("/levels/level_1.png");
		
		//add player
		player = new Player(level, 2 * 8, 2 * 8, input, 100);
		level.addEntity(player);
		
		//add enemies
		Bee bee0 = new Bee(level, 8 * 8, 1 * 8, 1, 100);
		Bee bee1 = new Bee(level, 9 * 8, 1 * 8, 2, 100);
		Bee bee2 = new Bee(level, 10 * 8, 1 * 8, 3, 100);
		Bee bee3 = new Bee(level, 11 * 8, 1 * 8, 4, 100);
		Bee bee4 = new Bee(level, 12 * 8, 1 * 8, 5, 100);
		Bee bee5 = new Bee(level, 13 * 8, 1 * 8, 6, 100);
		Bee bee6 = new Bee(level, 14 * 8, 1 * 8, 7, 100);
		Bee bee7 = new Bee(level, 15 * 8, 1 * 8, 8, 100);
		level.addEntity(bee0);
		level.addEntity(bee1);
		level.addEntity(bee2);
		level.addEntity(bee3);
		level.addEntity(bee4);
		level.addEntity(bee5);
		level.addEntity(bee6);
		level.addEntity(bee7);
		//setup title menu
		setMenu(new TitleMenu());	
	} //initializes screen
	
	public synchronized void start() // synch, preventing thread interferences and thread inconsistency errors
	{
		running = true; //changes running boolean
		thread = new Thread(this, TITLE + "_main"); //new thread contains this Game class, same and new Game()
		thread.start(); //creates a new thread object, start thread
	}
	
	public synchronized void stop()
	{
		running = false; //changes running boolean
		
		try
		{
			thread.join();// wait for thread to die, join all threads together after close of program/applet
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}// try to join threads, if it fails exit program
	}
	
	public void run()
	{
		long lastTime = System.nanoTime(); //sets whatever time our computer is currently at to lastTime in ns 
		long timer = System.currentTimeMillis(); //1000 ms is one second
		final double nanoSeconds = 1000000000.0 / 60.0; //1 billion divided by how many times we want to run our update method, turns into seconds
		double delta = 0;
		int frames = 0; //count exactly how many frames we have time to render (every time render() called)
		int updates = 0; //counts how many times update() is called
		requestFocus(); //method in Component class, focuses on window
		
		initialize(); //get screen ready to go
		requestFocus();
		
		while (running)
		{
			long now = System.nanoTime(); //current time to check against last time
			delta = delta + (now - lastTime) / nanoSeconds; //adding the difference between lastTime and now
			lastTime = now; //calculate the time it takes to get from here back to beginning of loop
			boolean shouldRender = true;
	
			while (delta >= 1) //will only happen 60 times a second
			{
				update();
				updates = updates + 1;
				delta = delta - 1; //set delta back to ~ 0
				shouldRender = true;
			} //when delta = 1, 1/60th of a second has passed and we can render screen
			
			try
			{
				Thread.sleep(2);
			} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			
			if (shouldRender)
			{
				frames = frames + 1;
				render();
			} //stops from rendering so many uneccesary frames
			
			
			if (System.currentTimeMillis() - timer >= 1000) //if difference between current time and last time we ran this method is greater than 1 second
			{
				timer = timer + 1000;
				//frame.setTitle(TITLE + " | " + updates + " updates per second, " + frames + " frames per second");
				updates = 0; //resets our variable
				frames = 0; //resets our variable
			}
			
			//System.out.println("RUNNING");
		}
	}
	

	
	public void update()
	{
		updateCount = updateCount + 1; //count ticks
		input.tick();
		if (menu != null) {
			menu.tick();
		} else{
		level.update();}
	}// tick, all the logic, 60 fps
	
	public void render()
	{
		BufferStrategy bufferStrategy = getBufferStrategy(); //buffer strategy, temporary storage space.  calcuate, put on waiting list, then grab the data when we need it. render it when we can, display when we need.
		if (bufferStrategy == null)
		{
			createBufferStrategy(3);
			return; //because BufferStrategy is set above, redundancy
		} //if canvas is returning null because bufferStrategy hasn't been created, create 3 bs
		
		
		if (menu !=null)
		{
			menu.render(screen);
		} else
			if (player != null)
			{
				int xOffset = player.x - (screen.width/2);
				int yOffset = player.y - (screen.height/2);
				
				level.renderTiles(screen, xOffset, yOffset);
				level.renderEntities(screen);
				
				if (level.entities.size() == 1)
			       {
						String message = "you win!";
						String determination = "you have determination.";
						font.render(message, screen, screen.xOffset+ (screen.width/2) - 32, screen.yOffset + 64, Colors.get(-1, -1, -1,555),1);
						font.render(determination, screen, screen.xOffset+ (screen.width/2) - 88, screen.yOffset + 64 + 16, Colors.get(-1, -1, -1,555),1);
			       } //when player is last entity on screen, win
				
			}
		
		
		for (int y = 0; y < screen.height;y++)
		{
			for (int x = 0; x < screen.width;x++)
			{
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		
		Graphics graphics = bufferStrategy.getDrawGraphics();// creates a link between Graphics class and bufferStrategy, context for drawing buffer
		
		// graphics go here
		graphics.fillRect(0, 0, getWidth(), getHeight()); // getWidth()/getHeight() part of component/canvas class to get correct window size and fill from top left corner
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);// draw buffered image to screen
		graphics.dispose();// releases all system resources for graphics at the end of each frame
	 	bufferStrategy.show();// swap buffer remove image we had last time. release memory and display
		// show the buffer
		
	} //display images, "unlimited" fps
	
}
