import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MichaelScottQueueTest {
    private MichaelScottQueue<Integer> michaelScottQueue;
    volatile int removedElement = -1;

    @BeforeEach
    void initializeQueue() {
        michaelScottQueue = new MichaelScottQueue<>();
    }

    private Thread addTwoElementFromThread(int element) {
        Thread thread = new Thread(() -> {
            michaelScottQueue.enqueue(element);
            michaelScottQueue.enqueue(element + 1);
        });
        thread.start();
        return thread;
    }

    private Thread addElementFromThread(int element) {
        Thread thread = new Thread(() -> {
            michaelScottQueue.enqueue(element);
        });
        thread.start();
        return thread;
    }

    private Thread removeElementFromThread() {
        Thread thread = new Thread(() -> removedElement = michaelScottQueue.dequeue());
        thread.start();
        return thread;
    }

    @Test
    void shouldAddElementsFromDifferentThreads() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        threads.add(addTwoElementFromThread(2));
        threads.add(addTwoElementFromThread(4));
        threads.add(addTwoElementFromThread(6));
        for (Thread thread : threads) {
            thread.join();
        }
        List<Integer> resultsAfterRemove = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            resultsAfterRemove.add(michaelScottQueue.dequeue());
        }
        for (int i = 2; i < 8; i++) {
            assertTrue(resultsAfterRemove.contains(i));
        }
    }

    @Test
    void shouldRemoveElementsFromDifferentThreads() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        threads.add(addElementFromThread(2));
        threads.add(addElementFromThread(4));
        threads.add(addElementFromThread(6));
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
        threads.add(removeElementFromThread());
        threads.add(removeElementFromThread());
        for (Thread thread : threads) {
            thread.join();
        }
        Integer dequeueResult = michaelScottQueue.dequeue();
        assertNull(michaelScottQueue.dequeue());
        assertTrue(dequeueResult == 2 || dequeueResult == 4 || dequeueResult == 6);
    }

    @Test
    void shouldAddAndRemoveElementsAtTheSameTime() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        threads.add(addElementFromThread(2));
        for (Thread thread : threads) {
            thread.join();
        }
        threads.clear();
        threads.add(addElementFromThread(4));
        threads.add(removeElementFromThread());
        threads.add(addElementFromThread(6));
        threads.add(removeElementFromThread());
        for (Thread thread : threads) {
            thread.join();
        }
        Integer dequeueResult = michaelScottQueue.dequeue();
        assertNull(michaelScottQueue.dequeue());
        assertTrue(dequeueResult == 2 || dequeueResult == 4 || dequeueResult == 6);
    }
}