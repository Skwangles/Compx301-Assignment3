import java.util.ArrayList;
import java.util.List;

public class Node{
    int hCost;//Must be -1 to be slight less than the default var
    int gCost;
    int x;
    int y;
    Node nodePrevious = null;
    int costOfPath = Integer.MAX_VALUE;
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

    public double getFCost(){
        return gCost + hCost;
    }

    @Override
    public String toString(){
        String storedPathString = "";
        for (Node n: storedPath) {
            storedPathString += ">"+n.toString();
        }

        return "X:"+  String.valueOf(x) + " Y:" + String.valueOf(y) +  " Path:" + storedPathString + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node))return false;
        if(((Node) o).x == this.x && ((Node) o).y == this.y) return true;
        return false;
    }
}