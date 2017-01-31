import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by johny.lam on 1/28/17.
 */
public class Main {

    public static void main(String[] args) {
        Solver.solveBoard(new Board(board6(), 6, 6, null, null));
    }

    public static List<Block> board6() {
        return Lists.newArrayList(
                new Block(Orientation.HORIZONTAL, 2, new Position(1, 2),  true),
                new Block(Orientation.VERTICAL, 2, new Position(3, 0)),
                new Block(Orientation.VERTICAL, 2, new Position(4, 0)),
                new Block(Orientation.VERTICAL, 3, new Position(0, 1)),
                new Block(Orientation.VERTICAL, 2, new Position(3, 2)),
                new Block(Orientation.VERTICAL, 2, new Position(2, 3)),
                new Block(Orientation.VERTICAL, 2, new Position(5, 4)),
                new Block(Orientation.HORIZONTAL, 3, new Position(0, 0)),
                new Block(Orientation.HORIZONTAL, 2, new Position(1, 1)),
                new Block(Orientation.HORIZONTAL, 2, new Position(4, 3)),
                new Block(Orientation.HORIZONTAL, 2, new Position(3, 4)),
                new Block(Orientation.HORIZONTAL, 3, new Position(0, 5))
        );
    }
}
