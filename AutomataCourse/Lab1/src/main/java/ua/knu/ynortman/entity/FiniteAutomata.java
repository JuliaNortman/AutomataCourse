package ua.knu.ynortman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FiniteAutomata<T> {
    protected Set<String> states; //A
    protected Set<T> alphabet; //X
    protected String initial;
    protected Set<String> acceptedStates;

}
