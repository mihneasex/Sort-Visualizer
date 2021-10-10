import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class Visualizer extends JPanel implements ActionListener
{
	static int[] Array = new int[200];
	
	private SwingWorker SortThread;
	private SwingWorker<Void, Void> ShuffleThread;
	
	static private int Left = -1, Right = -1, Pivot = -1;
	static private int Sleep = 1;
	
	JButton bs = new JButton("Bubble Sort");
	JButton qs = new JButton("Quick Sort");
	JButton ss = new JButton("Selection Sort");
	JButton is = new JButton("Insertion Sort");

	JButton incSpeed = new JButton("Decrease Sleep");
	JButton decSpeed = new JButton("Increase Sleep");
	
	JLabel SleepLabel = new JLabel(String.valueOf(Sleep));
	
	Visualizer()
	{
		this.setPreferredSize(new Dimension(1600, 900));
		
		for(int i = 0; i < Array.length; i++)
		{
			Array[i] = (int)(i + 5);
		}
		
		bs.setBounds(0, 0, 100, 100);
		bs.addActionListener(this);
		  
		qs.setBounds(0, 0, 100, 100);
		qs.addActionListener(this);
		
		ss.setBounds(0, 0, 100, 100);
		ss.addActionListener(this);

		is.setBounds(0, 0, 100, 100);
		is.addActionListener(this);
		
		incSpeed.setBounds(0, 0, 100, 100);
		incSpeed.addActionListener(this);
		  
		decSpeed.setBounds(0, 0, 100, 100);
		decSpeed.addActionListener(this);
		
		SleepLabel.setFont(new Font(null,Font.PLAIN,25));
		
		this.add(bs);
		this.add(qs);
		this.add(ss);
		this.add(is);

		this.add(incSpeed);
		this.add(decSpeed);

		this.add(SleepLabel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bs) 
		{
			if (isSorted())
			{
				BubbleSort();
				Shuffle();
			}
		}
		
		else if(e.getSource() == qs) 
		{
			if (isSorted())
			{
				QuickSort(0, Array.length - 1);
				Shuffle();
			}
		}
		
		else if(e.getSource() == ss) 
		{
			if (isSorted())
			{
				SelectionSort();
				Shuffle();
			}
		}
		
		else if(e.getSource() == is) 
		{
			if (isSorted())
			{
				InsertionSort();
				Shuffle();
			}
		}
		
		if(e.getSource() == incSpeed) 
		{
			if (Sleep > 0)
			{
				Sleep--;
				SleepLabel.setText(String.valueOf(Sleep));
			}
		}
		
		else if(e.getSource() == decSpeed) 
		{
			if (Sleep < 50)
			{
				Sleep++;
				SleepLabel.setText(String.valueOf(Sleep));
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		
		int val = 1600 / Array.length;
		int height = 900 / Array.length;
		
		for(int i = 0; i < Array.length; i++)
		{
			g2D.fillRect(i * val, 900, val, -Array[i] * height);
		}

		if (Left != -1 && Right != -1)
		{
			g2D.setColor(Color.RED);
			g2D.fillRect(Left * val, 900, val, -Array[Left] * height);

			g2D.setColor(Color.YELLOW);
			g2D.fillRect(Right * val, 900, val, -Array[Right] * height);
		}
		if (Pivot != -1)
		{
			g2D.setColor(Color.GREEN);
			g2D.fillRect(Pivot * val, 900, val, -Array[Pivot] * height);
		}
	}
	
	public boolean isSorted()
	{
		for(int i = 1; i < Array.length; i++)
		{
			if (Array[i - 1] > Array[i])
				return false;
		}
		
		return true;
	}
	
	public void Shuffle()
	{
		Left = -1;
		Right = -1;
		Pivot = -1;
		
		ShuffleThread = new SwingWorker<>() 
		{
		@Override
		public Void doInBackground() throws InterruptedException 
		{
			Random rand = new Random();
			
			for (int i = 0; i < Array.length; i++) 
			{
				int randomIndexToSwap = rand.nextInt(Array.length);
				int temp = Array[randomIndexToSwap];
				Array[randomIndexToSwap] = Array[i];
				Array[i] = temp;
				
				Thread.sleep(Sleep);
				repaint();
			}
			return null;
		}
			@Override
			public void done() {
				super.done();
				SortThread.execute();
			}
		};

		ShuffleThread.execute();
	}
	
	public void BubbleSort()
	{
		SortThread = new SwingWorker() 
		{
			@Override
			public Void doInBackground() throws InterruptedException 
			{
				for(int i = 0; i < Array.length - 1; i++)
				{
					Left = i;
					for(int y = 0; y < Array.length - i - 1; y++)
					{
						Right = y;
						if (Array[y] > Array[y + 1])
						{
							int temp = Array[y];
							Array[y] = Array[y + 1];
							Array[y + 1] = temp;
							
						}
						Thread.sleep(Sleep);
						repaint();
					}
				}
				
				Left = -1;
				Right = -1;
				Pivot = -1;
				
				return null;
			}
		};
	}
	
	
	
	public void QuickSort(int left, int right)
	{
		SortThread = new SwingWorker() 
		{
			public int Partition(int beg, int end) throws InterruptedException
			{
				int pivotValue = Array[end];
				int pivotIndex = (beg - 1);
				Pivot = end;
				
				for(int j = beg; j < end; j++)
			    {
					Left = pivotIndex;
					Right = j;
					if (Array[j] < pivotValue) 
				    {	
						pivotIndex++; 
				        int temp = Array[pivotIndex];
				        Array[pivotIndex] = Array[j];
				        Array[j] = temp;
				    }
					Thread.sleep(Sleep);
					repaint();
			    }
				
		        int temp = Array[pivotIndex + 1];
		        Array[pivotIndex + 1] = Array[end];
		        Array[end] = temp;
			    return (pivotIndex + 1);
			}
			
			public void Sort(int left, int right) throws InterruptedException
			{
				if (left < right)
				{
					int pivot = Partition(left, right);
				
					Sort(left, pivot - 1);
					Sort(pivot + 1, right);
				}
			}
			
			@Override
			public Void doInBackground() throws InterruptedException 
			{
				Sort(left, right);
				
				Left = -1;
				Right = -1;
				Pivot = -1;
				
				return null;
			}
		};
	}
	
	public void SelectionSort()
	{
		SortThread = new SwingWorker() 
		{
			@Override
			public Void doInBackground() throws InterruptedException 
			{
				int i, j, min;
		
				for(i = 0; i < Array.length; i++)
				{
					min = i;
					Left = i;
					for (j = i + 1; j < Array.length; j++)
					{
						Right = j;
						if (Array[j] < Array[min])  
							min = j; 
		        
						Thread.sleep(Sleep);
						repaint();
					}
			
					int temp = Array[min];  
					Array[min] = Array[i];  
					Array[i] = temp;  
				}
				
				Left = -1;
				Right = -1;
				Pivot = -1;
				
				return null;
			}
		};
	}
	
	public void InsertionSort()
	{
		SortThread = new SwingWorker() 
		{
			@Override
			public Void doInBackground() throws InterruptedException 
			{
				 for (int i = 1; i < Array.length; i++) 
				 {
					 Left = i;
			         int key = Array[i];
			         int j = i - 1;
			 
			         while (j >= 0 && Array[j] > key) 
			         {
			        	Right = j;
			          	Array[j + 1] = Array[j];
			            j = j - 1;

						Thread.sleep(Sleep);
						repaint();
			         }
			       	 Array[j + 1] = key;
				 }
				
				Left = -1;
				Right = -1;
				Pivot = -1;
				
				return null;
			}
		};
	}
}