import java.util.concurrent.atomic.AtomicReference;

public class MSNode<T> {
    public T data;
    public AtomicReference<MSNode<T>> next = new AtomicReference<>();

    public MSNode(T data) {
        this.data = data;
    }

    public MSNode() {}
}
