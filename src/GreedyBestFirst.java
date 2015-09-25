import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
	private static List<GreedyNode> _vistedNodeList = new ArrayList<GreedyNode>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadMaze();
		getSourceAndDestination();
		printArray();
		System.out.println();
		System.out.println("Source coordinates: "+_source[0]+","+_source[1]);
		System.out.println("Destination coordinates: "+_destination[0]+","+_destination[1]);
		
		//Start the algorithm
		findPath();
		
		//print the node visited
		for(int i =0; i < _vistedNodeList.size(); i++)
		{
			GreedyNode node = _vistedNodeList.get(i);
			System.out.print("["+node.getRow()+","+node.getCol()+"]-->");
			_maze[node.getRow()][node.getCol()] = '*';
		}
		System.out.println("G!");
		System.out.println();
		
		printArray();

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
	
	public static int calculateHeuristicScore(int x, int y)
	{
		/*calculateHeuristics(x, y):
		  Manhattan distance from point a to point b
		  abs(x - destinationNode.x) + abs(y - destinationNode.y)*/
		
		int score = 0;
		score = (java.lang.Math.abs(x - _destination[0]))
		+(java.lang.Math.abs(y - _destination[1]));
		
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
			// the node having lesser score is closer to the destination.
			/*if(arg0.getScore() < arg1.getScore())
				return arg0.getScore();
			else
				return arg1.getScore();*/
			return (arg0.getScore() - arg1.getScore());
		}
		
	}
	
	public static void findPath()
	{
		GreedyNode node = new GreedyNode(_source[0], _source[1]);
		node.setScore(calculateHeuristicScore(_source));
		List<String> visitedNode = new ArrayList<String>();
		
		Comparator<GreedyNode> comparator = new NodeComparator();
		PriorityQueue<GreedyNode> pQueue = new PriorityQueue<GreedyNode>(500, comparator);
		pQueue.add(node);
		
		boolean pathFound = false;
		while(!pathFound)
		{
			//boolean hasPacmanMoved = false;
			node = pQueue.remove();
			_vistedNodeList.add(node);
			visitedNode.add(String.valueOf(node.getRow())+String.valueOf(node.getCol()));
			
			int currentRow = node.getRow(), currentCol= node.getCol();
			int neighborsDiscovered = 0;
			
			if(_maze[currentRow][currentCol] == '.') 
			{
				pathFound = true;
				break;
			}
			
			GreedyNode next;
			while(neighborsDiscovered < 4)
			{
				switch(neighborsDiscovered)
				{
					case 0:
						// try left
						String nxtRowStr = String.valueOf(currentRow);
						String nxtColStr = String.valueOf(currentCol-1);
						if( (currentCol > 0) & (_maze[currentRow][currentCol - 1] != '%') & !visitedNode.contains(nxtRowStr+nxtColStr)) 
						{
							next = new GreedyNode(currentRow, (currentCol-1));
							//visitedNode.add(nxtRowStr+nxtColStr);
							node.leftNodeVisited = true;
							Integer newRow = currentRow;
							Integer newCol = currentCol-1;
							next.setCol(newCol);
							next.setRow(newRow);
							next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
							pQueue.add(next);
							//hasPacmanMoved = true;
						}
						break;
						
					case 1:
						// try up
						nxtRowStr = String.valueOf(currentRow-1);
						nxtColStr = String.valueOf(currentCol);
						if( (currentRow > 0) && (_maze[currentRow - 1][currentCol] != '%') & !visitedNode.contains(nxtRowStr+nxtColStr)) 
						{
							next = new GreedyNode((currentRow - 1), currentCol);
							//visitedNode.add(nxtRowStr+nxtColStr);
							node.upNodeVisited = true;
							Integer newRow = currentRow-1;
							Integer newCol = currentCol;
							next.setCol(newCol);
							next.setRow(newRow);
							next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
							pQueue.add(next);
							//hasPacmanMoved = true;
						}
						break;
						
					case 2:
						//try right
						nxtRowStr = String.valueOf(currentRow);
						nxtColStr = String.valueOf(currentCol+1);
						if( (currentCol < _COLUMN) && (_maze[currentRow][currentCol + 1] != '%') & !visitedNode.contains(nxtRowStr+nxtColStr)) 
						{
							next = new GreedyNode((currentRow - 1), currentCol + 1);
							//visitedNode.add(nxtRowStr+nxtColStr);
							node.rightNodeVisited = true;
							Integer newRow = currentRow;
							Integer newCol = currentCol+1;
							next.setCol(newCol);
							next.setRow(newRow);
							next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
							pQueue.add(next);
							//hasPacmanMoved = true;
						}
						break;
					
					case 3:
						//try down
						nxtRowStr = String.valueOf(currentRow+1);
						nxtColStr = String.valueOf(currentCol);
						if( (currentCol < _COLUMN) && (_maze[currentRow + 1][currentCol] != '%') & !visitedNode.contains(nxtRowStr+nxtColStr)) 
						{
							next = new GreedyNode((currentRow + 1), currentCol);
							//visitedNode.add(nxtRowStr+nxtColStr);
							node.downNodeVisited = true;
							Integer newRow = currentRow+1;
							Integer newCol = currentCol;
							next.setCol(newCol);
							next.setRow(newRow);
							next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
							pQueue.add(next);
							//hasPacmanMoved = true;
						}
						break;
				}
				neighborsDiscovered++;
			}
			//pQueue.remove();
		}
		
	}
}
