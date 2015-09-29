import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 */

/**
 * @author Piyush & Ashutosh
 *
 */
public class GreedyBestFirst {

	/**
	 * @param args
	 */


	private static String _medMazePath = "C:/Users/Piyush/Desktop/maze.txt";
	private static String _bigMazePath = "C:/Users/Piyush/Desktop/bigmaze.txt";
	private static String _openMazePath = "C:/Users/Piyush/Desktop/openMaze.txt";

	static int _Column = 0;
	static int _Rows = 0;

	static int cost = 0;
	private static int[] _destination = {0,0};
	private static int[] _source = {0,0};
	private static int[] _currentNode = {0,0};
	private static List<GreedyNode> _visitedNodeList = new ArrayList<GreedyNode>();

	static char[][] maze;

	public static void loadFile(int option) 
	{
		int row= 0;
		int col = 0;
		BufferedReader reader = null;

		try 
		{
			if(option ==1)
			{
				reader = new BufferedReader(new FileReader(_medMazePath));
				_Column = 23;
				_Rows = 23;
				maze = new char[_Rows][_Column];
			}
			else if(option == 2)
			{
				reader = new BufferedReader(new FileReader(_bigMazePath));
				_Column = 37;
				_Rows = 37;
				maze = new char[_Rows][_Column];
			}
			else if(option == 3)
			{
				reader = new BufferedReader(new FileReader(_openMazePath));
				_Column = 37;
				_Rows = 20;
				maze = new char[_Rows][_Column];
			}

			String line = "";
			while((line = reader.readLine()) != null) 
			{
				for(col = 0; col < line.length();col++) 
				{
					maze[row][col] = line.charAt(col);
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
		for(int row = 0; row <_Rows ; row++) 
		{
			for(int col = 0; col <_Column; col++) 
			{
				System.out.print(maze[row][col]);
			}
			System.out.println();
		}
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub


		System.out.println("Select one of the following:");
		System.out.println("1. Medium Maze");
		System.out.println("2. Big Maze");
		System.out.println("3. Open Maze");
		System.out.println("Any other key to exit");

		try 
		{
			BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
			String userCommand = userReader.readLine();

			if(userCommand.equalsIgnoreCase("1"))
			{
				loadFile(1);
			}
			else if(userCommand.equalsIgnoreCase("2"))
			{
				loadFile(2);
			}
			else if(userCommand.equalsIgnoreCase("3"))
			{
				loadFile(3);
			}
			else
			{
				System.out.println("Invalid option");
				return;
			}


			System.out.println("Here is the Original Maze..");

			System.out.println();

			getSourceAndDestination();
			printArray();
			System.out.println();
			System.out.println("Source coordinates: "+_source[0]+","+_source[1]);
			System.out.println("Destination coordinates: "+_destination[0]+","+_destination[1]);

			//Start the algorithm
			findPath();

			System.out.println();

			System.out.println("The cost for the Path for Greedy is" + " " +getcost());
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}

	}

	public static int getcost(){


		return cost;
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

		for(int row = 0; row<_Rows; row++)
		{
			for (int col = 0; col<_Column; col++)
			{
				if(sourceFound & destFound)
					break;

				if(maze[row][col]== 'P')
				{
					_source[0]= row;
					_source[1]= col;
					_currentNode[0]= row;
					_currentNode[1]= col;
					sourceFound = true;
				}
				else if(maze[row][col]== '.')
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
		GreedyNode node = new GreedyNode(_source[0], _source[1], null);
		node.setScore(calculateHeuristicScore(_source));
		List<String> visitedNode = new ArrayList<String>();

		Comparator<GreedyNode> comparator = new NodeComparator();
		PriorityQueue<GreedyNode> pQueue = new PriorityQueue<GreedyNode>(500, comparator);
		pQueue.add(node);
		_visitedNodeList.add(node);
		visitedNode.add(_source[0]+":"+_source[1]);


		boolean pathFound = false;
		while(!pathFound)
		{
			//boolean hasPacmanMoved = false;
			node = pQueue.remove();
			int currentRow = node.getRow(), currentCol= node.getCol();
			int neighborsDiscovered = 0;

			if(maze[currentRow][currentCol] == '.') 
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
					if( (currentCol > 0) & (maze[currentRow][currentCol - 1] != '%') & !visitedNode.contains(nxtRowStr+":"+nxtColStr)) 
					{
						next = new GreedyNode(currentRow, (currentCol-1), node);
						node.leftNodeVisited = true;
						Integer newRow = currentRow;
						Integer newCol = currentCol-1;
						next.setCol(newCol);
						next.setRow(newRow);
						next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
						pQueue.add(next);
						_visitedNodeList.add(next);
						visitedNode.add(nxtRowStr+":"+nxtColStr);
						//hasPacmanMoved = true;
					}
					break;


				case 1:
					//try down
					nxtRowStr = String.valueOf(currentRow+1);
					nxtColStr = String.valueOf(currentCol);
					if( (currentCol < _Column) && (maze[currentRow + 1][currentCol] != '%') & !visitedNode.contains(nxtRowStr+":"+nxtColStr)) 
					{
						next = new GreedyNode((currentRow + 1), currentCol, node);
						//visitedNode.add(nxtRowStr+nxtColStr);
						node.downNodeVisited = true;
						Integer newRow = currentRow+1;
						Integer newCol = currentCol;
						next.setCol(newCol);
						next.setRow(newRow);
						next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
						pQueue.add(next);
						_visitedNodeList.add(next);
						visitedNode.add(nxtRowStr+":"+nxtColStr);
						//hasPacmanMoved = true;
					}
					break;	

				case 2:
					// try up
					nxtRowStr = String.valueOf(currentRow-1);
					nxtColStr = String.valueOf(currentCol);
					if( (currentRow > 0) && (maze[currentRow - 1][currentCol] != '%') & !visitedNode.contains(nxtRowStr+":"+nxtColStr)) 
					{
						next = new GreedyNode((currentRow - 1), currentCol, node);
						//visitedNode.add(nxtRowStr+nxtColStr);
						node.upNodeVisited = true;
						Integer newRow = currentRow-1;
						Integer newCol = currentCol;
						next.setCol(newCol);
						next.setRow(newRow);
						next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
						pQueue.add(next);
						_visitedNodeList.add(next);
						visitedNode.add(nxtRowStr+":"+nxtColStr);
						//hasPacmanMoved = true;
					}
					break;

				case 3:
					//try right
					nxtRowStr = String.valueOf(currentRow);
					nxtColStr = String.valueOf(currentCol+1);
					if( (currentCol < _Column) && (maze[currentRow][currentCol + 1] != '%') & !visitedNode.contains(nxtRowStr+":"+nxtColStr)) 
					{
						next = new GreedyNode((currentRow - 1), currentCol + 1, node);
						//visitedNode.add(nxtRowStr+nxtColStr);
						node.rightNodeVisited = true;
						Integer newRow = currentRow;
						Integer newCol = currentCol+1;
						next.setCol(newCol);
						next.setRow(newRow);
						next.setScore(calculateHeuristicScore(next.getRow(),next.getCol()));
						pQueue.add(next);
						_visitedNodeList.add(next);
						visitedNode.add(nxtRowStr+":"+nxtColStr);
						//hasPacmanMoved = true;
					}
					break;
				}
				neighborsDiscovered++;
			}		

		}


		if(pathFound)
		{
			ArrayList<GreedyNode> path = new ArrayList<GreedyNode>();
			while(node != null) 
			{
				path.add(node);
				node = node.prev;
			}
			Collections.reverse(path);
			cost = path.size();
			// print path
			for(int i = 0; i < path.size(); i++) 
			{
				if(i == path.size() - 1) 
				{
					System.out.println(path.get(i));
				}
				else 
				{
					int x = path.get(i).getRow();
					int y = path.get(i).getCol();
					maze[x][y] = '.';
				}
			}

			for(int row = 0; row <_Rows ; row++) 
			{
				for(int col = 0; col <_Column; col++) 
				{
					System.out.print(maze[row][col]);
				}
				System.out.println();
			}


			System.out.println();

			for(int i =0; i<visitedNode.size();i++){

				System.out.print(visitedNode.get(i)+ "->");

			}

			System.out.println();
			int a = visitedNode.size();
			System.out.print("The total Number of Expanded Nodes for Greedy are" +" "+a);

		}

	}				

}

