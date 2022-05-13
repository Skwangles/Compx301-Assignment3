import java.io.File;
import java.util.*;
//Alexander Stokes - 1578409, Ralph Huey Olegario - 1522057

public class AStar {
    public Node startStar;
    public Node goalStar;
    public double maxTravelDist;
    public List<Node> stars;

    boolean visualiseAstar = true;
    public static void main(String[] args) {
        AStar runInstance = new AStar();
        runInstance.run(args);//0-CSV, 1-Start index, 2-Finish index

    }

    /**
     * The non-static main method
     */
    public void run(String[] args) {
        //Code adapted from https://www.baeldung.com/java-csv-file-array
        stars = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            //Populating stars list
            while (scanner.hasNextLine()) {

                String[] starCoords = scanner.nextLine().split(",");

                stars.add(new Node(Double.parseDouble(starCoords[0]),Double.parseDouble(starCoords[1]), stars.size()));
            }
            //Parsing other args
            startStar = stars.get(Integer.parseInt(args[1]));
            goalStar = stars.get(Integer.parseInt(args[2]));
            maxTravelDist = Double.parseDouble(args[3]);
            if(args.length == 5){
                visualiseAstar = false;
            }
        }
        catch (Exception e){
            System.err.println("Error occurred parsing the Commandline args");
        }

        //
        //-----------------------------------------A* IMPLEMENTATION--------------------------------------------
        //
        if(args.length != 4 && args.length != 5) {
            System.err.println("Usage of program: java AStar [CSVFile] [StartStarIndex] [GoalStarIndex] [MaxTravelDistance] [optional - anything - remove sleep & other paths]");
            System.exit(1);
        }

        PriorityQueue<Node> OPEN = new PriorityQueue<>(Comparator.comparingDouble(Node::getFCost));
        HashSet<Node> CLOSED = new HashSet<>();
        OPEN.add(startStar);
        Plotting plot = new Plotting(stars, startStar, startStar, goalStar);
        Node temp = null;
        while (OPEN.size() > 0) {
            temp = OPEN.poll();

            CLOSED.add(temp);

            if (temp.equals(goalStar)) {
                plot.addNode(temp);//Ensure the final goal is printed
                break;//Break while, goal is found
            }

            for (Node node : temp.findNeighbours(stars, maxTravelDist)) {
                if (CLOSED.contains(node)) continue;
                double movementCostToNode = (temp.gCost + node.getDistanceBetween(temp));
                if (!OPEN.contains(node) || movementCostToNode < node.gCost) {
                    node.nodePrevious = temp;
                    node.hCost = goalStar.getDistanceBetween(node);
                    node.gCost = movementCostToNode;
                    if (!OPEN.contains(node)) OPEN.add(node);
                    }
                }

            if(visualiseAstar) {//Visualised the search as it searching, printing all the branches that have been explored
                try {
                    plot.addNode(temp);//Add path to plotting
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println("Thread sleep caused an issue");
                    System.exit(1);
                }
            }
            }
            String message;
            //Shows whether the program has calculated a successful path or not

            if (temp == goalStar) {
                message = "Path Found!\nClick OK to close the program";
                System.out.println(message);

                new pop(message);
                System.exit(0);
            } else {
                message = "Path Not Found!\nClick OK to close the program";
                new pop(message);
                System.err.println(message);
                System.exit(0);
            }

    }
}
