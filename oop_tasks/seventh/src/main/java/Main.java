import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final CustomCyclicBarrier cyclicBarrier = new CustomCyclicBarrier(3,
            () -> System.out.println("All threads reached barrier, it can be released. "));

    public static void main(String[] args) {
        Event event = new Event(cyclicBarrier, 3000);
        Thread a = new Thread(event, "1-Thread");
        a.start();
        Thread b = new Thread(event, "2-Thread");
        b.start();
        Thread c = new Thread(event, "3-Thread");
        c.start();
    }
}

class Event implements Runnable{
    private static final Logger log = Logger.getLogger(Event.class.getName());
    private final CustomCyclicBarrier customCyclicBarrier;
    private final long time;

    public Event(CustomCyclicBarrier customCyclicBarrier, long time) {
        this.customCyclicBarrier = customCyclicBarrier;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting for all other threads. ");
            synchronized (this) {
                this.wait(time);
            }
            System.out.println("Thread waiting time over");
            customCyclicBarrier.await();
        } catch (InterruptedException e){
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}