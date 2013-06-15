package forkjoin;

public class FindWalterProblem {
    public static final String WALTER = "WALTER";
    private final String[] strings = new String[100_000_000];

    public FindWalterProblem() {
        for (int i = 0; i < strings.length; i++) {
            if (i == 99_923_128) {
                strings[i] = WALTER;
            } else {
                strings[i] = "NO WALTER :(";
            }
        }
    }

    public String[] getStrings() {
        return strings;
    }
}
