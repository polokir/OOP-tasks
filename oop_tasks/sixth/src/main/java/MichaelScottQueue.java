import java.util.concurrent.atomic.AtomicReference;

public class MichaelScottQueue<T> {
    private final AtomicReference<MSNode<T>> head;
    private final AtomicReference<MSNode<T>> tail;

    public MichaelScottQueue(){
        MSNode<T> dummy = new MSNode<>();
        head = new AtomicReference<>(dummy);
        tail = new AtomicReference<>(dummy);
    }

    public void enqueue(T data){
        MSNode<T> node = new MSNode<>(data);
        MSNode<T> currentTail;
        MSNode<T> currentNext;
        while(true){
            currentTail = this.tail.get();
            currentNext = currentTail.next.get();
            if(currentTail == this.tail.get()){
                if(currentNext == null){
                    if(currentTail.next.compareAndSet(null, node)) {
                        break;
                    }
                } else {
                    this.tail.compareAndSet(currentTail, currentNext);
                }
            }
        }
        this.tail.compareAndSet(currentTail, node);
    }

    public T dequeue(){
        MSNode<T> currentHead;
        MSNode<T> currentTail;
        MSNode<T> currentHeadNext;
        T data;

        while (true){
            currentHead = this.head.get();
            currentTail = this.tail.get();
            currentHeadNext = currentHead.next.get();

            if(currentHead == head.get()){
                if(currentHead == currentTail){
                    if(currentHeadNext == null){
                        return null;
                    }
                    this.tail.compareAndSet(currentTail, currentHeadNext);
                } else {
                    data = currentHeadNext.data;
                    if(head.compareAndSet(currentHead, currentHeadNext)){
                        break;
                    }
                }
            }
        }
        return data;
    }
}
