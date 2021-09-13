package ua.knu.ynortman;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import ua.knu.ynortman.entity.DeterministicFiniteAutomata;
import ua.knu.ynortman.entity.NonDeterministicFiniteAutomata;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<String> A = new HashSet<>(Arrays.asList("1", "2", "3", "4"));
        Set<String> X = new HashSet<>(Arrays.asList("x", "y"));
        String initial = "1";
        Set<String> F = new HashSet<>(Collections.singletonList("4"));
        Table<String, String, List<String>> Q = ArrayTable.create(
                Lists.newArrayList("x", "y"),
                Lists.newArrayList("1", "2",  "3", "4")
        );

        Q.put("x", "1", Lists.newArrayList("1", "2"));
        Q.put("y", "1", Lists.newArrayList("1"));
        Q.put("x", "2", Collections.singletonList("3"));
        Q.put("y", "2", Collections.singletonList("3"));
        Q.put("x", "3", Collections.singletonList("4"));
        Q.put("y", "3", Collections.singletonList("4"));


        NonDeterministicFiniteAutomata<String> nfa = new NonDeterministicFiniteAutomata<>(A, X, initial, F, Q);
        DeterministicFiniteAutomata<String> dfa =  nfa.toDFA();
        System.out.println();
        System.out.println("RESULT");
        System.out.println("************************");
        System.out.println(dfa);
    }
}
