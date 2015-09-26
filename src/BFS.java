
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

	public class BFS {
		int i, j;
		static int cost =0;
		BFS prev;

		public BFS(int[] i, BFS prev) {
			this.i = i[0];
			this.j = i[1];
			this.prev = prev;
		}
		public String toString() {
			return "[" + i + ", " + j + "]";
		}
		 
		 //generation of Maze
		 
	static	 final int WIDTH =  24;
	     static final int HEIGHT = 24 ;
	     
	     static int start_row;
	     static int start_col;
	     
	    static char[][] maze = new char[WIDTH][HEIGHT];
		 
		 
		 public static void loadFile(String fname) {



				int row= 0;
				int col = 0;

				try {
				    BufferedReader reader = new BufferedReader(new FileReader("/Users/ashutoshbhardwaj/desktop/maze.txt"));
				    String line;
				    while((line = reader.readLine()) != null) {
				        for(col = 0; col < line.length();col++) {
				            maze[row][col] = line.charAt(col);
				        }
				        row++;
				    }

				    reader.close();
				} catch(IOException e) {
				    e.printStackTrace();
				}
				}

				public static void printArray() {
				for(int row = 0; row <WIDTH ; row++) {
				    for(int col = 0; col <HEIGHT; col++) {
				        System.out.print(maze[row][col]);
				    }
				    System.out.println();
				}
				}
				
				
				
				 public static int[] findEntrance() {
////			    	
			    	int[] coordinates = {0,0};
			    	
			    	
			    for(int row = 0; row<WIDTH; row++){
			    	
			    	for (int col = 0; col<HEIGHT; col++){
			    		
			    		if(maze[row][col]== 'P'){
			    			start_col = col;
			    			start_row = row;
			    			
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
		public static void newshortestPath(char[][] maze) {
			int N = maze.length;
			// initial
			BFS step = new BFS(findEntrance(), null);
			LinkedList<BFS> queue = new LinkedList<BFS>();
			queue.add(step);
			// using set to check if already traversed
			HashSet<Integer> set = new HashSet<Integer>();
			boolean findDest = false;
				while(!queue.isEmpty()) {
					step = queue.remove();
					int i = step.i, j = step.j, id;
					if(maze[i][j] == '.') {	// find dest
						findDest = true;
						break;
					}
					
							
					
					BFS next;
					// move left
					char temp = maze[i][j - 1];
					if(j > 0 && temp != '%') {
						id = N * i + (j - 1);
						if(!set.contains(id)) {
							set.add(id);
							int[] a = {i,j-1};
							next = new BFS(a, step);
							queue.add(next);
						}
					}
					
					// move right
					if(j < N-1 && maze[i][j + 1] != '%') {
						id = N * i + (j + 1);
						if(!set.contains(id)) {
							set.add(id);
							int[] a = {i,j+1};
							next = new BFS(a, step);
							queue.add(next);
						}
					}
					
					//move up
					
					if(i > 0 && maze[i - 1][j] != '%') {
						id = N * (i - 1) + j;
						if(!set.contains(id)) {
							set.add(id);
							int[] a = {i-1,j};
							next = new BFS(a, step);
							queue.add(next);
						}
					}
					
					
					// move down
					if(i < N -1 && maze[i + 1][j] != '%') {
						id = N * (i + 1) + j;
						if(!set.contains(id)) {
							set.add(id);
							int[] a = {i+1,j};
							next = new BFS(a, step);
							queue.add(next);
						}
					}
				}
			
	
			if(findDest) {
				// build path
				ArrayList<ShortestPath> path = new ArrayList<ShortestPath>();
				
				while(step != null) {
					path.add(step);
					step = step.prev;
				}
				Collections.reverse(path);
				// print path
				for(int i = 0; i < path.size(); i++) {
					if(i == path.size() - 1) {
						System.out.println(path.get(i));
					}
					else {
						int x = path.get(i).i;
						int y = path.get(i).j;
						maze[x][y] = '*';
						cost++;
						System.out.print(path.get(i) + " -> ");
					}
				}
				
				for(int row = 0; row <WIDTH ; row++) {
				    for(int col = 0; col <HEIGHT; col++) {
				        System.out.print(maze[col][row]);
				    }
				    System.out.println();
				}
			}
		}

		public static void main(String[] args) {
				
			
			
			System.out.println("Here is the maze");
			
			//Mazegeneration array = new Mazegeneration();
	        loadFile("maze.txt");
	        printArray();

			System.out.println("Finding the Shortest Path...");
			
			newshortestPath(maze);
			System.out.println("The cost for the Shortest path is " +getcost());
		}
	 
	 }
	
	
