import com.google.common.collect.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class NAB {
    private List<String> states; //A
    private List<String> inputAlphabet; //X
    private Set<String> initial;
    private Set<String> acceptedStates;
    private Table<String, String, LinkedList<String>> transition; //f
}
