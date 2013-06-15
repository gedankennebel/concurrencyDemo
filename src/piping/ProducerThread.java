package piping;

import java.io.IOException;
import java.io.PipedWriter;

/**
 * This Thread will produce messages and write it into his piped output stream.
 * After 50 messages the piped output stream will be closed.
 * <p/>
 * For better testing the synchronized behaviour, this Thread sleeps randomly milliseconds.
 *
 * @author Ali
 */
public class ProducerThread extends Thread {

    private PipedWriter pipedWriter;

    public ProducerThread(PipedWriter pipedWriter) {
        this.pipedWriter = pipedWriter;
    }

    @Override
    public void run() {
        try (PipedWriter pipedWriter = this.pipedWriter) {
            for (int sequenceNumber = 1; sequenceNumber <= 30; sequenceNumber++) {
                String message = "Message #" + (sequenceNumber) + " ";
                pipedWriter.write(message);
                System.out.println(currentThread().getName() + " produced: " + message);
                sleep((long) (500 * Math.random()));
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
