

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ashutoshbhardwaj
 *
 */

public class DepthFirst {
	int row, col;
	static int cost =0;
	static int noOfNodesexpanded = 0;
	boolean visited = false;
	DepthFirst prev;
	private static List<String> visitedNode = new ArrayList<String>();
	private static String _medMazePath = "C:/Users/Piyush/Desktop/maze.txt";
	private static String _bigMazePath = "C:/Users/Piyush/Desktop/bigmaze.txt";
	private static String _openMazePath = "C:/Users/Piyush/Desktop/openMaze.txt";


	//generation of Maze
	static int _Column = 0;
	static int _Rows = 0;

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



	public static int[] findEntrance() 
	{	    	
		int[] coordinates = {0,0};
		for(int row = 0; row<_Rows; row++)
		{
			for (int col = 0; col<_Column; col++)
			{
				if(maze[row][col]== 'P')
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

		return cost;
	}

	public static int getnoOfNodesexpanded(){


		return noOfNodesexpanded;
	}

	public static void getPath(char[][] maze) 
	{
		int coordinaates[] = findEntrance();
		DFSNode currentNode = new DFSNode(coordinaates[0], coordinaates[1]);
		Stack<DFSNode> stack = new Stack<DFSNode>();
		stack.push(currentNode);
		boolean findDest = false;
		visitedNode.add(String.valueOf(coordinaates[0])+","+String.valueOf(coordinaates[1]));

		while(!findDest) 
		{
			currentNode = stack.peek();
			boolean hasPacmanMoved = false;
			int currentRow = currentNode.getRow(), currentCol= currentNode.getCol();

			if(maze[currentRow][currentCol] == '.') 
			{
				findDest = true;
				break;
			}

			DFSNode next;

			// try left
			String nxtRowStr = String.valueOf(currentRow);
			String nxtColStr = String.valueOf(currentCol-1);
			if( (currentCol > 0) && (maze[currentRow][currentCol - 1] != '%') & !visitedNode.contains(nxtRowStr+","+nxtColStr)) 
			{
				next = new DFSNode(currentRow, (currentCol-1));
				currentNode.leftNodeVisited = true;
				Integer newRow = currentRow;
				Integer newCol = currentCol-1;
				next.setCol(newCol);
				next.setRow(newRow);
				String nodeId = newRow.toString()+","+newCol.toString();
				visitedNode.add(nodeId);
				stack.push(next);
				hasPacmanMoved = true;
			}

			// try down
			nxtRowStr = String.valueOf(currentRow+1);
			nxtColStr = String.valueOf(currentCol);
			if(!hasPacmanMoved && currentRow < _Rows -1 && maze[currentRow + 1][currentCol] != '%'
					& !visitedNode.contains(nxtRowStr+","+nxtColStr)) 
			{
				next = new DFSNode((currentRow+1), currentCol);
				currentNode.downNodeVisited = true;
				Integer newRow = currentRow+1;
				Integer newCol = currentCol;
				next.setCol(newCol);
				next.setRow(newRow);
				String nodeId = newRow.toString()+","+newCol.toString();
				stack.push(next);
				visitedNode.add(nodeId);
				hasPacmanMoved = true;
			}
			// try up
			nxtRowStr = String.valueOf(currentRow-1);
			nxtColStr = String.valueOf(currentCol);
			//char temp = maze[currentRow - 1][currentCol];
			//boolean flag = !visitedNode.contains(nxtRowStr+","+nxtColStr);
			if(!hasPacmanMoved && currentRow > 0 && maze[currentRow - 1][currentCol] != '%'
					& !visitedNode.contains(nxtRowStr+","+nxtColStr)) 
			{
				next = new DFSNode((currentRow-1), currentCol);
				currentNode.upNodeVisited = true;
				Integer newRow = currentRow-1;
				Integer newCol = currentCol;
				next.setCol(newCol);
				next.setRow(newRow);
				String nodeId = newRow.toString()+","+newCol.toString();
				stack.push(next);
				visitedNode.add(nodeId);
				hasPacmanMoved = true;
			}

			// try right
			nxtRowStr = String.valueOf(currentRow);
			nxtColStr = String.valueOf(currentCol+1);
			if(!hasPacmanMoved && (currentCol < _Column-1) && maze[currentRow][currentCol + 1] != '%' 
					& !visitedNode.contains(nxtRowStr+","+nxtColStr))
			{
				next = new DFSNode(currentRow, (currentCol+1));
				currentNode.rightNodeVisited = true;
				Integer newRow = currentRow;
				Integer newCol = currentCol+1;
				next.setCol(newCol);
				next.setRow(newRow);
				String nodeId = newRow.toString()+","+newCol.toString();
				visitedNode.add(nodeId);
				stack.push(next);
				hasPacmanMoved = true;			
			}

			// Pacman couldn't move! Pop the current node and backtrack.
			if(!hasPacmanMoved)
			{
				stack.pop();
			}

		}

		if(findDest) 
		{
			cost = stack.size();
			while(!stack.isEmpty())
			{

				DFSNode d = stack.pop();
				//cost++;
				System.out.print("["+d.getRow()+", "+d.getCol()+"]<--");
				maze[d.getRow()][d.getCol()] = '.';
			}
			System.out.println();

			for(int row = 0; row <_Rows ; row++) 
			{
				for(int col = 0; col <_Column; col++) 
				{
					System.out.print(maze[row][col]);
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) 
	{



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

			System.out.println("Here is the maze");
			printArray();

			System.out.println("Finding the Shortest Path in DFS...");

			getPath(maze);
			System.out.println();
			System.out.println("The cost for the Shortest path is " + getcost());

			System.out.println("The Expanded Nodes for DFS are...");	

			for( int i =0; i<visitedNode.size(); i++){

				System.out.print(visitedNode.get(i)+ "->");
			}

			System.out.println();

			int noOfNodesExpanded = visitedNode.size();
			System.out.println("The total number of Nodes Expanded for DFS are" + " " +noOfNodesExpanded);

		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



