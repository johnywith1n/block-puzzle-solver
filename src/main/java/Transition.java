import java.util.ArrayList;
import java.util.List;

/**
 * Created by pikachu on 1/28/17.
 */
public class Transition {

    private final Block before;
    private final Block after;

    public Transition(Block before, Block after) {
        this.before = before;
        this.after = after;
    }

    public Block getBefore() {
        return before;
    }

    public Block getAfter() {
        return after;
    }

    public static List<Transition> compress(List<Transition> transitions) {
        List<Transition> result = new ArrayList<>();

        int i = 0;
        Transition prevTransition = null;

        for (Transition t : transitions) {
            if (t == null) {
                continue;
            }

            if (prevTransition != null && t.before.equals(prevTransition.after)) {
                prevTransition = new Transition(prevTransition.before, t.after);
            } else {
                if (prevTransition != null) {
                    result.add(prevTransition);
                }
                prevTransition = t;
            }
        }

        if (prevTransition != null) {
            result.add(prevTransition);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transition that = (Transition) o;

        if (before != null ? !before.equals(that.before) : that.before != null) return false;
        return after != null ? after.equals(that.after) : that.after == null;
    }

    @Override
    public int hashCode() {
        int result = before != null ? before.hashCode() : 0;
        result = 31 * result + (after != null ? after.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "before=" + before +
                "\n, after=" + after +
                "\n\n";
    }
}
