import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomCyclicBarrierTest {
    private CustomCyclicBarrier customCyclicBarrier;
    private boolean reached = false;

    @BeforeEach
    public void setUp(){
        customCyclicBarrier = new CustomCyclicBarrier(3, () -> reached = true);
    }

    private void createThread(){
        Thread thread = new Thread(() -> {
            try {
                customCyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @Test
    public void shouldAllThreadsReachBarrier() throws InterruptedException {
        createThread();
        createThread();
        createThread();
        synchronized (this){
            this.wait(2000);
        }
        assertTrue(reached);
    }

    @Test
    public void shouldRemainOneThreadWaiting() throws InterruptedException {
        createThread();
        createThread();
        synchronized (this){
            this.wait(2000);
        }
       assertEquals(1, customCyclicBarrier.getWaiting());
    }

    @Test
    public void shouldRemainThreeThreadsAfterReleasingBarrier() throws InterruptedException {
        createThread();
        createThread();
        createThread();
        synchronized (this){
            this.wait(3000);
        }
        assertTrue(reached);
        assertEquals(3, customCyclicBarrier.getWaiting());
    }

    @Test
    public void shouldRemainThreeThreadsThatNotReachedBarrier() throws InterruptedException {
        createThread();
        createThread();
        createThread();
        synchronized (this){
            this.wait(2000);
        }
        assertEquals(3, customCyclicBarrier.getAmountOfThreads());
    }
}