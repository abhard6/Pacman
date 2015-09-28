/**
 * 
 */

/**
 * @author Piyush
 *
 */
public class GhostAstarNode {

	private int row=0;
	private int col=0;
	private double g_score=0;
	private double h_score=0;
	private double f_score=0;

	GhostAstarNode parentNode=null;
	private String currentlyFacing = "";

	public int[] ghost = new int[2];
	public int gFacing = 1;

  public GhostAstarNode(GhostAstarNode copy) {
    if(copy != null) {
      this.row = copy.row;
      this.col = copy.col;
    }
  }

	public GhostAstarNode(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	public GhostAstarNode(int row, int col, GhostAstarNode parentNode)
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
	public double getG_score() {
		return g_score;
	}
	public void setG_score(double g_score) {
		this.g_score = g_score;
	}
	public double getH_score() {
		return h_score;
	}
	public void setH_score(double h_score) {
		this.h_score = h_score;
	}

	public double getF_score() {
		return f_score;
	}

	public void setF_score(double f_score) {
		this.f_score = f_score;
	}
	
	public GhostAstarNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(GhostAstarNode parentNode) {
		this.parentNode = parentNode;
	}

	public String getCurrentlyFacing() {
		return currentlyFacing;
	}

	public void setCurrentlyFacing(String currentlyFacing) {
		this.currentlyFacing = currentlyFacing;
	}

}
