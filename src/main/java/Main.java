import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by pikachu on 1/28/17.
 */
public class Main {

    public static void main(String[] args) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(new Block(Orientation.HORIZONTAL, 2, new Position(1, 2),  true));
        blocks.add(new Block(Orientation.HORIZONTAL, 3, new Position(0, 0)));
        blocks.add(new Block(Orientation.VERTICAL, 2, new Position(3, 0)));
        blocks.add(new Block(Orientation.VERTICAL, 2, new Position(4, 0)));
        blocks.add(new Block(Orientation.VERTICAL, 3, new Position(0, 1)));
        blocks.add(new Block(Orientation.HORIZONTAL, 2, new Position(1, 1)));
        blocks.add(new Block(Orientation.VERTICAL, 2, new Position(3, 2)));
        blocks.add(new Block(Orientation.VERTICAL, 2, new Position(2, 3)));
        blocks.add(new Block(Orientation.VERTICAL, 2, new Position(5, 4)));
        blocks.add(new Block(Orientation.HORIZONTAL, 2, new Position(4, 3)));
        blocks.add(new Block(Orientation.HORIZONTAL, 2, new Position(3, 4)));
        blocks.add(new Block(Orientation.HORIZONTAL, 3, new Position(0, 5)));

        Board board = new Board(blocks, 6, 6, null, null);

        Solver.solveBoard(board);
    }
}
