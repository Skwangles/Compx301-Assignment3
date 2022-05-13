import java.util.ArrayList;
import java.util.List;
//Alexander Stokes - 1578409, Ralph Huey Olegario - 1522057
public class Node{
    int index;
    double hCost = 0;
    double gCost = 0;
    double x;
    double y;
    Node nodePrevious = null;

    /**
     * Constructs a basic star from the X & Y coords from the CSV file
     * @param x X coordinate on X axis
     * @param y Y coordinate on Y axis
     */
    public Node(double x, double y, int index){
        this.index = index;
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the F cost heuristic from the g & h costs (combined cost of path + distance to goal)
     * @return fcost
     */
    public double getFCost(){
        return gCost + hCost;
    }

    /**
     * Finds how close the other node is to the parent node
     * @param node - node to get the distance to
     * @return value of the distance
     */
    public double getDistanceBetween(Node node){
        return Math.sqrt(Math.pow((this.x - node.x), 2.00) + Math.pow((this.y - node.y),2.00));
    }

    /**
     * Find a list of all the neighbouring nodes within the maxDistance
     * @param stars - List of all stars to loop through
     * @param maxDistance - max distance from this to a neighbour
     * @return - list of neighbours
     */
    public List<Node> findNeighbours(List<Node> stars, double maxDistance){
        List<Node> neighbours = new ArrayList<>();
        for(Node n : stars){
            if(this.getDistanceBetween(n) <= maxDistance){
                neighbours.add(n);
            }
        }
        return neighbours;
    }

    @Override
    public String toString(){
        return "X:"+  String.valueOf(x) + " Y:" + String.valueOf(y) + " G:" + gCost;
    }


    @Override
    public boolean equals(Object objectInstance) {//X & Y must be the only factors, e.g. otherwise the 'temp' node (which has a h value) will not be seen as == to the goal (which has no hvalue) but the same x&y coords
        if (!(objectInstance instanceof Node))return false;
        if(((Node) objectInstance).x == this.x && ((Node) objectInstance).y == this.y) return true;
        return false;
    }

}