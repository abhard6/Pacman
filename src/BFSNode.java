
public class BFSNode {
	
	private int col=0;
	private int row=0;

	BFSNode prev;
	
	boolean leftNodeVisited = false;
	boolean upNodeVisited = false;
	boolean rightNodeVisited = false;
	boolean downNodeVisited = false;
	
	// Default constructor
	public BFSNode()
	{
		this.col = 0;
		this.row = 0;
	}
	
	// Constructor with row, col and prev node
	public BFSNode( int row, int col,BFSNode prev) 
	{
		this.setCol(col);
		this.setRow(row);
		this.prev = prev;
	}

	
	//Constructor with all fields
	public BFSNode(int row, int col, boolean leftNodeVisited,
			boolean upNodeVisited, boolean rightNodeVisited,
			boolean downNodeVisited) 
	{
		this.setCol(col);
		this.setRow(row);
		this.leftNodeVisited = leftNodeVisited;
		this.upNodeVisited = upNodeVisited;
		this.rightNodeVisited = rightNodeVisited;
		this.downNodeVisited = downNodeVisited;
		//this.currentNode = currentNode;
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

