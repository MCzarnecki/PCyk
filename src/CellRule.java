import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CellRule {

    volatile AtomicBoolean evaluated = new AtomicBoolean(false);
    volatile String coordinates;
    volatile List<Rule> rules = new ArrayList<>();

    volatile int xCor;
    volatile int yCor;

    boolean isNull;

    CellRule(String coordinates, boolean isNull) {
        this.coordinates = coordinates;
        this.isNull = isNull;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        for (Rule rule: rules) {
            value.append(rule.toString()).append("\n");
        }
        return value.toString();
    }
}
