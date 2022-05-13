//Alexander Stokes - 1578409, Ralph Huey Olegario - 1522057

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Plotting {
    JFrame frame;
    Panel panel;
    List<Node> stars;
    Node start;
    Node goalStar;

    public Plotting(List<Node> stars, Node nodesToDraw, Node start, Node goalStar) {
        this.stars = stars;
        this.start = start;
        this.goalStar = goalStar;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocation(0, 0);
        frame.setBackground(Color.black);
        panel = new Panel(this.stars, nodesToDraw, start, goalStar);
        panel.setBackground(Color.black);
        frame.add(panel);

        frame.setVisible(true);
    }

    /**
     * Adds the new node to the 'explored nodes' list
     * @param node - Node that has been explored
     */
    public void addNode(Node node){
        frame.invalidate();
        panel.endOfPath.add(node);
        frame.repaint();
    }

    class Panel extends JPanel {

        double offsetX;
        double offsetY;
        int marg = 20;
        double widthScale = 1;
        double heightScale = 1;
        List<Node> stars;
        ArrayList<Node> endOfPath;
        Node goalStar;
        Node beginningOfPath;
        public Panel(List<Node> starList, Node endOfPath, Node beginningOfPath, Node goalStar) {
            stars = starList;
            this.endOfPath = new ArrayList<>();
            this.endOfPath.add(endOfPath);
            this.goalStar = goalStar;
            this.beginningOfPath = beginningOfPath;


            double maxNegativeX = 0;
            double maxNegativeY = 0;

            //Find largest negative value to make the new left/bottom most value
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

            //Flip the graph points
            AffineTransform old = graph.getTransform();
            graph.translate(0, getHeight()-1);
            graph.scale(1, -1);

            //Sets the value of a single preference for the rendering algorithms.
            graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // get width and height
            int width = getWidth();
            int height = getHeight();

            // draw axis lines
            graph.draw(new Line2D.Double(marg, marg, marg, height - marg));
            graph.draw(new Line2D.Double(marg, marg, width - marg, marg));

            double reScalePointsToWindow = (double) (height - 4 * marg) / getMax();//Normalises the points to the size of the window
            for(Node currentNode : endOfPath) {
                graph.setColor(Color.lightGray);
                graph.setStroke(new BasicStroke(1));

                if(currentNode.equals(goalStar))//differentiate final line from the others
                {
                    graph.setColor(Color.orange);
                    graph.setStroke(new BasicStroke(4));
                }

                //Trace back path by looping through the parent of each node
                while (currentNode.nodePrevious != null) {
                    int prevIndex = currentNode.index;
                    currentNode = currentNode.nodePrevious;

                    graph.draw(new Line2D.Double(marg + reScalePointsToWindow * stars.get(prevIndex).x * widthScale, marg + reScalePointsToWindow * stars.get(prevIndex).y * heightScale, marg + reScalePointsToWindow * stars.get(currentNode.index).x * widthScale, marg + reScalePointsToWindow * stars.get(currentNode.index).y * widthScale));

                    graph.fill(drawNodeFromIndex(currentNode.index, reScalePointsToWindow, 7));
                }
            }

            //set color for points
            graph.setPaint(Color.RED);

            // set points to the graph
            for (int i = 0; i < stars.size(); i++) {
                graph.fill(drawNodeFromIndex(i, reScalePointsToWindow, 4));
                }

            //Draw start and finish stars
            graph.setPaint(Color.GREEN);
            graph.fill(drawNodeFromIndex(beginningOfPath.index, reScalePointsToWindow, 10));
            graph.setPaint(Color.BLUE);
            graph.fill(drawNodeFromIndex(goalStar.index, reScalePointsToWindow, 10));

            graph.setTransform(old);//Unflips the drawing of the graph

            //----Draw indexs of each star----
//            graph.setColor(Color.red);
//            for (int i = 0; i < stars.size(); i++) {
//                 mirroredNumbers(graph, String.valueOf(stars.get(i).index), (float)( marg + reScalePointsToWindow * stars.get(i).x * widthScale),height - (float)(marg + reScalePointsToWindow * stars.get(i).y* heightScale));
//            }

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

        /**
         * Gets the maximum x value for the reScaleToWindow calcuations
         * @return x of the largest x star
         */
        private double getMax() {
            double max = -Double.MAX_VALUE;
            for (Node star : stars) {
                if (star.x > max)
                    max = star.x;
            }
            return max;
        }

        /**
         * Converts the negative most value offset into a positive, so all the points are on the screen
         * @param num - minimum x/y value
         * @return 0 if not <0, the positive value otherwise
         */
        public double convertToPositiveOffset(double num) {
            if (num < 0) {
                return num * -1;
            }
            return 0f;//offset is nothing if everything is positive
        }

    }

}

