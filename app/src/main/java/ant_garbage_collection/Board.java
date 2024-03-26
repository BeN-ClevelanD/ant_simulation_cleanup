package ant_garbage_collection;

import java.util.concurrent.atomic.AtomicInteger;

public class Board {
    public int length;
    public  int width;
    public AtomicInteger[][] board;

    public Board(int length, int width) {
        this.length = length;
        this.width = width;
        board = new AtomicInteger[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new AtomicInteger(0);
            }
        }
        
    }

    public void print_board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void set_garbage_level(int x, int y, int level) {
        board[y][x].set(level);
    }

    public synchronized  void place_garbage(int x, int y) {
        board[y][x].incrementAndGet();
    }

    public synchronized void remove_garbage(int x, int y) {
        board[y][x].decrementAndGet();
    }

    public boolean check_garbage(int x, int y) {
        if (board[y][x].get() > 0) {
            //System.out.println("Special: " + board[y][x] + " " + x + " " + y);
            return true;
        } else {
            return false;
        }
    }

    public void print_garbage(int x, int y) {
        System.out.println("Garbage at position: " + x + ", " + y);
    }

    public void generate_garbage(int num) {
        for (int i = 0; i < num; i++) {
            int y = random_generator.nextInt(board.length);
            int x = random_generator.nextInt(board[0].length);
            place_garbage(x, y);
        }
    }
}
