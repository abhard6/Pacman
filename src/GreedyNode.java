/**
 * 
 */

/**
 * @author Piyush
 *
 */
public class GreedyNode {

	private int col=0;
	private int row=0;
	private int score=0;

	boolean leftNodeVisited = false;
	boolean upNodeVisited = false;
	boolean rightNodeVisited = false;
	boolean downNodeVisited = false;

	// Default constructor
	public GreedyNode()
	{
		this.col = 0;
		this.row = 0;
		this.setScore(0);
	}

	// Constructor with row and col.
	public GreedyNode(int col, int row) 
	{
		this.setCol(col);
		this.setRow(row);
	}

	// Constructor with row and col.
	public GreedyNode(int col, int row, int score) 
	{
		this.setCol(col);
		this.setRow(row);
		this.setScore(score);
	}

	//Constructor with all fields
	public GreedyNode(int col, int row, boolean leftNodeVisited,
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
