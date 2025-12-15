import javax.swing.*;

public class AppDemo {
	public static void main(String[] args) 
	{
		int width = 1000;
		int height = 800;
		
		//create frame
		JFrame f = new JFrame("Block Breaker");

		f.setSize(width,height);
		f.setLocationRelativeTo(null);//center window
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create instance of game
		BlockBreaker bb = new BlockBreaker(width,height);
		f.add(bb);
		f.pack();
		
		f.setVisible(true);
	}

}
