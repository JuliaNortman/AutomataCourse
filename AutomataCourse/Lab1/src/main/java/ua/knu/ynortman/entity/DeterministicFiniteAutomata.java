package ua.knu.ynortman.entity;

import com.google.common.collect.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class DeterministicFiniteAutomata<T> extends FiniteAutomata<T>{

    private Table<T, String, String> transition;

    public DeterministicFiniteAutomata(Set<String> states, Set<T> alphabet,
                                       String initial, Set<String> acceptedStates,
                                       Table<T, String, String> transition) {
        super(states, alphabet, initial, acceptedStates);
        this.transition = transition;
    }
}
