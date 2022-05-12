//import require classes and packages

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.util.List;

//Extends JPanel class
public class Plotting {
    //initialize coordinates
    JFrame frame;

    public Plotting(List<Node> stars, Node[] nodesToDraw, Node start, Node goalStar) {
        //create an instance of JFrame class
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocation(0, 0);

        frame.add(new Frame(stars, nodesToDraw, start, goalStar));

        frame.setVisible(true);
    }

    public void setup(List<Node> stars, Node[] nodesToDraw, Node start, Node goalStar){
        frame.invalidate();
        frame.add(new Frame(stars, nodesToDraw, start,  goalStar));

        frame.setVisible(true);

    }


    class Frame extends JPanel {

        double offsetX;
        double offsetY;
        int marg = 20;
        double widthScale = 1;
        double heightScale = 1;
        List<Node> stars;
        Node[] endOfPath;
        Node goalStar;
        Node beginningOfPath;
        public Frame(List<Node> starList, Node[] endOfPath, Node beginningOfPath,  Node goalStar) {
            stars = starList;
            this.endOfPath = endOfPath;
            this.goalStar = goalStar;
            this.beginningOfPath = beginningOfPath;


            double maxNegativeX = 0;
            double maxNegativeY = 0;

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
            offsetX = convertToPositiveOffset(maxNegativeX);
            offsetY = convertToPositiveOffset(maxNegativeY);
            for (Node node : stars) {
                node.y = node.y + offsetY;
                node.x = node.x + offsetX;
            }

        }


        protected void paintComponent(Graphics grf) {

            //create instance of the Graphics to use its methods
            super.paintComponent(grf);
            Graphics2D graph = (Graphics2D) grf;

            AffineTransform old = graph.getTransform();

            graph.translate(0, getHeight()-1);
            graph.scale(1, -1);

            //Sets the value of a single preference for the rendering algorithms.
            graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // get width and height
            int width = getWidth();
            int height = getHeight();

            // draw graph lines
            graph.draw(new Line2D.Double(marg, marg, marg, height - marg));
            graph.draw(new Line2D.Double(marg, marg, width - marg, marg));



            double reScalePointsToWindow = (double) (height - 4 * marg) / getMax();

            for(Node currentNode : endOfPath) {
                //Draw finalPath
                while (currentNode != null) {
                    int prevIndex = currentNode.index;
                    currentNode = currentNode.nodePrevious;
                    if (currentNode == null) break;
                    graph.setColor(Color.black);
                    graph.draw(new Line2D.Double(marg + reScalePointsToWindow * stars.get(prevIndex).x * widthScale, marg + reScalePointsToWindow * stars.get(prevIndex).y * heightScale, marg + reScalePointsToWindow * stars.get(currentNode.index).x * widthScale, marg + reScalePointsToWindow * stars.get(currentNode.index).y * widthScale));
                    graph.setColor(Color.red);
                    graph.fill(drawNodeFromIndex(currentNode.index, reScalePointsToWindow, 7));
                }
            }


            //set color for points
            graph.setPaint(Color.RED);

            // set points to the graph
            for (int i = 0; i < stars.size(); i++) {
                //graph.drawString(String.valueOf(stars.get(i).index),(float)( marg + reScalePointsToWindow * stars.get(i).x * widthScale),(float)(marg + reScalePointsToWindow * stars.get(i).y * heightScale ));
                graph.fill(drawNodeFromIndex(i, reScalePointsToWindow, 4));
                }

            //Draw start and finish
            graph.setPaint(Color.GREEN);
            graph.fill(drawNodeFromIndex(beginningOfPath.index, reScalePointsToWindow, 10));
            graph.setPaint(Color.BLUE);
            graph.fill(drawNodeFromIndex(goalStar.index, reScalePointsToWindow, 10));

            graph.setTransform(old);

            for (int i = 0; i < stars.size(); i++) {
                //graph.drawString(String.valueOf(stars.get(i).index),(float)( marg + reScalePointsToWindow * stars.get(i).x * widthScale),(float)(marg + reScalePointsToWindow * stars.get(i).y * heightScale ));
                mirroredNumbers(graph, String.valueOf(stars.get(i).index), (float)( marg + reScalePointsToWindow * stars.get(i).x * widthScale),height - (float)(marg + reScalePointsToWindow * stars.get(i).y* heightScale));
            }
            //mirroredNumbers(graph, "Hello World", marg, height-10);
        }
        private void mirroredNumbers(Graphics2D g, String s, float x, float y)
        {
            //AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            g.setStroke(new BasicStroke(2));
            g.drawString(s,x,y);
            //image = op.filter(image, null);
        }
        private Ellipse2D drawNodeFromIndex(int index, double scale, int starSize){

            double x1 = marg + scale * stars.get(index).x * widthScale;
            double y1 = marg + scale * stars.get(index).y * heightScale;

            return new Ellipse2D.Double(x1 - starSize/2, y1 - starSize/2, starSize, starSize);

        }

        //create getMax() method to find maximum value
        private double getMax() {
            double max = -Double.MAX_VALUE;
            for (Node star : stars) {
                if (star.x > max)
                    max = star.x;
            }
            return max;
        }

        public double convertToPositiveOffset(double num) {
            if (num < 0) {
                return num * -1;
            }
            return 0f;//offset is nothing if everything is positive
        }

    }

}

