package producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer extends Thread {

    private BlockingQueue<Integer> blockingQueue;
    private LotteryGame game;

    public Consumer(BlockingQueue<Integer> blockingQueue, LotteryGame game) {
        this.blockingQueue = blockingQueue;
        this.game = game;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                int i = blockingQueue.take();
                game.getMap().put(this, i);
                Thread.sleep(ThreadLocalRandom.current().nextInt(100, 400));
                System.out.println(this.getName() + " waiting at phaser-barrier with number: " + i + " , State of Phaser: " + game.getPhaser().toString());
                game.getPhaser().arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.getPhaser().arriveAndDeregister();
    }
}
