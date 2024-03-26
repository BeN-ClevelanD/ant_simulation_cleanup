package ant_garbage_collection;

public class Ant {

    private boolean hasGarbage = false;
    private int x;
    private int y;

    public Ant(int x, int y, boolean hasGarbage) {
        this.x = x;
        this.y = y;
        this.hasGarbage = hasGarbage;
    }
    public Ant(boolean hasGarbage) {
        this.x = -1;
        this.y = -1;
        this.hasGarbage = hasGarbage;
    }

    public void performAction(Board theBoard){
        if(hasGarbage){
            //drop garbage
            System.out.println("Ant at position: " + x + ", " + y + " dropped garbage");
            theBoard.place_garbage(x, y);
            System.out.println("The garbage level at position: " + x + ", " + y + " is now: " + theBoard.board[y][x].get());
            hasGarbage = false;
        }else{
            //pick up garbage
            if(theBoard.check_garbage(x, y)){
                System.out.println("Ant at position: " + x + ", " + y + " picked up garbage");
                System.out.println("The garbage level at position: " + x + ", " + y + " WAS : " + theBoard.board[y][x].get());
                theBoard.remove_garbage(x, y);
                System.out.println("The garbage level at position: " + x + ", " + y + " is now: " + theBoard.board[y][x].get());
                hasGarbage = true;
            }
        }
    }
    
    public boolean get_garbage_status() {
        return hasGarbage;
    }
    
    public void set_garbage_status(boolean status) {
        hasGarbage = status;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] get_position() {
        int[] position = {x, y};
        return position;
    }

    public void collect_garbage() {
        hasGarbage = true;
    }

    public void drop_garbage() {
        hasGarbage = false;
    }   

    public void print_position() {
        System.out.println("Ant is at position: " + x + ", " + y);
    }

    public void print_garbage_status() {
        if (hasGarbage) {
            System.out.println("Ant is carrying garbage");
        } else {
            System.out.println("Ant is not carrying garbage");
        }
    }

    public void print_status() {
        print_position();
        print_garbage_status();
    }

    public int get_y() {
        return y;
    }

    public int get_x() {
        return x;
    }

    public void set_x(int x) {
        this.x = x;
    }   

    public void set_y(int y) {
        this.y = y;
    }


    
}
