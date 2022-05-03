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




        System.out.println("---A* Finished---");
    }
}
