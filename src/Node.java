import java.util.ArrayList;
import java.util.List;

public class Node{
    int hCost = 0;
    int gCost = 0;
    int x;
    int y;
    Node nodePrevious = null;
//    int costOfPath = Integer.MAX_VALUE;
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

    public int getFCost(){
        return gCost + hCost;
    }

    @Override
    public String toString(){
        String storedPathString = "";
        for (Node n: storedPath) {
            storedPathString += ">"+n.toString();
        }

        return "X:"+  String.valueOf(x) + " Y:" + String.valueOf(y);
    }


    @Override
    public boolean equals(Object objectInstance) {
        if (!(objectInstance instanceof Node))return false;
        if(((Node) objectInstance).x == this.x && ((Node) objectInstance).y == this.y) return true;
        return false;
    }
}