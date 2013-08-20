package com.eglee.game;

//import java.util.List;
//import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class InputHandler implements KeyListener
{

	public InputHandler(Game game)
	{
		game.addKeyListener(this);
	}
	
	public class Key
	{
		private int numTimesPressed = 0;
		private int absorbs = 0;
		private boolean pressed = false;
		public boolean clicked;
		
		public Key() {
			keys.add(this);
		}
		
		public int getNumTimesPressed()
		{
			return numTimesPressed;
		}
		
		public boolean isPressed()
		{
			return pressed;
		}
		
		public void toggle(boolean isPressed)
		{
			pressed = isPressed;
			if (isPressed) numTimesPressed++;
		}
		
		public void tick() 
		{
			if (absorbs < numTimesPressed) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}
	
	
	public List<Key> keys = new ArrayList<Key>();

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key action = new Key();
	public Key menu = new Key();

	
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).pressed = false;
		}
	}

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		toggleKey(e.getKeyCode(), true);
		
	}


	public void keyReleased(KeyEvent e)
	{
		toggleKey(e.getKeyCode(), false);
		
	}

	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void toggleKey(int keyCode, boolean isPressed)
	{
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
		{
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
		{
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
		{
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
		{
			right.toggle(isPressed);
		}
		
		if (keyCode == KeyEvent.VK_SPACE)
		{
			action.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_ENTER)
		{
			menu.toggle(isPressed);
		}
		
	}	
}
