/**
 * 
 */

/**
 * @author ashutoshbhardwaj
 *
 */
public class DFSNode {
	
	private int col=0;
	private int row=0;
	
	boolean leftNodeVisited = false;
	boolean upNodeVisited = false;
	boolean rightNodeVisited = false;
	boolean downNodeVisited = false;
	
	// Default constructor
	public DFSNode()
	{
		this.col = 0;
		this.row = 0;
	}
	
	// Constructor with row and col.
	public DFSNode(int col, int row) 
	{
		this.setCol(col);
		this.setRow(row);
	}

	
	//Constructor with all fields
	public DFSNode(int col, int row, boolean leftNodeVisited,
			boolean upNodeVisited, boolean rightNodeVisited,
			boolean downNodeVisited) 
	{
		this.setCol(col);
		this.setRow(row);
		this.leftNodeVisited = leftNodeVisited;
		this.upNodeVisited = upNodeVisited;
		this.rightNodeVisited = rightNodeVisited;
		this.downNodeVisited = downNodeVisited;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public boolean isLeftNodeVisited() {
		return leftNodeVisited;
	}

	public void setLeftNodeVisited(boolean leftNodeVisited) {
		this.leftNodeVisited = leftNodeVisited;
	}

	
	public boolean isUpNodeVisited() {
		return upNodeVisited;
	}

	public void setUpNodeVisited(boolean upNodeVisited) {
		this.upNodeVisited = upNodeVisited;
	}

	
	public boolean isRightNodeVisited() {
		return rightNodeVisited;
	}

	public void setRightNodeVisited(boolean rightNodeVisited) {
		this.rightNodeVisited = rightNodeVisited;
	}

	
	
	public boolean isDownNodeVisited() {
		return downNodeVisited;
	}
	

	public void setDownNodeVisited(boolean downNodeVisited) {
		this.downNodeVisited = downNodeVisited;
	}
	

}
