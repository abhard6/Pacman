import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 
 */

/**
 * @author Piyush
 *
 */
public class AstarSearch {

	private static Map<String, AstarNode> _visitedNode = new HashMap<>();
	private static String _medMazePath = "./mediumMaze.txt";
	private static String _bigMazePath = "./bigMaze.txt";
	private static String _openMazePath = "./openMaze.txt";
	private static String _smallTurnPath = "./smallTurns.txt";
	private static String _largeTurnPath = "./bigTurns.txt";

  private static String _smallGhost = "./smallGhost.txt";
  private static String _mediumGhost = "./mediumGhost.txt";
  private static String _bigGhost = "./bigGhost.txt";
	
	static int _Column = 0;
	static int _Rows = 0;
	static int _cost = 0;
	static char[][] _maze;
	
	private static int[] _destination = {0,0};
	private static int[] _source = {0,0};
	private static int[] _currentNode = {0,0};
  private static int[] _ghost = {0,0};

	public static void main(String[] args) 
	{
		System.out.println("Select one of the following:");
		System.out.println("1. Medium Maze");
		System.out.println("2. Big Maze");
		System.out.println("3. Open Maze");
		System.out.println("4. Small Turn Maze");
		System.out.println("5. Big Turn Maze");
    System.out.println("6. Small Ghost Maze");
    System.out.println("7. Medium Ghost Maze");
    System.out.println("8. Big Ghost Maze");
    System.out.println("Any other key to exit");

		try 
		{
			loadFile();
			getSourceAndDestinationAndGhost();
			findPath();
			System.out.println("Finding the Shortest Path by A* algorithm");
			printArray();
			System.out.println();
			System.out.println("Cost is : "+_cost);
			System.out.println(_visitedNode.size());
			
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
    node.setF_score(node.getG_score()+node.getH_score());
    node.setCurrentlyFacing("r");
    node.ghost = _ghost;
    node.gFacing = 1;

		Comparator<AstarNode> comparator = new NodeComparator();
		PriorityQueue<AstarNode> pQueue = new PriorityQueue<>(5000, comparator);
		
		pQueue.add(node);
		_visitedNode.put(node.getRow()+":"+node.getCol(), node);

		boolean pathFound = false;
		while(!pathFound && !pQueue.isEmpty())
		{
			node = pQueue.remove();
      int[] nghost = moveGhost(node.ghost, node.gFacing, 1);

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
        int nxtRow=-1, nxtCol=-1;
        String facing = "";
				switch(neighborsDiscovered)
				{
					case 1:
						// try left
            nxtRow = currentRow;
						nxtCol = currentCol-1;
            facing ="l";
            break;
					case 0:
						//try down
						nxtRow= currentRow+1;
						nxtCol = currentCol;
            facing = "d";
						break;
						
					case 2:
						// try right
						nxtRow = currentRow;
						nxtCol = currentCol+1;
						facing = "r";
						break;
						
					case 3:
						//try up
						nxtRow = currentRow-1;
						nxtCol = currentCol;
            facing = "u";
						break;
				}
        
        if(nxtRow < 0 || nxtRow >= _maze.length || nxtCol < 0 || nxtCol >= _maze[0].length)
          continue;

        if((_maze[nxtRow][nxtCol] != '%')
                && !_visitedNode.containsKey(nxtRow+":"+nxtCol)
                && !(nxtRow == nghost[0] && nxtCol == nghost[1]) )
        {
          next = new AstarNode(nxtRow,nxtCol,node);
          next.ghost[0] = nghost[0];
          next.ghost[1] = nghost[1];
          next.gFacing = nghost[2];
          next.setRow(nxtRow);
          next.setCol(nxtCol);
          next.setG_score(calculate_g_Score(nxtRow, nxtCol));
          next.setH_score(calculate_h_Score(nxtRow, nxtCol));
          next.setF_score(next.getG_score()+next.getH_score());
          next.setCurrentlyFacing(facing);
          pQueue.add(next);
          _visitedNode.put(nxtRow+":"+nxtCol, next);
        }
			}

      int[] nkghost = moveGhost(node.ghost, node.gFacing, 2);
      node.ghost[0] = nkghost[0];
      node.ghost[1] = nkghost[1];
      node.gFacing = nkghost[2];
      node.setG_score(calculate_g_Score(currentRow, currentCol));
      node.setH_score(calculate_h_Score(currentRow, currentCol));
      node.setF_score(node.getG_score() + node.getH_score());
      pQueue.add(node);
      _visitedNode.remove(currentRow + ":" + currentCol);
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

  public static int[] moveGhost(int[] ghost, int facing, int k) {
    int[] ret = new int[]{ghost[0], ghost[1], facing};
    for(int i=0;i<k;i++) {
      if (_maze[ghost[0] + facing][ghost[1]] == 'g') {
        ret[0] = ret[0] + facing;
        ret[1] = ghost[1];
        ret[2] = facing;
      } else if (_maze[ghost[0]][ghost[1] + facing] == 'g') {
        ret[0] = ghost[0];
        ret[1] = ghost[1] + facing;
        ret[2] = facing;
      } else {
        if (_maze[ghost[0] - facing][ghost[1]] == 'g') {
          ret[0] = ghost[0] - facing;
          ret[1] = ghost[1];
          ret[2] = -1 * facing;
        } else if (_maze[ghost[0]][ghost[1] - facing] == 'g') {
          ret[0] = ghost[0];
          ret[1] = ghost[1] - facing;
          ret[2] = -1 * facing;
        }
      }
    }

    return ret;
  }
	
	public static void getSourceAndDestinationAndGhost()
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
				} else if(_maze[row][col] == 'G'){
          _ghost[0] = row;
          _ghost[1] = col;
        }
			}
			if(sourceFound & destFound)
				break;
		}
	}
	
	public static void loadFile() throws IOException
	{
		BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
		int userCommand = Integer.parseInt(userReader.readLine());

		if(userCommand >=0 && userCommand < 9){
      loadFile(userCommand);
    } else
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
    String mapFile = "";
		try 
		{
      switch(option) {
        case 1:
          mapFile = _medMazePath;
          _Column = 23;
          _Rows = 23;
          break;
        case 2:
          mapFile = _bigMazePath;
          _Column = 37;
          _Rows = 37;
          break;
        case 3:
          mapFile = _openMazePath;
          _Column = 37;
          _Rows = 20;
          break;
        case 4:
          mapFile = _smallTurnPath;
          _Column = 32;
          _Rows = 11;
          break;
        case 5:
          mapFile = _largeTurnPath;
          _Column = 37;
          _Rows = 37;
          break;
        case 6:
          mapFile = _smallGhost;
          _Column = 32;
          _Rows = 11;
          break;
        case 7:
          mapFile = _mediumGhost;
          _Column = 23;
          _Rows = 23;
          break;
        case 8:
          mapFile = _bigGhost;
          _Column = 37;
          _Rows = 37;
          break;
      }

      reader = new BufferedReader(new FileReader(mapFile));
      _maze = new char[_Rows][_Column];


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
				cost = cost + 1;
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
			if(node1.getF_score() - node2.getF_score() > 0.0)
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
		
	}
}
