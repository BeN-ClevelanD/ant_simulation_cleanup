package ant_garbage_collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class antActionTests {

    public Board testbed;
    public Ant testAnt;

    @BeforeAll
    public void setUp(){
        testbed = new Board(10, 10);
        testAnt = new Ant(5, 5, false);
        testbed.generate_garbage(50);
    }



    //COMMENTS: NB: IMPORTANT
    //PLEASE NOTE: whilst an ants position will be set as x=something, and y=something, the board is indexed as "board[y][x]"
    //This is of course because the columns (fist layer of array) are the y values, and the rows (second value of array) is the x value
    //Therefore you may see 
    //     
        // testAnt.set_x(9);
        // testAnt.set_y(7);
        // assertTrue(testbed.board[7][9] == 1);"


        //TO RUN THE TESTS JUST USE "./gradelw test" or "gradlew test"





    @Test
    void antNoGarbageBoardHasGarbage(){
        //Move ant to 4,4 and make sure the ant has no garbage, and that the board has 2 garbage pieces at 4,4
        testAnt.set_x(4);
        testAnt.set_y(4);
        testAnt.set_garbage_status(false);
        testbed.set_garbage_level(4, 4, 2);

        //Check garbage level at 4,4 and ant garbage status

        assertTrue(testAnt.get_garbage_status() == false);
        assertTrue(testbed.board[4][4].get() == 2);

        //Get ant to pick up piece, then check ant has garbage, and the level of garbage at the block 4,4 has decreased by 1
        testAnt.performAction(testbed);
        assertTrue(testAnt.get_garbage_status() == true);
        assertTrue(testbed.board[4][4].get() == 1);
      



    }

    @Test
    void antHasGarbageBoardHasGarbage(){

        //Ant now has garbage and so does board
        testAnt.set_garbage_status(true);
        testAnt.set_x(9);
        testAnt.set_y(7);

        testbed.set_garbage_level(9, 7, 1);
     
      
        assertTrue(testAnt.get_garbage_status() == true);
        assertTrue(testbed.board[7][9].get() == 1);

        //Get ant to drop garbage, and check that the ant no longer has garbage, and that the level of garbage at the block 9,7 has increased by 1
        testAnt.performAction(testbed);

        assertTrue(testAnt.get_garbage_status() == false);
        assertTrue(testbed.board[7][9].get() == 2);

        
    }


    @Test
    void antNoGarbageBoardNoGarbage(){
        //Ant now has no garbage and neither does the board, hence nothing will really happen
        testAnt.set_garbage_status(false);
        testAnt.set_x(6);
        testAnt.set_y(3);
        testbed.set_garbage_level(6, 3, 0);

        assertTrue(testAnt.get_garbage_status() == false);
        assertTrue(testbed.board[3][6].get() == 0);

        testAnt.performAction(testbed);

        assertTrue(testAnt.get_garbage_status() == false);
        assertTrue(testbed.board[3][6].get() == 0);


        
    }


    @Test
    void antHasGarbageBoardNoGarbage(){
        //The ant has garbage and the board has none, meaning the ant should drop it and no longer be carrying, and the board value should go from 0 to 1
        testAnt.set_x(2);
        testAnt.set_y(8);
        testAnt.set_garbage_status(true);
        testbed.set_garbage_level(2, 8, 0);

        assertTrue(testAnt.get_garbage_status() == true);
        assertTrue(testbed.board[8][2].get() == 0);

        testAnt.performAction(testbed);
        assertTrue(testAnt.get_garbage_status() == false);
        assertTrue(testbed.board[8][2].get() == 1);
        
    }


}