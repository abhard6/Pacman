
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFS 
{

	static int cost =0;
	//BFS prev;
	int row, col;
	boolean visited = false;
	private static List<String> visitedNode = new ArrayList<String>();
	private static String _medMazePath = "C:/Users/Piyush/Desktop/maze.txt";
	private static String _bigMazePath = "C:/Users/Piyush/Desktop/bigmaze.txt";
	private static String _openMazePath = "C:/Users/Piyush/Desktop/openMaze.txt";

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


	// BFS
	public static void newshortestPath(char[][] maze) 
	{
		int coordinaates[] = findEntrance();
		BFSNode currentNode = new BFSNode(coordinaates[0], coordinaates[1], null);
		LinkedList<BFSNode> queue = new LinkedList<BFSNode>();
		queue.add(currentNode);

		String id = (String.valueOf(coordinaates[0])+":"+String.valueOf(coordinaates[1]));
		visitedNode.add(id);

		boolean findDest = false;
		while(!queue.isEmpty()) 
		{
			currentNode = queue.remove();
			int currentRow = currentNode.getRow(), currentCol= currentNode.getCol();
			if(maze[currentRow][currentCol] == '.') 
			{	// find dest
				findDest = true;
				break;
			}

			BFSNode next;
			String nodeid = "";
			String nxtRowStr ="";
			String nxtColStr = "";
			

			// move down
			nxtRowStr = String.valueOf(currentRow+1);
			nxtColStr = String.valueOf(currentCol);
			nodeid = nxtRowStr+":"+nxtColStr;
			if(currentRow < _Rows-1 && maze[currentRow+1][currentCol] != '%'  & !visitedNode.contains(nxtRowStr+":"+nxtColStr)) 
			{
				next = new BFSNode(currentRow+1,(currentCol), currentNode);
				next.setCol(Integer.valueOf(nxtColStr));
				next.setRow(Integer.valueOf(nxtRowStr));
				visitedNode.add(nodeid);
				queue.add(next);
			}

			// move right
			nxtRowStr = String.valueOf(currentRow);
			nxtColStr = String.valueOf(currentCol+1);
			nodeid = nxtRowStr+":"+nxtColStr;
			if(currentCol < _Column -1 && maze[currentRow][currentCol + 1] != '%'  & !visitedNode.contains(nodeid)) 
			{
				next = new BFSNode(currentRow,(currentCol+1), currentNode);
				next.setCol(Integer.valueOf(nxtColStr));
				next.setRow(Integer.valueOf(nxtRowStr));
				visitedNode.add(nodeid);
				queue.add(next);
			}

			//move up
			nxtRowStr = String.valueOf(currentRow-1);
			nxtColStr = String.valueOf(currentCol);
			nodeid = nxtRowStr+":"+nxtColStr;
			if(currentRow > 0 && maze[currentRow-1][currentCol] != '%'  & !visitedNode.contains(nxtRowStr+":"+nxtColStr)) 
			{
				next = new BFSNode((currentRow-1), currentCol, currentNode);
				next.setCol(Integer.valueOf(nxtColStr));
				next.setRow(Integer.valueOf(nxtRowStr));
				visitedNode.add(nodeid);
				queue.add(next);
			}		
			

			// move left
			nxtRowStr = String.valueOf(currentRow);
			nxtColStr = String.valueOf(currentCol-1);
			nodeid = nxtRowStr+":"+nxtColStr;
			if(currentCol > 0 && maze[currentRow][currentCol - 1] != '%' & !visitedNode.contains(nodeid)) 
			{
				next = new BFSNode(currentRow,(currentCol-1), currentNode);
				Integer newRow = currentRow;
				Integer newCol = currentCol-1;
				next.setCol(newCol);
				next.setRow(newRow);
				visitedNode.add(nodeid);
				queue.add(next);
			}

		}

		if(findDest) 
		{
			// build path
			ArrayList<BFSNode> path = new ArrayList<BFSNode>();

			while(currentNode != null) 
			{
				path.add(currentNode);
				currentNode = currentNode.prev;
			}
			cost = path.size();
			Collections.reverse(path);
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
					//cost++;
					//System.out.print(path.get(i) + " -> ");
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
		}
	}

	public static void main(String[] args) {


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

			System.out.println("Finding the Shortest Path in BFS...");

			newshortestPath(maze);

			System.out.println();
			System.out.println("The cost for the Shortest path is " +getcost());
			
			System.out.print("The Expanded Nodes for BFS are...");
			
			for(int i = 0; i< visitedNode.size(); i++){
				
				
				System.out.print(visitedNode.get(i) + " -> ");
				
				
			}
			
			System.out.println();
			
			int noOfNodesexpanded = visitedNode.size();
			System.out.println("The total number of Nodes Expanded are" + " " + noOfNodesexpanded);
			
			
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


