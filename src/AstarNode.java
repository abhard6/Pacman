/**
 * 
 */

/**
 * @author Piyush
 *
 */
public class AstarNode {
	
	private int row=0;
	private int col=0;
	private int g_score=0;
	private int h_score=0;
	private int f_score=0;
	
	AstarNode parentNode=null;

	boolean leftNodeVisited = false;
	boolean upNodeVisited = false;
	boolean rightNodeVisited = false;
	boolean downNodeVisited = false;
	
	public AstarNode(int row, int col) 
	{
		this.row = row;
		this.col = col;
	}
	
	public AstarNode(int row, int col, AstarNode parentNode)
	{
		this.row = row;
		this.col = col;
		this.parentNode = parentNode;
	}
	
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getG_score() {
		return g_score;
	}
	public void setG_score(int g_score) {
		this.g_score = g_score;
	}
	public int getH_score() {
		return h_score;
	}
	public void setH_score(int h_score) {
		this.h_score = h_score;
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

	public int getF_score() {
		return f_score;
	}

	public void setF_score(int f_score) {
		this.f_score = f_score;
	}
	
	public AstarNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(AstarNode parentNode) {
		this.parentNode = parentNode;
	}
		

}