package piping;

import java.io.IOException;
import java.io.PipedReader;

/**
 * This Thread will consume messages from {@link ProducerThread} via an {@link java.io.PipedReader}
 * It will consume as long messages as the producer closes his piped output stream.
 * <p/>
 * For better testing the synchronized behaviour, this Thread sleeps randomly milliseconds.
 *
 * @author Ali
 */
public class ConsumerThread extends Thread {
    private PipedReader pipedReader;

    public ConsumerThread(PipedReader pipedReader) {
        this.pipedReader = pipedReader;
    }

    @Override
    public void run() {
        // If PipedWriter has been closed, it will return an int value of -1.
        int closedFlagOfWriter = 0;
        try (PipedReader pipedReader = this.pipedReader) {
            while (closedFlagOfWriter != -1) {
                char[] message = new char[500];
                closedFlagOfWriter = pipedReader.read(message);
                if (closedFlagOfWriter == -1) {
                    System.out.println("Producer has closed his piped output stream");
                } else {
                    System.out.println(currentThread().getName() + " consumed: " + String.valueOf(message) + "\n");
                }
                sleep((long) (500 * Math.random()));
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

