package ant_garbage_collection;

import java.util.concurrent.atomic.AtomicInteger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {

    public static void log2DArrayToFile(AtomicInteger[][] board, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) { // Set true for append mode
            for (AtomicInteger[] row : board) {
                for (AtomicInteger cell : row) {
                    writer.write(cell + " ");
                }
                writer.newLine(); // Move to the next line after writing one row
            }
            writer.newLine(); // Separate each board state with a blank line for clarity
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}