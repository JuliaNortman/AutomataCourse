package ua.knu.ynortman;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

@Data
public class MiliAutomata {
    private List<String> states; //A
    private List<String> inputAlphabet; //X
    private Set<String> outputAlphabet; //Y
    private String initial;
    private Set<String> acceptedStates;
    private Table<String, String, String> transition; //f
    private Table<String, String, String> output; //g

    private List<String> rows;
    private List<String> columns;

    public MiliAutomata(List<String> states,
                        List<String> inputAlphabet,
                        Set<String> outputAlphabet,
                        String initial,
                        Set<String> acceptedStates,
                        Table<String, String, String> transition,
                        Table<String, String, String> output) {
        this.states = states;
        this.inputAlphabet = inputAlphabet;
        this.outputAlphabet = outputAlphabet;
        this.initial = initial;
        this.acceptedStates = acceptedStates;
        this.transition = transition;
        this.output = output;
        this.rows = new ArrayList<>(output.rowKeySet());
        this.columns = new ArrayList<>(output.columnKeySet());
    }

    public MiliAutomata minimize() {
        List<EquivalenceClass> iClass = Collections.singletonList(new EquivalenceClass("K"));
        iClass.get(0).getStates().addAll(states);

        List<EquivalenceClass> iPlusOneClass = split(iClass, output);
        Table<String, String, EquivalenceClass> transTable = nextTransTable(transition, iPlusOneClass);

        System.out.println(iClass);
        System.out.println(iPlusOneClass);
        System.out.println(transTable);
        int i = 0;
        while (!iPlusOneClass.equals(iClass)) {
            System.out.println("==============================");
            iClass = iPlusOneClass;
            iPlusOneClass = split(iClass, transTable);
            System.out.println(iClass);
            System.out.println(iPlusOneClass);
            transTable = nextTransTable(transition, iPlusOneClass);
            System.out.println(transTable);
            i++;
        }


        return buildAutomata(iClass);
    }

    private<T> List<EquivalenceClass> split(List<EquivalenceClass> classes,
                                            Table<String, String, T> table) {
        List<EquivalenceClass> result = new LinkedList<>();
        for(EquivalenceClass cl : classes) {
            List<EquivalenceClass> subclasses = new LinkedList<>();
            Set<Integer> usedStates = new HashSet<>();
            for (int i = 0; i < cl.getStates().size(); ++i) {
                if (cl.getStates().size() == 1) continue;
                EquivalenceClass equivalenceClass = new EquivalenceClass(cl.getName() + (i + 1));
                for (int j = i; j < cl.getStates().size(); ++j) {
                    if (!usedStates.contains(j) && compareColumns(cl.getStates().get(i),
                            cl.getStates().get(j), table)) {
                        equivalenceClass.addState(cl.getStates().get(j));
                        usedStates.add(j);
                    }
                }
                if (!equivalenceClass.getStates().isEmpty()
                        && !CollectionUtils.isEqualCollection(equivalenceClass.getStates(), cl.getStates())) {
                    subclasses.add(equivalenceClass);
                }
            }
            if(subclasses.isEmpty()) {
                result.add(cl);
            } else {
                result.addAll(subclasses);
            }
        }
        return result;
    }

    private <T> boolean compareColumns(String first, String second, Table<String, String, T> table) {
        Map<String, T> firstColumn = table.column(first);
        Map<String, T> secondColumn = table.column(second);
        for(String r : rows) {
            if(!firstColumn.get(r).equals(secondColumn.get(r))) {
                return false;
            }
        }
        return true;
    }

    private Table<String, String, EquivalenceClass> nextTransTable(Table<String, String, String> table,
                                                         List<EquivalenceClass> classes) {
        Table<String, String, EquivalenceClass> result = ArrayTable.create(
                inputAlphabet, states);
        for (String c : columns) {
            for (String r : rows) {
                String state = transition.get(r, c);
                result.put(r, c, getClassByState(classes,state));
            }
        }
        return result;
    }

    private EquivalenceClass getClassByState(List<EquivalenceClass> classes, String state) {
        for (EquivalenceClass cl : classes) {
            if(cl.getStates().contains(state)) {
                return cl;
            }
        }
        return null;
    }

    private MiliAutomata buildAutomata(List<EquivalenceClass> classes) {
        List<String> newStates = new LinkedList<>();
        Set<String> newAccepted = new HashSet<>();
        String newInitial = "";
        for(EquivalenceClass cl : classes) {
            newStates.add(cl.getName());
            if(cl.getStates().contains(initial)) {
                newInitial = cl.getName();
            }
            if(!Sets.intersection(new HashSet<>(cl.getStates()), acceptedStates).isEmpty()) {
                newAccepted.add(cl.getName());
            }
        }

        Table<String, String, String> f = ArrayTable.create(inputAlphabet, newStates);

        for (String x : inputAlphabet) {
            for(String column : newStates) {
                String state = transition.get(x,
                        getClassByName(column, classes).getStates().get(0));
                String classNameForState = getClassNameByState(state, classes);
                f.put(x, column, classNameForState);
            }
        }

        Table<String, String, String> g = ArrayTable.create(inputAlphabet, newStates);
        for (String x : inputAlphabet) {
            for(String column : newStates) {
                String outputLetter = output.get(x,
                        getClassByName(column, classes).getStates().get(0));
                g.put(x, column, outputLetter);
            }
        }
        return new MiliAutomata(newStates, inputAlphabet, outputAlphabet, newInitial, newAccepted, f, g);
    }

    private String getClassNameByState(String state, List<EquivalenceClass> classes) {
        for(EquivalenceClass cl : classes) {
            if (cl.getStates().contains(state)) {
                return cl.getName();
            }
        }
        return null;
    }

    private EquivalenceClass getClassByName(String name, List<EquivalenceClass> classes) {
        for (EquivalenceClass cl : classes) {
            if(name.equals(cl.getName())) {
                return cl;
            }
        }
        return null;
    }
}
