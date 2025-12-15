import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;



public class BlockBreaker extends JPanel implements ActionListener, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private class Tile
	{
		int x; 
		int y;
		
		Tile(int x, int y)
		{
			this.x = x; this.y =y;
		}
	}
	
	Random random = new Random();
		
	int boardWidth;
	int boardHeight;
	int tileSize = 50;
	
	//Bar
	Tile bar;
	int barMove;
	
	//Ball
	Tile ball;
	
	//blocks
	Tile block;
	ArrayList<Tile> blocks;
	
	//game logic
	Timer gameLoop;
	int ballVelocityX;
	int ballVelocityY;
	boolean gameOver = false;
	
	BlockBreaker(int width, int height)
	{
		this.boardWidth = width; this.boardHeight = height;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(new Color(197, 208, 212));
		
		addKeyListener(this);
		setFocusable(true);
		
		bar = new Tile(350, 770);
		
		ball = new Tile(500,750);
		ballVelocityY = makeRandomNumber() * -1;
		//create a gride of blocks to place
		blocks = new ArrayList<>();
		for(int j = 0; j < 5; j++) 
		{
			for(int i = 0; i < 50; i++)
			{
				block = new Tile(i * tileSize, j * tileSize);
				blocks.add(block);
			}
		}
		
		gameLoop = new Timer(5,this);
		gameLoop.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g)
	{
		//draw bar
		g.setColor(Color.GRAY);
		g.fill3DRect(bar.x, bar.y, 300, 15, true);
		
		//draw circle
		g.setColor(Color.GRAY);
		g.fillOval(ball.x, ball.y, 20, 20);
		
		//draw blocks
		for(Tile block: blocks)
		{
			g.setColor(Color.GRAY);
			g.fill3DRect(block.x,block.y,tileSize,tileSize,true);
		}
		
	}
	
	public boolean collision(Tile t1, int w1, int h1, Tile t2, int w2, int h2) 
	{
	    return t1.x < t2.x + w2 &&
	           t1.x + w1 > t2.x &&
	           t1.y < t2.y + h2 &&
	           t1.y + h1 > t2.y;
	}
	
	public void move()
	{
		//Bar movement
		bar.x += barMove;
		
		//Ball movement
		ball.x += ballVelocityX;
		ball.y += ballVelocityY;
		
		//logic for ball movement
		if(ball.y <= 0) ballVelocityY = -ballVelocityY;
		if(ball.y + 20 >= boardHeight) gameOver = true;
		if(ball.x <= 0 || ball.x + 20 >= boardWidth) ballVelocityX = -ballVelocityX;
		
		if (collision(bar, 300, 15, ball, 20, 20))
		{
			if(ballVelocityX > 0)
			{
				ballVelocityX = makeRandomNumber(); 
				ballVelocityY = -ballVelocityY;
			}
			else
			{
				ballVelocityX = makeRandomNumber() * -1; 
				ballVelocityY = -ballVelocityY;
			}
		}
		int i = 0;
		for(Tile block: blocks)
		{
			if(collision(block, tileSize, tileSize, ball, 20,20))
			{
				ballVelocityX = makeRandomNumber(); 
				ballVelocityY = -ballVelocityY;
				blocks.remove(i);
				break;
			}
			i++;
		}
	}
	
	public int makeRandomNumber()
	{
		int num = random.nextInt(5);
		while (num == 0)
			num = random.nextInt(5);
		return num;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if(gameOver) gameLoop.stop();
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_A,KeyEvent.VK_LEFT:
			barMove = -3;
			break;
		case KeyEvent.VK_D,KeyEvent.VK_RIGHT:
			barMove = 3;
			break;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_A,KeyEvent.VK_LEFT:
			barMove = 0;
			break;
		case KeyEvent.VK_D,KeyEvent.VK_RIGHT:
			barMove = 0;
			break;
		}
		
	}
	
	//not needed
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}


	



	
	
}
