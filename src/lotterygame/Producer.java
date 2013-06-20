package lotterygame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {

    private BlockingQueue<Integer> blockingQueue;
    private LotteryGame game;

    public Producer(LotteryGame game, BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.game = game;
    }

    @Override
    public void run() {
        while (game.isGameRunning()) {
            blockingQueue.offer(ThreadLocalRandom.current().nextInt(1, 10_000));
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(200, 700));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
