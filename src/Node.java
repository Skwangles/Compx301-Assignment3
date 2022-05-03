import java.util.ArrayList;
import java.util.List;

public class Node{
    int costOfStoredPath = Integer.MAX_VALUE;//Must be greater than any possible path cost
    int x;
    int y;
    private List<Node> storedPath = new ArrayList<>();

    /**
     * Constructs a basic star from the X & Y coords from the CSV file
     * @param x X coordinate on X axis
     * @param y Y coordinate on Y axis
     */
    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the storedPath to whichever path has the least cost. Also informs the caller if what the result of the comparison is
     * @param successor The Node which is travelling to this Node
     * @param weightOfTravel The cost of the travel between the caller and this.
     * @return true if the caller had a lower cost.
     */
    public boolean isLessThanStored(Node successor, int weightOfTravel){
        if(successor.costOfStoredPath + weightOfTravel < this.costOfStoredPath){
            storedPath = successor.storedPath;
            storedPath.add(successor);
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String storedPathString = "";
        for (Node n: storedPath) {
            storedPathString += ">"+n.toString();
        }

        return "X:"+  String.valueOf(x) + " Y:" + String.valueOf(y) + " Cost:" + costOfStoredPath +" Path:" + storedPathString + "\n";
    }

}