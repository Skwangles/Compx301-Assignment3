import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AStar {
    public Node startStar;
    public Node goalStar;
    public int maxTravelDist;
    public List<Node> stars;
    public static void main(String[] args){
    AStar runInstance = new AStar();
    runInstance.run(args);//0-CSV, 1-Start index, 2-Finish index

    }

    /**
     * The non-static main method
     */
    public void run(String[] args){
        //Code adapted from https://www.baeldung.com/java-csv-file-array
        stars = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            //Populating stars list
            while (scanner.hasNextLine()) {

                String[] starCoords = scanner.nextLine().split(",");

                stars.add(new Node(Integer.parseInt(starCoords[0]),Integer.parseInt(starCoords[1])));
            }
            System.out.println("Parsing Other Args");
            //Parsing other args
            startStar = stars.get(Integer.parseInt(args[1]));
            goalStar = stars.get(Integer.parseInt(args[2]));
            maxTravelDist = Integer.parseInt(args[3]);
        }
        catch (Exception e){
            System.err.println("Error occurred parsing the Commandline args");
        }

        System.out.println(stars.toString());
        new Plotting(stars, Arrays.asList(new Node[]{stars.get(0), stars.get(2), stars.get(4)}));
    }

    /**
     * Gets all neighbours within the global maxTravelDistance
     * @param node The node whose neighbours we are finding
     * @return List of stars to travel to
     */
    public List<Node> findNeighbours(Node node){
        List<Node> neighbours = new ArrayList<>();
        for (Node potentialNeighbour: stars) {
            //Check potentialNeighbour hasn't been visited or something
            if((potentialNeighbour.x != node.x || potentialNeighbour.y != node.y) && getDistanceBetweenNodes(node, potentialNeighbour) > maxTravelDist){
                neighbours.add(potentialNeighbour);
            }
        }
        return neighbours;
    }

    /**
     * Gets the distance between two Nodes using Pythagoras
     * @return distance
     */
    public double getDistanceBetweenNodes(Node node1, Node node2){
        return Math.sqrt((node1.x - node2.x)^2 +(node1.y - node2.y)^2);
    }

}
