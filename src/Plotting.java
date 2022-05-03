//import require classes and packages
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//Extends JPanel class
public class Plotting {
    //initialize coordinates

    class Frame extends JPanel {

    int[] cord = {65, 20, 40, 80};
    int marg = 60;
    List<Node> stars;
    List<Node> finalPath;

        public Frame(List<Node> starList, List<Node> finalPathList) {
            stars = starList;
            finalPath = finalPathList;
            int maxNegativeX = 0;
            int maxNegativeY = 0;

            //Find largest negative value
            for (Node node : stars) {
                if (node.x < maxNegativeX) {
                    maxNegativeX = node.x;
                }
                if (node.y < maxNegativeY) {
                    maxNegativeY = node.y;
                }
            }

            //Get & apply offset
            maxNegativeX = convertToPositiveOffset(maxNegativeX);
            maxNegativeY = convertToPositiveOffset(maxNegativeY);
            for (Node node : stars) {
                node.y = node.y + maxNegativeY;
                node.x = node.x + maxNegativeX;
            }
            for(Node node: finalPath){
                node.y = node.y + maxNegativeY;
                node.x = node.x + maxNegativeX;
            }

        }


    protected void paintComponent(Graphics grf) {
        //create instance of the Graphics to use its methods
        super.paintComponent(grf);
        Graphics2D graph = (Graphics2D) grf;

        //Sets the value of a single preference for the rendering algorithms.
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // get width and height
        int width = getWidth();
        int height = getHeight();

        // draw graph
        graph.draw(new Line2D.Double(marg, marg, marg, height - marg));
        graph.draw(new Line2D.Double(marg, height - marg, width - marg, height - marg));

        //find value of x and scale to plot points
        double scale = (double) (height - 4 * marg) / getMax();

        //set color for points
        graph.setPaint(Color.RED);

        // set points to the graph
        for (int i = 0; i < stars.size(); i++) {
            double x1 = marg + scale * stars.get(i).x * 2;
            double y1 = marg + scale * stars.get(i).y;
            graph.fill(new Ellipse2D.Double(x1 - 4, y1 - 4, 10, 10));
        }

        //Draw finalPath
        for(int i = 0; i < finalPath.size()-1; i++){//size()-1 otherwise at end of list outOfBounds
            graph.draw(new Line2D.Double(finalPath.get(i).x, finalPath.get(i).y, finalPath.get(i+1).x, finalPath.get(i+1).y));
        }
    }

    //create getMax() method to find maximum value
    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).x > max)
                max = stars.get(i).x;
        }
        return max;
    }

    public int convertToPositiveOffset(int num) {
        if (num < 0) {
            return num * -1;
        }
        return 0;//offset is nothing if everything is positive
    }

}

    public Plotting(List<Node> stars, List<Node> finalPath) {
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new Frame(stars, finalPath));
        frame.setLocation(0, 0);
        frame.setVisible(true);

    }
}

