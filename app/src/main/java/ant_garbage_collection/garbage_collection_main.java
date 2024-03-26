package ant_garbage_collection;

import org.checkerframework.checker.units.qual.t;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.swing.SwingUtilities;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

public class garbage_collection_main {
    private static final ConcurrentHashMap<String, Boolean> occupiedPositions = new ConcurrentHashMap<>();
    private static final Random random_generator = new Random();
    public static double density;
    public static int boardSize;
    public static int numAnts;
    private static CyclicBarrier barrier;

    public static void main(String[] args) {

        
        
        JSONObject config = null;
    // Reading in the JSON file and checking that the values fall between the min and maxes
        try (InputStream is = garbage_collection_main.class.getClassLoader().getResourceAsStream("config.json")) {
            if (is == null) {
                throw new FileNotFoundException("config.json file is not found in resources folder");
            }
            // Correct way to convert the InputStream to a String
            String jsoner = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            config = new JSONObject(jsoner);
        } catch (FileNotFoundException e) {
            System.out.println("config.json file is not found in resources folder");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the JSON file.");
            e.printStackTrace();
            System.exit(0);
        } catch (JSONException e) {
            System.out.println("The JSON file contains invalid JSON.");
            e.printStackTrace();
            System.exit(0);
        }
        

        // Extract the board size and density values
        int minBoardSize = config.getJSONObject("boardSize").getInt("min");
        int maxBoardSize = config.getJSONObject("boardSize").getInt("max");
        boardSize = config.getJSONObject("boardSize").getInt("val");
        double minDensity = config.getJSONObject("garbageDensity").getDouble("min");
        double maxDensity = config.getJSONObject("garbageDensity").getDouble("max");
        density = config.getJSONObject("garbageDensity").getDouble("val");
        int minAnts = config.getJSONObject("numberOfAnts").getInt("min");
        int maxAnts = config.getJSONObject("numberOfAnts").getInt("max");
        numAnts = config.getJSONObject("numberOfAnts").getInt("val");

        //initializing the cyclic barrier with the number of ants from the config file
        barrier = new CyclicBarrier(numAnts);

        

        if(boardSize < minBoardSize || boardSize > maxBoardSize){
            System.out.println("Board size is out of bounds");
            System.exit(0);
        }
        if(density < minDensity || density > maxDensity){
            System.out.println("Density is out of bounds");
            System.exit(0);
        }
        if(numAnts < minAnts || numAnts > maxAnts){
            System.out.println("Number of ants is out of bounds");
            System.exit(0);
        }


        int garbageAmount = (int)(boardSize * boardSize * density);
        Board theGameBoard = new Board(boardSize, boardSize);

     

        System.out.println(boardSize);
        theGameBoard.generate_garbage(garbageAmount);
        System.out.println(garbageAmount);




        //runSimulation(theGameBoard);  
      //  SwingUtilities.invokeLater(() -> {
            SimulationFrame simulationFrame = new SimulationFrame(theGameBoard, occupiedPositions);
            //simulationFrame.setVisible(true);

            ExecutorService executorService = Executors.newFixedThreadPool(numAnts);
            for (int i = 0; i < numAnts; i++) {
                executorService.submit(() -> {
                    runAnt(theGameBoard, simulationFrame);
                });
            }

            executorService.shutdown();

              
            
            // Initiate a graceful shutdown
                
        try {
            // Wait for all tasks to finish execution or timeout after a long period
            if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                // Optional: Force shutdown if tasks did not terminate in time
                executorService.shutdownNow();
            }
            // Proceed with logging after all ants have finished
            System.out.println("Logging now");
            Logging.log2DArrayToFile(theGameBoard.board, "log.txt");
        } catch (InterruptedException e) {
            // Handle the case where the current thread was interrupted
            executorService.shutdownNow();
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }


          
      //  });  
        
        
    }



    private static void runAnt(Board theGameBoard, SimulationFrame simulationFrame) {
        // ... [Initialize your Ant here] ...

        

        int newx, newy;
        String newPosKey;
    
        // Initialize Ant with a unique starting position
        do {
            newx = random_generator.nextInt(boardSize);
            newy = random_generator.nextInt(boardSize);
            newPosKey = newx + ":" + newy;
        } while (occupiedPositions.putIfAbsent(newPosKey, true) != null);
      
        Ant theAnt = new Ant(newx, newy, false);

        System.out.println();
        System.out.println(theAnt.get_x() + " " + theAnt.get_y() + " " + theAnt.get_garbage_status() + "\n");
        System.out.println();


        //Ant theAnt = new Ant(false);

       SwingUtilities.invokeLater(() -> {
        simulationFrame.updateAntPosition(theAnt.get_x(), theAnt.get_y());
        simulationFrame.repaintBoard(theGameBoard);
        });

       


















        for (int iter = 0; iter < 10000; iter++) {


            
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }


            

            // Synchronized movement logic
            do {
                newx = random_generator.nextInt(boardSize);
                newy = random_generator.nextInt(boardSize);
                newPosKey = newx + ":" + newy;
           // } while (!occupiedPositions.putIfAbsent(newPosKey, Boolean.TRUE));
            } while (occupiedPositions.putIfAbsent(newPosKey, true) != null);


            // Perform the action with the Ant
            theAnt.performAction(theGameBoard);

            // After action, update GUI on the EDT
            // SwingUtilities.invokeLater(() -> {
            //     simulationFrame.updateAntPosition(theAnt.get_x(), theAnt.get_y());
            //     simulationFrame.repaintBoard(theGameBoard);
            // });

            SwingUtilities.invokeLater(() -> {
                // Clear the game board before updating with new positions
               // simulationFrame.clearBoard();
    
                // Iterate over the occupied positions and update GUI
                System.out.println();
                System.out.println("BEFORE MAPPING");
                for (String pos : occupiedPositions.keySet()) {
                    System.out.println(pos + "is occupied");
                    String[] coordinates = pos.split(":");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    simulationFrame.updateAntPosition(x, y);
                }
                System.out.println("AFTER MAPPING");
                System.out.println();
                // Repaint the game board
                simulationFrame.repaint();
            });

            // Release old position, move to new position
            String oldPosKey = theAnt.get_x() + ":" + theAnt.get_y();
            occupiedPositions.remove(oldPosKey);
            theAnt.move(newx, newy);
            System.out.println("Ant was at " + oldPosKey + " and is now at " + newPosKey);





            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                return;
            }

            // Delay if necessary, but consider using a ScheduledExecutorService instead for real-time simulation
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // When an ant's simulation is done, update the log file (this should probably be done once at the end, not per ant)
        
    }



}
