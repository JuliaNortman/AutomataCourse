import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class UNAB {
    private List<String> states; //A
    private List<String> inputAlphabet; //X
    private String initial;
    private ArrayList<Set<String>> acceptedStates;
    private Table<String, String, List<String>> transition; //f

    public NAB toNab() {
        String a0 = toNabState(initial, 0); //(a0, 0)
        Set<String> nabInitial = new HashSet<>(Collections.singleton(a0)); //A0'  line 1
        List<String> nabStates = new LinkedList<>(); //A'  line 1
        Set<String> nabAccepted = new HashSet<>(); //F'  line 1
        Table<String, String, LinkedList<String>> nabTransition = HashBasedTable.create(); //f'
        Set<String> C = new HashSet<>(Collections.singleton(a0)); //C  line2
        while (!C.isEmpty()) { //line 3
            String state = C.stream().findAny().get(); //line 4 (a,i)
            C.remove(state); //line 4
            nabStates.add(state); //line 5
            String a = getA(state);
            int i = getI(state);
            if(acceptedStates.get(0).contains(a) && i == 0) { //line 6
                nabAccepted.add(state); //line 6
            }
            for(String x : inputAlphabet) { //line 7 for each letter in alphabet
                List<String> states = transition.get(a, x); //line 7
                for(String aTrans : states) { //line 7 for each state in transition; aTrans is a'
                    if(!acceptedStates.get(i).contains(a)) { //line 8
                        String aTransNab = toNabState(aTrans, i); //(a',i)
                        if(!nabStates.contains(aTransNab)) { //line 9
                            C.add(aTransNab); //line 9
                        }
                        nabTransition = add(nabTransition, state, x, aTransNab); //line 10
                    } else { //line 11
                        int m = acceptedStates.size();
                        String aTransNabNext = toNabState(aTrans, (i+1)%m);
                        if(!nabStates.contains(aTransNabNext)) { //line 12
                            C.add(aTransNabNext); //line 12
                        }
                        nabTransition = add(nabTransition, state, x, aTransNabNext); //line 13
                    }
                }
            }
        }
        return new NAB(nabStates, inputAlphabet, nabInitial, nabAccepted, nabTransition);
    }

    private String toNabState(String a, int i) {
        return "(" + a + "," + i + ")";
    }

    private String getA(String state) {
        return state.split(",")[0].split("\\(")[1];
    }

    private int getI(String state) {
        String i = state.split(",")[1].split("\\)")[0];
        return Integer.parseInt(i);
    }

    private Table<String, String, LinkedList<String>> add(Table<String, String, LinkedList<String>> f,
                                                    String state1, String x, String state2) {
        if(f.contains(state1, x)) {
            LinkedList<String> transitions = f.get(state1, x);
            transitions.add(state2);
            f.put(state1, x, transitions);
        } else {
            f.put(state1, x, new LinkedList<>(Arrays.asList(state2)));
        }
        return f;
    }
}
