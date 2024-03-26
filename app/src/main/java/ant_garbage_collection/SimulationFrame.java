package ant_garbage_collection;

import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;

public class SimulationFrame extends JFrame {

    private SimulationPanel panel;

    public SimulationFrame(Board board, ConcurrentHashMap<String, Boolean> antPositions){
        setTitle("Ant Garbage Collection Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new SimulationPanel(board, antPositions);
        getContentPane().add(panel); // Add the panel to the frame's content pane
        pack(); // Size the frame to fit the content
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void repaintBoard(Board board) {
        panel.setBoard(board);
        panel.repaint(); // Request the panel to repaint itself
    }
    

    public void updateAntPosition(int x, int y) {
        panel.setAntPosition(x, y);
        panel.repaint(); // Request the panel to repaint itself with the new ant position
    }
}
