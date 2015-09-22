
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

	public class ShortestPath {
		int i, j;
		ShortestPath prev;

		public ShortestPath(int i, int j, ShortestPath prev) {
			this.i = i;
			this.j = j;
			this.prev = prev;
		}
		public String toString() {
			return "[" + i + ", " + j + "]";
		}
		 
		 //generation of Maze
		 
	static	 final int WIDTH = 23;
	     static final int HEIGHT = 23;
	     
	    static char[][] maze = new char[WIDTH][HEIGHT];
		 
		 
		 public static void loadFile(String fname) {



				int row= 0;
				int col = 0;

				try {
				    BufferedReader reader = new BufferedReader(new FileReader("/Users/ashutoshbhardwaj/desktop/maze.txt"));
				    String line;
				    while((line = reader.readLine()) != null) {
				        for(col = 0; col < line.length();col++) {
				            maze[col][row] = line.charAt(col);
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
				        System.out.print(maze[col][row]);
				    }
				    System.out.println();
				}
				}
		 
		 
		// BFS
		public static void newshortestPath(char[][] maze) {
			int N = maze.length;
			// initial
			ShortestPath step = new ShortestPath(22, 22, null);
			LinkedList<ShortestPath> queue = new LinkedList<ShortestPath>();
			queue.add(step);
			// using set to check if already traversed
			HashSet<Integer> set = new HashSet<Integer>();
			boolean findDest = false;
			while(!queue.isEmpty() && !findDest) {
				LinkedList<ShortestPath> tmpQueue = new LinkedList<ShortestPath>();
				while(!queue.isEmpty()) {
					step = queue.remove();
					int i = step.i, j = step.j, id;
					if(maze[i][j] == '.') {	// find dest
						findDest = true;
						break;
					}
					ShortestPath next;
					// move left
					char temp = maze[i][j - 1];
					if(j > 0 && temp != '%') {
						id = N * i + (j - 1);
						if(!set.contains(id)) {
							set.add(id);
							next = new ShortestPath(i, j - 1, step);
							tmpQueue.add(next);
						}
					}
					// move right
					if(j < N - 1 && maze[i][j + 1] != '%') {
						id = N * i + (j + 1);
						if(!set.contains(id)) {
							set.add(id);
							next = new ShortestPath(i, j + 1, step);
							tmpQueue.add(next);
						}
					}
					// move up
					if(i > 0 && maze[i - 1][j] != '%') {
						id = N * (i - 1) + j;
						if(!set.contains(id)) {
							set.add(id);
							next = new ShortestPath(i - 1, j, step);
							tmpQueue.add(next);
						}
					}
					// move down
					if(i < N - 1 && maze[i + 1][j] != '%') {
						id = N * (i + 1) + j;
						if(!set.contains(id)) {
							set.add(id);
							next = new ShortestPath(i + 1, j, step);
							tmpQueue.add(next);
						}
					}
				}
				queue = tmpQueue;
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
						System.out.print(path.get(i) + " -> ");
					}
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
		}
	 
	 }
	
	
