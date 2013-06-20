package lotterygame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Phaser;

public class LotteryGame {

    private Map<Thread, Integer> map = new ConcurrentHashMap<>();
    private volatile boolean gameRunning = true;
    private Phaser phaser;

    public LotteryGame(final int numberOfParties) {
        phaser = new Phaser(numberOfParties) {
            @Override
            protected boolean onAdvance(int phase, int parties) {
                if (parties > 1) {
                    System.out.println("***** Reached onAdvance method with " + map.size() + " Threads , State of Phaser: " + this.toString() + " *****");
                    return kickOutLoser();
                } else {
                    return endGame(phase);
                }
            }
        };
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    private boolean endGame(int phase) {
        System.out.println("\nFinish after " + (phase + 1) + " rounds! " + getWinner().getName() + " won the game!");
        getWinner().interrupt();
        setGameRunning(false);
        return true;
    }

    private boolean kickOutLoser() {
        Thread looserThread = getLooser();
        if (looserThread != null) {
            System.out.println("Decision: " + looserThread.getName() + " is out!\n");
            map.remove(looserThread);
            looserThread.interrupt();
        }
        return false;
    }

    private Thread getLooser() {
        final int lowestNumber = getLowestNumber();
        List<Thread> looserThreadArray = new ArrayList<>();
        for (Thread thread : map.keySet()) {
            if (map.get(thread) == lowestNumber) {
                looserThreadArray.add(thread);
            }
        }
        if (looserThreadArray.size() == 1) {
            return looserThreadArray.get(0);
        } else {
            System.out.println("Draw match between 2 or more Threads! No kickout this round!");
            return null;
        }
    }

    private int getLowestNumber() {
        int lowest = Integer.MAX_VALUE;
        for (Thread thread : map.keySet()) {
            if (map.get(thread) <= lowest) {
                lowest = map.get(thread);
            }
        }
        return lowest;
    }

    private Thread getWinner() {
        Thread winnerThread = null;
        for (Thread thread : map.keySet()) {
            winnerThread = thread;
        }
        return winnerThread;
    }

    public Map<Thread, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Thread, Integer> map) {
        this.map = map;
    }

    public Phaser getPhaser() {
        return phaser;
    }

    public void setPhaser(Phaser phaser) {
        this.phaser = phaser;
    }
}
