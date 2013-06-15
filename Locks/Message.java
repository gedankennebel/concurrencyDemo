package Locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Message {
    private int contents;
    private boolean available = false;

    Lock lock = new ReentrantLock();
    Condition emptyCondition = lock.newCondition();
    Condition fullCondition = lock.newCondition();

    public int get() {
        lock.lock();
        while (available == false) {
            try {
                emptyCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false;
        fullCondition.signal();
        lock.unlock();
        return contents;
    }

    public void put(int value) {
        lock.lock();
        while (available == true) {
            try {
                fullCondition.await();
            } catch (InterruptedException e) {
            }
        }
        contents = value;
        available = true;
        emptyCondition.signal();
        lock.unlock();
    }
}