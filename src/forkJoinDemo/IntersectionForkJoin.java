package forkJoinDemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class IntersectionForkJoin {

    public static void main(String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        final StretchJava[] stretches = readStretches();
        System.out.println("Reading file completed in " + (System.currentTimeMillis() - start) + " ms");
//
//        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
//        final AtomicInteger count = new AtomicInteger();
//        for (int index1 = 0; index1 < stretches.length; index1++) {
//            final int finalIndex1 = index1;
//            pool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    int localCount = 0;
//                    for (int index2 = finalIndex1 + 1; index2 < stretches.length; index2++) {
//                        if (stretches[finalIndex1].intersects(stretches[index2])) {
//                            localCount++;
//                        }
//                    }
//                    count.getAndAdd(localCount);
//                }
//            });
//        }
//        pool.shutdown();
//        pool.awaitTermination(1, TimeUnit.HOURS);
//
//        System.out.println("count = " + count);

        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        final IntersectionsFinder finder = new IntersectionsFinder(stretches, 0, stretches.length);
        System.out.println("Intersections found= " + forkJoinPool.invoke(finder));


        System.out.println("Calculating took " + (System.currentTimeMillis() - start) + " ms\n\n");
//
    }

    private static StretchJava[] readStretches() throws IOException {
        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader("/Users/najum/Documents/dev/uni/Winfo/Piping/src/forkJoinDemo/Strecken_10000.dat"))) {
            List<StretchJava> stretches = new ArrayList<>(10_000);
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] split = line.split(" ");
                double[] doubles = new double[split.length];
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    doubles[i] = Double.valueOf(s);
                }
                stretches.add(StretchJava.valueOf(doubles));
                line = bufferedReader.readLine();
            }
            return stretches.toArray(new StretchJava[stretches.size()]);
        }
    }
}
