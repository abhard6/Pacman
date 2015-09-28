import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by wsgreen on 9/27/15.
 */
public class Visualization extends JFrame {
  JLabel[][] panelHolder;

  public Visualization(char[][] map) {
    JLabel wall = new JLabel(new ImageIcon("./wall.png"));
    int rows = map.length;
    int cols = map.length;

    this.setBounds(0, 0, 30*rows, 40*cols);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panelHolder = new JLabel[rows][cols];
    setLayout(new GridLayout(rows, cols));

    for(int m = 0; m < rows; m++) {
      for(int n = 0; n < cols; n++) {

        if(map[m][n]=='%') {
          panelHolder[m][n] = new JLabel(new ImageIcon("./wall.png"));
        }
        else if(map[m][n] == 'G') {
          panelHolder[m][n] = new JLabel(new ImageIcon("./ghost.png"));
        }
        else if(map[m][n] == 'P') {
          panelHolder[m][n] = new JLabel(new ImageIcon("./pacman.png"));

        }else if(map[m][n] == '.') {
          panelHolder[m][n] = new JLabel(new ImageIcon("./gold.png"));
        } else{
          panelHolder[m][n] = new JLabel();

        }


        add(panelHolder[m][n]);
      }
    }

    this.setVisible(true);

    this.pack();

  }

  public void animate(ArrayList<GhostAstarNode> movements) {
    for(int i=1;i<movements.size();i++) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      int m = movements.get(i-1).getRow();
      int n = movements.get(i-1).getCol();
      int mm = movements.get(i).getRow();
      int nn = movements.get(i).getCol();
      panelHolder[mm][nn].setIcon(panelHolder[m][n].getIcon());

      panelHolder[m][n].setIcon(new ImageIcon());

      panelHolder[m][n].repaint();
      panelHolder[mm][nn].repaint();

      //ghost
      m = movements.get(i-1).ghost[0];
      n = movements.get(i-1).ghost[1];
      mm = movements.get(i).ghost[0];
      nn = movements.get(i).ghost[1];

      panelHolder[mm][nn].setIcon(panelHolder[m][n].getIcon());

      panelHolder[m][n].setIcon(new ImageIcon());

      panelHolder[m][n].repaint();
      panelHolder[mm][nn].repaint();
      this.repaint();
    }
  }

  public void drawCenteredCircle(Graphics g, int x, int y, int r) {
    x = x-(r/2);
    y = y-(r/2);
    g.fillOval(x,y,r,r);
  }

}
