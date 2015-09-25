import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 
 */

/**
 * @author Piyush
 *
 */
public class GreedyBestFirst {

	/**
	 * @param args
	 */
	
	private static String _MazeFilePath = "C:/Users/Piyush/Desktop/maze.txt";
	static	 final int _COLUMN = 24;
	static final int _ROWS = 24;
	static char[][] _maze = new char[_ROWS][_COLUMN];
	private static int[] _destination = {0,0};
	private static int[] _source = {0,0};
	private static int[] _currentNode = {0,0};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadMaze();
		getSourceAndDestination();
		printArray();
		System.out.println();
		System.out.println("Source coordinates: "+_source[0]+","+_source[1]);
		System.out.println("Destination coordinates: "+_destination[0]+","+_destination[1]);
		
		//Start the algorithm
		//findPath();

	}
	
	public static void loadMaze() 
	{
		int row= 0;
		int col = 0;

		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(_MazeFilePath));
			String line = "";
			while((line = reader.readLine()) != null) 
			{
				for(col = 0; col < line.length();col++) 
				{
					_maze[row][col] = line.charAt(col);
				}
				row++;
			}
			reader.close();
		} 
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void printArray() 
	{
		for(int row = 0; row <_ROWS ; row++) 
		{
			for(int col = 0; col <_COLUMN; col++) 
			{
				System.out.print(_maze[row][col]);
			}
			System.out.println();
		}
	}
	
	public static int calculateHeuristicScore(int currentNode[])
	{
		/*calculateHeuristics(dest, currentNode):
		  Manhattan distance from point a to point b
		  abs(dest.x - currentNode.x) + abs(dest.y - currentNode.y)*/
		
		int score = 0;
		score = (java.lang.Math.abs(currentNode[0] - _destination[0]))
		+(java.lang.Math.abs(currentNode[1] - _destination[1]));
		
		return score;
	}
	
	public static void getSourceAndDestination() 
	{	    	
		boolean sourceFound = false;
		boolean destFound = false;
		
		for(int row = 0; row<_ROWS; row++)
		{
			for (int col = 0; col<_COLUMN; col++)
			{
				if(sourceFound & destFound)
					break;
				
				if(_maze[row][col]== 'P')
				{
					_source[0]= row;
					_source[1]= col;
					_currentNode[0]= row;
					_currentNode[1]= col;
					sourceFound = true;
				}
				else if(_maze[row][col]== '.')
				{
					_destination[0]= row;
					_destination[1]= col;
					destFound = true;
				}
			}
			if(sourceFound & destFound)
				break;
		}
	}
	
	public static class NodeComparator implements Comparator<GreedyNode>{

		public int compare(GreedyNode arg0, GreedyNode arg1) 
		{
			// TODO Auto-generated method stub
			if(arg0.getScore() > arg1.getScore())
				return arg0.getScore();
			else
				return arg1.getScore();			
		}
		
	}
	
	public static void findPath()
	{
		GreedyNode node = new GreedyNode(_source[0], _source[1]);
		node.setScore(calculateHeuristicScore(_source));
		
		Comparator<GreedyNode> comparator = new NodeComparator();
		PriorityQueue<GreedyNode> pQueue = new PriorityQueue<GreedyNode>(comparator);
		pQueue.add(node);
		
		boolean pathFound = false;
		while(!pathFound)
		{
			boolean hasPacmanMoved = false;
			int currentRow = node.getRow(), currentCol= node.getCol();

			if(_maze[currentRow][currentCol] == '.') 
			{
				pathFound = true;
				break;
			}
			for(int i=0; i<4; i++)
			{
				
			}
		}
		
	}


}
