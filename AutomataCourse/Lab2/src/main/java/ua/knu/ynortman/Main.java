package ua.knu.ynortman;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Example 1");
        Table<String, String, String> g = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );
        g.put("x", "1", "u");
        g.put("y", "1", "v");
        g.put("x", "2", "u");
        g.put("y", "2", "u");
        g.put("x", "3", "u");
        g.put("y", "3", "v");
        g.put("x", "4", "u");
        g.put("y", "4", "v");


        Table<String, String, String> f = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );
        f.put("x", "1", "2");
        f.put("y", "1", "4");
        f.put("x", "2", "3");
        f.put("y", "2", "3");
        f.put("x", "3", "2");
        f.put("y", "3", "4");
        f.put("x", "4", "4");
        f.put("y", "4", "4");

        List<String> A = new LinkedList<>(Arrays.asList("1", "2", "3", "4"));
        List<String> X = new LinkedList<>(Arrays.asList("x", "y"));
        Set<String> Y = new HashSet<>(Arrays.asList("u", "v"));
        String initial = "1";
        Set<String> F = new HashSet<>(Collections.singletonList("4"));

        MiliAutomata miliAutomata = new MiliAutomata(A, X, Y, initial, F, f, g);
        System.out.println(miliAutomata.minimize());

        System.out.println("\n\n\nExample 2");


        Table<String, String, String> g1 = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );
        g1.put("x", "1", "u");
        g1.put("y", "1", "u");
        g1.put("x", "2", "v");
        g1.put("y", "2", "u");
        g1.put("x", "3", "u");
        g1.put("y", "3", "v");
        g1.put("x", "4", "u");
        g1.put("y", "4", "v");


        Table<String, String, String> f1 = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );
        f1.put("x", "1", "2");
        f1.put("y", "1", "4");
        f1.put("x", "2", "3");
        f1.put("y", "2", "3");
        f1.put("x", "3", "2");
        f1.put("y", "3", "4");
        f1.put("x", "4", "2");
        f1.put("y", "4", "4");

        List<String> A1 = new LinkedList<>(Arrays.asList("1", "2", "3", "4"));
        List<String> X1 = new LinkedList<>(Arrays.asList("x", "y"));
        Set<String> Y1 = new HashSet<>(Arrays.asList("u", "v"));
        String initial1 = "1";
        Set<String> F1 = new HashSet<>(Collections.singletonList("4"));

        MiliAutomata miliAutomata1 = new MiliAutomata(A1, X1, Y1, initial1, F1, f1, g1);
        System.out.println(miliAutomata1.minimize());


        System.out.println("\n\n\nExample 3");

        Table<String, String, String> g2 = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );
        g2.put("x", "1", "u");
        g2.put("y", "1", "u");
        g2.put("x", "2", "u");
        g2.put("y", "2", "u");
        g2.put("x", "3", "u");
        g2.put("y", "3", "v");
        g2.put("x", "4", "u");
        g2.put("y", "4", "v");


        Table<String, String, String> f2 = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );
        f2.put("x", "1", "2");
        f2.put("y", "1", "3");
        f2.put("x", "2", "1");
        f2.put("y", "2", "3");
        f2.put("x", "3", "2");
        f2.put("y", "3", "4");
        f2.put("x", "4", "4");
        f2.put("y", "4", "4");

        List<String> A2 = new LinkedList<>(Arrays.asList("1", "2", "3", "4"));
        List<String> X2 = new LinkedList<>(Arrays.asList("x", "y"));
        Set<String> Y2 = new HashSet<>(Arrays.asList("u", "v"));
        String initial2 = "1";
        Set<String> F2 = new HashSet<>(Collections.singletonList("4"));

        MiliAutomata miliAutomata2 = new MiliAutomata(A2, X2, Y2, initial2, F2, f2, g2);
        System.out.println(miliAutomata2.minimize());
    }
}
