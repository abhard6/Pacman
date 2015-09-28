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
 * @author Piyush
 *
 */
public class AstarSearch {

	private static List<String> _visitedNodeStr = new ArrayList<String>();
	private static List<AstarNode> _visitedNode = new ArrayList<AstarNode>();
	private static String _medMazePath = "C:/Users/Piyush/Desktop/maze.txt";
	private static String _bigMazePath = "C:/Users/Piyush/Desktop/bigmaze.txt";
	private static String _openMazePath = "C:/Users/Piyush/Desktop/openMaze.txt";
	private static String _smallTurnPath = "C:/Users/Piyush/Desktop/smallTurns.txt";
	private static String _largeTurnPath = "C:/Users/Piyush/Desktop/bigTurns.txt";
	
	static int _Column = 0;
	static int _Rows = 0;
	static int _cost = 0;
	static char[][] _maze;
	
	private static int[] _destination = {0,0};
	private static int[] _source = {0,0};
	private static int[] _currentNode = {0,0};
	
	public static void main(String[] args) 
	{
		System.out.println("Select one of the following:");
		System.out.println("1. Medium Maze");
		System.out.println("2. Big Maze");
		System.out.println("3. Open Maze");
		System.out.println("4. Small Turn Maze");
		System.out.println("5. Big Turn Maze");
		System.out.println("Any other key to exit");

		try 
		{
			loadFile();
			getSourceAndDestination();
			findPath();
			System.out.println("Finding the Shortest Path by A* algorithm");
			printArray();
			System.out.println();
			System.out.print("Cost is : "+_cost +" And ");
			System.out.println("Nbr of Expanded nodes:"+_visitedNode.size());
			
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
	
	public static void findPath()
	{
		AstarNode node = new AstarNode(_currentNode[0],_currentNode[1],null);
		node.setG_score(calculate_g_Score(_currentNode[0], _currentNode[1]));
		node.setH_score(calculate_h_Score(_currentNode[0], _currentNode[1]));
		node.setE_score(calculate_euclidean_Score(_currentNode[0], _currentNode[1]));
		node.setF_score(node.getG_score()+node.getH_score());
		node.setCurrentlyFacing("r");
		
		//_visitedNode.add(String.valueOf(_currentNode[0])+":"+String.valueOf(_currentNode[1]));
		
		Comparator<AstarNode> comparator = new NodeComparator();
		PriorityQueue<AstarNode> pQueue = new PriorityQueue<AstarNode>(5000, comparator);
		
		pQueue.add(node);
		_visitedNode.add(node);
		_visitedNodeStr.add(String.valueOf(node.getRow())+":"+String.valueOf(node.getCol()));
		
		boolean pathFound = false;
		while(!pathFound)
		{
			//boolean hasPacmanMoved = false;
			node = pQueue.remove();
			//_visitedNode.add(node);
			//_visitedNodeStr.add(String.valueOf(node.getRow())+":"+String.valueOf(node.getCol()));
			int currentRow = node.getRow();
			int currentCol= node.getCol();
			
			if(_maze[currentRow][currentCol] == '.') 
			{
				pathFound = true;
				break;
			}
			
			AstarNode next;
			for(int neighborsDiscovered =0; neighborsDiscovered<4; neighborsDiscovered++)
			{
				switch(neighborsDiscovered)
				{
					case 0:
						// try left
						String nxtRowStr = String.valueOf(currentRow);
						String nxtColStr = String.valueOf(currentCol-1);
						if( (currentCol > 0) & (_maze[currentRow][currentCol - 1] != '%') 
								& !_visitedNodeStr.contains(nxtRowStr+":"+nxtColStr)) 
						{
							node.leftNodeVisited = true;
							next = new AstarNode(currentRow, (currentCol-1),node);
							Integer newRow = currentRow;
							Integer newCol = currentCol-1;
							next.setRow(newRow);
							next.setCol(newCol);
							next.setG_score(calculate_g_Score(newRow, newCol));
							next.setH_score(calculate_h_Score(newRow, newCol));
							next.setE_score(calculate_euclidean_Score(newRow, newCol));
							next.setE_score(next.getG_score()+next.getH_score());
							next.setCurrentlyFacing("l");
							pQueue.add(next);
							_visitedNodeStr.add(nxtRowStr+":"+nxtColStr);
							_visitedNode.add(next);
						}
						break;
						
					case 1:
						//try down
						nxtRowStr = String.valueOf(currentRow+1);
						nxtColStr = String.valueOf(currentCol);
						if( (currentCol > 0) & (_maze[currentRow+1][currentCol] != '%') 
								& !_visitedNodeStr.contains(nxtRowStr+":"+nxtColStr)) 
						{
							node.downNodeVisited = true;
							next = new AstarNode((currentRow+1), currentCol,node);
							Integer newRow = currentRow+1;
							Integer newCol = currentCol;
							next.setRow(newRow);
							next.setCol(newCol);
							next.setG_score(calculate_g_Score(newRow, newCol));
							next.setH_score(calculate_h_Score(newRow, newCol));
							next.setF_score(next.getG_score()+next.getH_score());
							next.setE_score(calculate_euclidean_Score(newRow, newCol));
							next.setCurrentlyFacing("d");
							pQueue.add(next);
							_visitedNodeStr.add(nxtRowStr+":"+nxtColStr);
							_visitedNode.add(next);
						}
						break;
						
					case 3:
						// try right
						nxtRowStr = String.valueOf(currentRow);
						nxtColStr = String.valueOf(currentCol+1);
						if( (currentCol > 0) & (_maze[currentRow][currentCol + 1] != '%') 
								& !_visitedNodeStr.contains(nxtRowStr+":"+nxtColStr)) 
						{
							node.rightNodeVisited = true;
							next = new AstarNode(currentRow, (currentCol+1),node);
							Integer newRow = currentRow;
							Integer newCol = currentCol+1;
							next.setRow(newRow);
							next.setCol(newCol);
							next.setG_score(calculate_g_Score(newRow, newCol));
							next.setH_score(calculate_h_Score(newRow, newCol));
							next.setF_score(next.getG_score()+next.getH_score());
							next.setE_score(calculate_euclidean_Score(newRow, newCol));
							next.setCurrentlyFacing("r");
							pQueue.add(next);
							_visitedNodeStr.add(nxtRowStr+":"+nxtColStr);
							_visitedNode.add(next);
						}
						break;
						
					case 2:
						//try up
						nxtRowStr = String.valueOf(currentRow-1);
						nxtColStr = String.valueOf(currentCol);
						if( (currentRow > 0) & (_maze[currentRow-1][currentCol] != '%') 
								& !_visitedNodeStr.contains(nxtRowStr+":"+nxtColStr)) 
						{
							node.rightNodeVisited = true;
							next = new AstarNode((currentRow-1), currentCol,node);
							Integer newRow = currentRow-1;
							Integer newCol = currentCol;
							next.setRow(newRow);
							next.setCol(newCol);
							next.setG_score(calculate_g_Score(newRow, newCol));
							next.setH_score(calculate_h_Score(newRow, newCol));
							next.setF_score(next.getG_score()+next.getH_score());
							next.setE_score(calculate_euclidean_Score(newRow, newCol));
							next.setCurrentlyFacing("u");
							pQueue.add(next);
							_visitedNodeStr.add(nxtRowStr+":"+nxtColStr);
							_visitedNode.add(next);
						}
						break;
				}
			}
		}
		
		if(!pathFound)
			return;
		
		ArrayList<AstarNode> printList = new ArrayList<AstarNode>();
		while(node!=null)
		{
			printList.add(node);
			node = node.getParentNode();
		}
		//_cost = printList.size();
		
		Collections.reverse(printList);
		_cost = computeCost(printList);
		
		for(int i = 0; i < printList.size(); i++) 
		{
			if(i == printList.size() - 1) 
			{
				System.out.println(printList.get(i));
			}
			else 
			{
				int x = printList.get(i).getRow();
				int y = printList.get(i).getCol();
				_maze[x][y] = '*';
				//cost++;
				System.out.print(printList.get(i) + " <- ");
			}
		}
		
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
	
	public static void loadFile() throws IOException
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
		else if(userCommand.equalsIgnoreCase("4"))
		{
			loadFile(4);
		}
		else if(userCommand.equalsIgnoreCase("5"))
		{
			loadFile(5);
		}
		else
		{
			System.out.println("Invalid option");
			return;
		}

		System.out.println("Here is the maze");
		printArray();
	}
	
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
				_maze = new char[_Rows][_Column];
			}
			else if(option == 2)
			{
				reader = new BufferedReader(new FileReader(_bigMazePath));
				_Column = 37;
				_Rows = 37;
				_maze = new char[_Rows][_Column];
			}
			else if(option == 3)
			{
				reader = new BufferedReader(new FileReader(_openMazePath));
				_Column = 37;
				_Rows = 20;
				_maze = new char[_Rows][_Column];
			}
			else if(option == 4)
			{
				reader = new BufferedReader(new FileReader(_smallTurnPath));
				_Column = 32;
				_Rows = 11;
				_maze = new char[_Rows][_Column];
			}
			else if(option == 5)
			{
				reader = new BufferedReader(new FileReader(_largeTurnPath));
				_Column = 37;
				_Rows = 37;
				_maze = new char[_Rows][_Column];
			}

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
		for(int row = 0; row <_Rows ; row++) 
		{
			for(int col = 0; col <_Column; col++) 
			{
				System.out.print(_maze[row][col]);
			}
			System.out.println();
		}
	}



	public static int[] findEntrance() 
	{	    	
		int[] coordinates = {0,0};
		for(int row = 0; row<_Rows; row++)
		{
			for (int col = 0; col<_Column; col++)
			{
				if(_maze[row][col]== 'P')
				{
					coordinates[0]= row;
					coordinates[1]= col;
					return coordinates;
				}
			}
		}
		return coordinates;
	}

	public static int getcost(){

		return _cost;
	}

	
	public static int computeCost(ArrayList<AstarNode> printList)
	{
		int cost = 0;
		AstarNode prevnode = printList.get(0);
		for(int i = 1; i < printList.size(); i++)
		{
			AstarNode currnode = printList.get(i);
			String prevNodeFacing = prevnode.getCurrentlyFacing();
			String currNodeFacing = currnode.getCurrentlyFacing();
			
			if(prevNodeFacing.equalsIgnoreCase(currNodeFacing))
				cost = cost + 2;
			else
				cost =cost + 3;
			
			prevnode = currnode;
		}
		return cost;
	}


	public static double calculate_g_Score(int x, int y)
	{
		/*calculateHeuristics(dest, currentNode):
		  Manhattan distance from point a to point b
		  abs(dest.x - currentNode.x) + abs(dest.y - currentNode.y)*/
		
		double score = 0;
		score = (java.lang.Math.abs(x - _source[0]))
		+(java.lang.Math.abs(y - _source[1]));
		
		//score = score*(1+0.01);
		
		return score;
	}
	
	public static double calculate_h_Score(int x, int y)
	{
		/*calculateHeuristics(x, y):
		  Manhattan distance from point a to point b
		  abs(x - destinationNode.x) + abs(y - destinationNode.y)*/
		
		double score = 0;
		score = (java.lang.Math.abs(x - _destination[0]))
		+(java.lang.Math.abs(y - _destination[1]));
		
		score = score*(1+0.01);
		
		return score;
	}
	
	public static double calculate_euclidean_Score(int x, int y)
	{
		/*calculateHeuristics(x, y):
		  Manhattan distance from point a to point b
		  abs(x - destinationNode.x) + abs(y - destinationNode.y)*/
		
		double score = 0;
		double arg1 = (java.lang.Math.abs(x - _destination[0]))
				*(java.lang.Math.abs(x - _destination[0]));
		double arg2 = (java.lang.Math.abs(y - _destination[1]))
				*(java.lang.Math.abs(y - _destination[1]));
		score = java.lang.Math.sqrt(arg1+arg2);
		
		return score;
	}
	
	public static class NodeComparator implements Comparator<AstarNode>
	{
		/*public int compare(AstarNode node1, AstarNode node2) 
		{
			if(node1.getF_score() - node2.getF_score() > 1.0)
				return 2;
			else if(node1.getF_score() - node2.getF_score() > 0.0)
				return 1;
			else if((node1.getF_score() - node2.getF_score()) == 0.0)
				return 0;
			else if((node1.getF_score() - node2.getF_score()) > -1.0)
				return -1;
			else
				return -2;
		}*/
		
		public int compare(AstarNode node1, AstarNode node2) 
		{
			if(node1.getF_score() - node2.getF_score() < 0.0)
				return 2;
			else if((node1.getF_score() - node2.getF_score()) == 0.0)
			{
				if(node1.getH_score() - node2.getH_score() > 0.0)
					return 1;
				else if(node1.getH_score() - node2.getH_score() == 0.0)
					return 0;
				else
					return -1;
				//return 0;
			}
			else
				return -2;
		}
		
		/*public int compare(AstarNode node1, AstarNode node2) 
		{
			if(node1.getE_score() - node2.getE_score() > 0.0)
				return 2;
			else if((node1.getE_score() - node2.getE_score()) == 0.0)
			{
				if(node1.getH_score() - node2.getH_score() > 0.0)
					return 1;
				else if(node1.getH_score() - node2.getH_score() == 0.0)
					return 0;
				else
					return -1;
				//return 0;
			}
			else
				return -2;
		}*/
		
	}
}
