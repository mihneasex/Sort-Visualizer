import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window extends JFrame 
{
	Visualizer Context = new Visualizer();
	
	Window()
	{
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.add(Context);
		this.pack();
		
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
