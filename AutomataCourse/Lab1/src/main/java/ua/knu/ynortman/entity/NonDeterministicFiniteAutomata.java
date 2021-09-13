package ua.knu.ynortman.entity;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class NonDeterministicFiniteAutomata <T> extends FiniteAutomata<T>{

    private Table<T, String, List<String>> transition;

    public NonDeterministicFiniteAutomata(Set<String> states, Set<T> alphabet,
                                          String initial,
                                          Set<String> acceptedStates, Table<T, String, List<String>> transition) {
        super(states, alphabet, initial, acceptedStates);
        this.transition = transition;
    }

    public DeterministicFiniteAutomata<T> toDFA() {
        Set<String> dfaStates = new HashSet<>(); //B
        Set<String> dfaAcceptedStates = new HashSet<>(); //F'
        String dfaInitial = this.initial; //b0
        Table<T, String, String> dfaTransition = HashBasedTable.create();
        Set<String> C = Sets.newHashSet(dfaInitial);
        while(!C.isEmpty()) {
            final String state = C.stream().findAny().get(); //C'
            final List<String> substates = Lists.newArrayList(state.split("-"));
            System.out.println("Current state " + state);
            C.remove(state);
            System.out.println("C "+C);
            dfaStates.add(state);
            System.out.println("B = " + dfaStates);
            if(!Sets.intersection(Sets.newHashSet(substates), this.acceptedStates).isEmpty()) {
                dfaAcceptedStates.add(state);
            }
            for(T symbol : this.alphabet) {
                System.out.println("x = " + symbol + ", state = " + state);
                StringBuilder newState = new StringBuilder();
                for(String substate : substates) {
                    List<String> gotoStates = this.transition.get(symbol, substate);
                    if(gotoStates != null) {
                        newState.append(gotoStates.stream()
                                .map(Objects::toString)
                                .collect(Collectors.joining("-"))).append("-"); //C''
                    }
                }
                newState.deleteCharAt(newState.length()-1);
                System.out.println("New state = " + newState);
                Set<String> Ctemp = new HashSet<>();
                for(String substate : substates) {
                    List<String> gotoStates = this.transition.get(symbol, substate);
                    if(gotoStates != null) {
                        Ctemp = Sets.newHashSet(gotoStates);
                    }
                }
                dfaTransition.put(symbol, state, newState.toString());
                if(!dfaStates.containsAll(Ctemp) && !dfaTransition.containsColumn(newState.toString())) {
                    C.add(newState.toString());
                }


            }
            System.out.println(dfaTransition);
            System.out.println("===============================\n");
        }

        return new DeterministicFiniteAutomata<>(dfaStates,
                this.alphabet, dfaInitial, dfaAcceptedStates, dfaTransition);
    }
}