

public class ThreadManager {

    private final int INTERVAL = 5000;

    public void printTreadsInfo(ThreadGroup threadGroup){
        new Thread(() -> printInfoWithIntervals(threadGroup)).start();
    }

    private void printInfoWithIntervals(ThreadGroup threadGroup) {
        while(threadGroup.activeCount() > 0){
            printInfoAboutGroup(threadGroup, 0);
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void printInfoAboutGroup(ThreadGroup threadGroup, int levelOfThread) {
        printWithIndent(threadGroup.getName(), levelOfThread);

        Thread [] threadsInCurrentGroup = new Thread[(threadGroup.activeCount())];
        int threadsCount = threadGroup.enumerate(threadsInCurrentGroup, false);
        if(threadsCount > 0){
            printWithIndent("Threads at " + threadGroup.getName() + ":", levelOfThread);
            for(int i = 0; i < threadsCount; i++ ){
                printWithIndent(threadsInCurrentGroup[i].getName(), levelOfThread + 1);
            }
        }
        ThreadGroup [] threadGroups = new ThreadGroup[threadGroup.activeGroupCount()];
        int groupsCount = threadGroup.enumerate(threadGroups, false);
        if(groupsCount > 0){
            printWithIndent("Thread groups in " + threadGroup.getName() + ":", levelOfThread);
            for(int i = 0; i < groupsCount; i++){
                printInfoAboutGroup(threadGroups[i], levelOfThread + 1);
            }
        }
    }

    private void printWithIndent(String name, int levelOfThread) {
        for(int i = 0; i < levelOfThread; i++){
            System.out.print("  ");
        }
        System.out.print(name + System.lineSeparator());
    }
}
