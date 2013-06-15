package producerConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LotteryGame game = new LotteryGame();
        System.out.println("Starting game with " + game.getNumberOfThreads().get() + " Threads");
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(game.getNumberOfThreads().get());

        Producer producer = new Producer(game, blockingQueue);
        producer.start();

        for (int i = 0; i < game.getNumberOfThreads().get(); i++) {
            new Consumer(blockingQueue, game).start();
        }
    }
}
