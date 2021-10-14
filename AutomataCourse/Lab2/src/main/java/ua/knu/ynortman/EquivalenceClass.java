package ua.knu.ynortman;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Data
@ToString(includeFieldNames = false)
@EqualsAndHashCode(exclude = {"name"})
public class EquivalenceClass {
    private String name;
    private List<String> states;

    public EquivalenceClass(String name) {
        this.name = name;
        this.states = new LinkedList<>();
    }

    public void addState(String state) {
        states.add(state);
    }
}
