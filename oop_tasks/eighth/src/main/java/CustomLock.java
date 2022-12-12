public interface CustomLock {
    void lock();
    void unlock();
    boolean tryLock();
}
