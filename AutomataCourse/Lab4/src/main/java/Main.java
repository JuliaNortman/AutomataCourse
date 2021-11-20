import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> unabStates = Arrays.asList("0", "1");
        List<String> unabAlphabet = Arrays.asList("a", "b");
        String unabInitial = "0";
        ArrayList<Set<String>> unabAccepted = new ArrayList<>(Arrays.asList(
                new HashSet<>(Collections.singleton("0")),
                new HashSet<>(Collections.singleton("1"))
        ));
        Table<String, String, List<String>> f = ArrayTable.create(
                Lists.newArrayList("0", "1"),
                Lists.newArrayList("a", "b")
        );
        f.put("0", "a", Collections.singletonList("1"));
        f.put("0", "b", Collections.singletonList("0"));
        f.put("1", "a", Collections.singletonList("1"));
        f.put("1", "b", Collections.singletonList("0"));
        UNAB unab = new UNAB(unabStates,
                unabAlphabet,
                unabInitial,
                unabAccepted,
                f);
        System.out.println(unab.toNab());


        List<String> unabStates1 = Arrays.asList("1", "2", "3", "4");
        List<String> unabAlphabet1 = Arrays.asList("x", "y");
        String unabInitial1 = "1";
        ArrayList<Set<String>> unabAccepted1 = new ArrayList<>(Arrays.asList(
                new HashSet<>(Collections.singleton("2")),
                new HashSet<>(Collections.singleton("1")),
                new HashSet<>(Collections.singleton("3")),
                new HashSet<>(Collections.singleton("4"))
        ));
        Table<String, String, List<String>> f1 = ArrayTable.create(
                Lists.newArrayList("1", "2", "3", "4"),
                Lists.newArrayList("x", "y")
        );
        f1.put("1", "x", Collections.singletonList("1"));
        f1.put("1", "y", Lists.newArrayList( "1","2"));
        f1.put("2", "x", Collections.singletonList("3"));
        f1.put("2", "y", Lists.newArrayList( "3","4"));
        f1.put("3", "x", Lists.newArrayList( "4","2"));
        f1.put("3", "y", Collections.singletonList("4"));
        f1.put("4", "x", Collections.singletonList("1"));
        f1.put("4", "y", Collections.singletonList("1"));
        UNAB unab1 = new UNAB(unabStates1,
                unabAlphabet1,
                unabInitial1,
                unabAccepted1,
                f1);
        System.out.println(unab1.toNab());
    }
}
