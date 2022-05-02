//import require classes and packages
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;

//Extends JPanel class
public class Plotting extends JPanel {
    //initialize coordinates
    int[] cord = {65, 20, 40, 80};
    int marg = 60;
    static ArrayList<Coord> coordArrayList = new ArrayList<>();
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
        for (int i = 0; i < coordArrayList.size(); i++) {
            double x1 = marg + scale * coordArrayList.get(i)._x * 2;
            double y1 = marg + scale * coordArrayList.get(i)._y;
            graph.fill(new Ellipse2D.Double(x1-4, y1-4, 10, 10));
        }
    }

    //create getMax() method to find maximum value
    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < coordArrayList.size(); i++) {
            if (coordArrayList.get(i)._x > max)
                max = coordArrayList.get(i)._x;
        }
        return max;
    }

    //main() method start
    public static void main(String args[]) {
        int negativeX = 0;
        int negativeY = 0;
        try{
            File file = new File("CSVFiles/"+args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";

            while((line = br.readLine()) != null)
            {
                String[] coordValues = line.split(",");
                if(Integer.parseInt(coordValues[0])<negativeX)
                {
                    negativeX = Integer.parseInt(coordValues[0]);
                }
                if(Integer.parseInt(coordValues[1])<negativeY)
                {
                    negativeY = Integer.parseInt(coordValues[1]);
                }
                Coord lineValue = new Coord(Integer.parseInt(coordValues[0]), Integer.parseInt(coordValues[1]));
                coordArrayList.add(lineValue);
            }
            negativeX*=-1;
            negativeY*=-1;
            for(int arrayIndex = 0; arrayIndex<coordArrayList.size(); arrayIndex++)
            {
                int coordX = coordArrayList.get(arrayIndex)._x;
                int coordY = coordArrayList.get(arrayIndex)._y;
                coordX+=negativeX;
                coordY+=negativeY;
                System.out.println(coordX + "," + coordY);
                Coord replaceCoord = new Coord(coordX,coordY);
                coordArrayList.set(arrayIndex,replaceCoord);
            }
        }
        catch(Exception e)
        {
            System.err.println("File not found");
            System.exit(1);
        }
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Plotting());
        frame.setSize(negativeX, negativeY);
        frame.setLocation(0, 0);
        frame.setVisible(true);
    }

    public static class Coord
    {
        int _x;
        int _y;
        /*
        *set coordinates
        */
        public Coord(int x, int y)
        {
            _x = x;
            _y = y;
        }
    }
}