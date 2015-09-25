

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	boolean visited = false;
	DepthFirst prev;
	private static List<String> visitedNode = new ArrayList<String>();


	//generation of Maze
	static	 final int WIDTH = 37;
	static final int HEIGHT = 37;

	static char[][] maze = new char[WIDTH][HEIGHT];


	public static void loadFile() 
	{
		int row= 0;
		int col = 0;

		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/Piyush/Desktop/bigmaze.txt"));
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
		for(int row = 0; row <WIDTH ; row++) 
		{
			for(int col = 0; col <HEIGHT; col++) 
			{
				System.out.print(maze[row][col]);
			}
			System.out.println();
		}
	}



	public static int[] findEntrance() 
	{	    	
		int[] coordinates = {0,0};
		for(int row = 0; row<WIDTH; row++)
		{
			for (int col = 0; col<HEIGHT; col++)
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

	public static void getPath(char[][] maze) 
	{
		int N = maze.length;
		int coordinaates[] = findEntrance();
		DFSNode currentNode = new DFSNode(coordinaates[0], coordinaates[1]);
		Stack<DFSNode> stack = new Stack<DFSNode>();
		stack.push(currentNode);
		boolean findDest = false;
		
		while(!findDest) 
		{
			currentNode = stack.peek();
			boolean hasPacmanMoved = false;
			int currentRow = currentNode.getRow(), currentCol= currentNode.getCol();
			
			if(stack.size() == 2)
				System.out.println("stack size is 1");

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

			// try up
			nxtRowStr = String.valueOf(currentRow-1);
			nxtColStr = String.valueOf(currentCol);
			char temp = maze[currentRow - 1][currentCol];
			boolean flag = !visitedNode.contains(nxtRowStr+","+nxtColStr);
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
			if(!hasPacmanMoved && (currentCol < N-1) && maze[currentRow][currentCol + 1] != '%' 
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
				
			// try down
			nxtRowStr = String.valueOf(currentRow+1);
			nxtColStr = String.valueOf(currentCol);
			if(!hasPacmanMoved && currentRow < N -1 && maze[currentRow + 1][currentCol] != '%'
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
			
			// Pacman couldn't move! Pop the current node and backtrack.
			if(!hasPacmanMoved)
			{
				stack.pop();
			}

		}

		if(findDest) 
		{
			while(!stack.isEmpty())
			{
				DFSNode d = stack.pop();
				System.out.print("["+d.getRow()+", "+d.getCol()+"]<--");
			}
		}
	}

	public static void main(String[] args) 
	{
		System.out.println("Here is the maze");
		loadFile();
		printArray();

		System.out.println("Finding the Shortest Path in DFS...");

		getPath(maze);
		System.out.println();
		System.out.println("The cost for the Shortest path is ");
	}

}



