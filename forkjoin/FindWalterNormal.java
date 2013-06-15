package forkjoin;

public class FindWalterNormal {
    public static void main(String[] args) {
        FindWalterProblem walterProblem = new FindWalterProblem();
        long start = System.currentTimeMillis();
        if (findWalter(walterProblem.getStrings())) {
            System.out.println("Found Walter in " + String.valueOf(System.currentTimeMillis() - start) + " ms");
        }
    }

    private static boolean findWalter(final String[] stringArray) {
        for (String string : stringArray) {
            if (string.equals("WALTER")) {
                return true;
            }
        }
        return false;
    }
}
