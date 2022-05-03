import java.util.ArrayList;
import java.util.List;

public class Node{
    int index;
    int hCost = 0;
    int gCost = 0;
    int x;
    int y;
    Node nodePrevious = null;

    /**
     * Constructs a basic star from the X & Y coords from the CSV file
     * @param x X coordinate on X axis
     * @param y Y coordinate on Y axis
     */
    public Node(int x, int y, int index){
        this.index = index;
        this.x = x;
        this.y = y;
    }

    public int getFCost(){
        return gCost + hCost;
    }

    @Override
    public String toString(){
        String distanceTally = "";
        Node curr = this;
        while (curr.nodePrevious != null){
            distanceTally += AStar.getDistanceBetweenNodes(curr, curr.nodePrevious) + ", ";
            curr = curr.nodePrevious;
        }

        return "X:"+  String.valueOf(x) + " Y:" + String.valueOf(y) + " D:" + distanceTally;
    }


    @Override
    public boolean equals(Object objectInstance) {
        if (!(objectInstance instanceof Node))return false;
        if(((Node) objectInstance).x == this.x && ((Node) objectInstance).y == this.y) return true;
        return false;
    }
}