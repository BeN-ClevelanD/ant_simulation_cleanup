package ant_garbage_collection;
import org.apache.commons.math3.random.MersenneTwister;

public class random_generator {
    private static MersenneTwister mersenneTwister;
    
 
    private random_generator() { }

  
    public static MersenneTwister getInstance() {
        if (mersenneTwister == null) {
            mersenneTwister = new MersenneTwister();
        }
        return mersenneTwister;
    }
    
  
    public static synchronized int nextInt(int bound) {
        return getInstance().nextInt(bound);
    }
    
   
}
