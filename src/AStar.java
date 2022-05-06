import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class AStar {
    public Node startStar;
    public Node goalStar;
    public int maxTravelDist;
    public List<Node> stars;
    public static void main(String[] args) throws InterruptedException {
        AStar runInstance = new AStar();
        runInstance.run(args);//0-CSV, 1-Start index, 2-Finish index

    }

    /**
     * The non-static main method
     */
    public void run(String[] args) throws InterruptedException {
        //Code adapted from https://www.baeldung.com/java-csv-file-array
        stars = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            //Populating stars list
            while (scanner.hasNextLine()) {

                String[] starCoords = scanner.nextLine().split(",");

                stars.add(new Node(Integer.parseInt(starCoords[0]),Integer.parseInt(starCoords[1]), stars.size()));
            }
            //Parsing other args
            startStar = stars.get(Integer.parseInt(args[1]));
            goalStar = stars.get(Integer.parseInt(args[2]));
            maxTravelDist = Integer.parseInt(args[3]);
        }
        catch (Exception e){
            System.err.println("Error occurred parsing the Commandline args");
        }

        //
        //-----------------------------------------A* IMPLEMENTATION--------------------------------------------
        //
        if(args.length == 4) {
            List<Node> OPEN = new ArrayList<>();
            HashSet<Node> CLOSED = new HashSet<>();
            OPEN.add(startStar);
            Plotting plot = new Plotting(stars, new Node[]{startStar, goalStar}, startStar, goalStar);
            Node temp = null;
            while (OPEN.size() > 0) {
                temp = OPEN.get(0);//Will return null if list is empty
                for (Node node : OPEN) {
                    int fcost = node.getFCost();
                    if (fcost < temp.getFCost()) {
                        temp = node;
                    } else if (fcost == temp.getFCost()) {
                        temp = (temp.hCost > node.hCost ? node : temp);
                    }
                }
                OPEN.remove(temp);
                CLOSED.add(temp);

                if (temp.equals(goalStar)) {
                    plot.setup(stars, new Node[]{temp}, startStar, goalStar);
                    break;//Break while, goal is found
                }

                for (Node node : temp.findNeighbours(stars, maxTravelDist)) {
                    if (CLOSED.contains(node)) continue;
                    int movementCostToNode = (int) (temp.gCost + node.getDistanceBetween(temp));
                    if (!OPEN.contains(node) || movementCostToNode < node.gCost) {
                        node.nodePrevious = temp;
                        node.hCost = (int) goalStar.getDistanceBetween(node);
                        node.gCost = movementCostToNode;
                        if (!OPEN.contains(node)) OPEN.add(node);
                    }
                }
                Thread.sleep(50);//----------------------------------------------------REMOVE FOR INSTANTANEOUS CALCULATION-------------------
                plot.setup(stars, CLOSED.toArray(new Node[CLOSED.size()]), startStar, goalStar);
            }
            String message;
            //Shows whether the program has calculated a successful path or not
            if (temp == goalStar) {
                message = "Path Found!";
                System.out.println(message);
                new pop(message);
            } else {
                message = "Path Not Found!";
                new pop(message);
                System.err.println(message);
                System.exit(1);
            }
        }
        else
        {
            System.err.println("Usage of program: CSVFile StartStarIndex GoalStarIndex MaxTravelDistance");
        }
    }
}
