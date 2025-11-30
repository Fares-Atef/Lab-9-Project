package result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ResultCollector {
    private static final ResultCollector instance = new ResultCollector();

    private final List<String>[] rowErrors;
    private final List<String>[] colErrors;
    private final List<String>[] boxErrors;

    @SuppressWarnings("unchecked")
    private ResultCollector() {
        rowErrors = new List[9];
        colErrors = new List[9];
        boxErrors = new List[9];
        for (int i = 0; i < 9; i++) {
            rowErrors[i] = Collections.synchronizedList(new ArrayList<>());
            colErrors[i] = Collections.synchronizedList(new ArrayList<>());
            boxErrors[i] = Collections.synchronizedList(new ArrayList<>());
        }
    }

    public static ResultCollector getInstance() {
        return instance;
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            rowErrors[i].clear();
            colErrors[i].clear();
            boxErrors[i].clear();
        }
    }

    public void check(String type, int index, int[] values) {
        if (values == null || values.length != 9) return;

        Map<Integer, Integer> count = new HashMap<>();
        for (int v : values) {
            count.put(v, count.getOrDefault(v, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            int val = e.getKey();
            int occurrences = e.getValue();
            if (occurrences > 1) {
                String entry = String.format("%s %d, #%d, %s",
                        type, index + 1, val, Arrays.toString(values));
                addError(type, index, entry);
            }
        }
    }

    private void addError(String type, int index, String entry) {
        switch (type) {
            case "ROW": rowErrors[index].add(entry); break;
            case "COL": colErrors[index].add(entry); break;
            case "BOX": boxErrors[index].add(entry); break;
            default: break;
        }
    }

    public void printFinalResult() {
        boolean any = false;
        for (int i = 0; i < 9; i++) {
            if (!rowErrors[i].isEmpty() || !colErrors[i].isEmpty() || !boxErrors[i].isEmpty()) {
                any = true;
                break;
            }
        }

        if (!any) {
            System.out.println("VALID RESULT");
            return;
        }

        System.out.println("INVALID RESULT");

        for (int i = 0; i < 9; i++) {
            for (String s : rowErrors[i]) System.out.println(s);
        }

        System.out.println("-----------------------------------------------");

        for (int i = 0; i < 9; i++) {
            for (String s : colErrors[i]) System.out.println(s);
        }

        System.out.println("-----------------------------------------------");

        for (int i = 0; i < 9; i++) {
            for (String s : boxErrors[i]) System.out.println(s);
        }
    }
}
