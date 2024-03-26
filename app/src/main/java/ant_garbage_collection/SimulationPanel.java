package ant_garbage_collection;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class SimulationPanel extends JPanel {

    private ConcurrentHashMap<String, Boolean> antPositions;

    private static final int CELL_SIZE = 20; // This determines the size of each cell
    private Board board;
    private int antX = -1; // Initial X position of the ant
    private int antY = -1; // Initial Y position of the ant

    public SimulationPanel(Board board, ConcurrentHashMap<String, Boolean> antPositions) {
        this.board = board;
        this.antPositions = antPositions;
        setPreferredSize(new Dimension(board.width * CELL_SIZE, board.length * CELL_SIZE));
    }

    public void setBoard(Board board) {
        this.board = board;
        repaint();
    }

    public void setAntPosition(int x, int y) {
        this.antX = x;
        this.antY = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

       // System.out.println("PaintCOmponent is being calleds");
        // Draw the board
      //  System.out.println(board.length);
       // System.out.println(board.width);
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.width; x++) {
                int cellValue = board.board[y][x].get();
                if (cellValue == 0) {
                    //System.out.println("no garbagge here! : x is " + x + " y is " + y + " cellValue is " + cellValue);
                    g.setColor(Color.WHITE);
                } 
                else if(cellValue == 1){
                    g.setColor(Color.RED);
                }
                else if(cellValue == 2){
                    g.setColor(Color.BLUE);
                }
                else if(cellValue == 3){
                    g.setColor(Color.GREEN);
                }
                else if(cellValue == 4){
                    g.setColor(Color.ORANGE);
                }
                else if(cellValue == 5){
                    g.setColor(Color.PINK);
                }
                else if(cellValue == 6){
                    g.setColor(Color.CYAN);
                }
                else if(cellValue == 7){
                    g.setColor(Color.MAGENTA);
                }
                else if(cellValue == 8){
                    g.setColor(Color.YELLOW);
                }
                else if(cellValue == 9){
                    g.setColor(Color.GRAY);
                }
                else if(cellValue == 7){
                    g.setColor(Color.LIGHT_GRAY);
                }
                //else {
                    // Calculate the shade of blue based on the amount of garbage
                    //System.out.println("placing now:   x is " + x + " y is " + y + " cellValue is " + cellValue);
                   // int shade = Math.min(255, 255 - cellValue * 30); // Make sure it does not go below 0
                   // g.setColor(new Color(0, 0, shade));
                //}
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }


        // Draw the grid lines (after drawing the cells)
        g.setColor(Color.BLACK); // Set the color for the grid lines
        for (int y = 0; y <= board.length; y++) {
            g.drawLine(0, y * CELL_SIZE, board.width * CELL_SIZE, y * CELL_SIZE);
        }
        for (int x = 0; x <= board.width; x++) {
            g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, board.length * CELL_SIZE);
        }

        // Draw the ant as a yellow circle
        // if (antX >= 0 && antY >= 0) { // Only draw if the ant's position has been set
        //     g.setColor(Color.YELLOW);
        //     g.fillOval(antX * CELL_SIZE, antY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        // }

        g.setColor(Color.BLACK);
        for (String key : antPositions.keySet()) {
            String[] coordinates = key.split(":");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            if (antX >= 0 && antY >= 0) { 
            g.fillOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }


    }
}
