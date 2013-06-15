package piping;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * This Example illustrates the Java piping concept through a simple producer/consumer-paradigm.
 * Both, producer and consumer, are realised as a {@link Thread} and connected with a pipe.
 * Every character typed data (here messages) that the producer writes into his output stream,
 * will be received from the connected input stream of the other Thread.
 * <p/>
 * The read()- and write()-methods of the pipes take all the work for a synchronized communication.
 * <p/>
 * Alternatively the classes {@link java.io.PipedInputStream} and {@link java.io.PipedOutputStream}
 * could be used if we have to pass bytes instead of characters.
 *
 * @author Ali
 */
public class ExecutePipes {

    public static void main(String[] args) throws IOException, InterruptedException {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        ProducerThread producerThread = new ProducerThread(pipedWriter);
        ConsumerThread consumerThread = new ConsumerThread(pipedReader);

        producerThread.start();
        consumerThread.start();
    }
}
