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
        blocks.add(new Block(Orientation.HORIZONTAL, 2, Lists.newArrayList(new Position(1, 2), new Position(2, 2)), true));
        blocks.add(new Block(Orientation.HORIZONTAL, 3, Lists.newArrayList(new Position(0, 0), new Position(1, 0), new Position(2, 0))));
        blocks.add(new Block(Orientation.VERTICAL, 2, Lists.newArrayList(new Position(3, 0), new Position(3, 1))));
        blocks.add(new Block(Orientation.VERTICAL, 2, Lists.newArrayList(new Position(4, 0), new Position(4, 1))));
        blocks.add(new Block(Orientation.VERTICAL, 3, Lists.newArrayList(new Position(0, 1), new Position(0, 2), new Position(0, 3))));
        blocks.add(new Block(Orientation.HORIZONTAL, 2, Lists.newArrayList(new Position(1, 1), new Position(2, 1))));
        blocks.add(new Block(Orientation.VERTICAL, 2, Lists.newArrayList(new Position(3, 2), new Position(3, 3))));
        blocks.add(new Block(Orientation.VERTICAL, 2, Lists.newArrayList(new Position(2, 3), new Position(2, 4))));
        blocks.add(new Block(Orientation.VERTICAL, 2, Lists.newArrayList(new Position(5, 4), new Position(5, 5))));
        blocks.add(new Block(Orientation.HORIZONTAL, 2, Lists.newArrayList(new Position(4, 3), new Position(5, 3))));
        blocks.add(new Block(Orientation.HORIZONTAL, 2, Lists.newArrayList(new Position(3, 4), new Position(4, 4))));
        blocks.add(new Block(Orientation.HORIZONTAL, 3, Lists.newArrayList(new Position(0, 5), new Position(1, 5), new Position(2, 5))));

        Board board = new Board(blocks, 6, 6, null, null);
        Solver solver = new Solver(board);

        Set<Board> solutions = solver.solve();

        board = solver.findBestSolution(solutions);

        List<Transition> solution = board.getTransitions();

        System.out.println(solution.size());

        for (Transition t : solution) {
            System.out.println(t);
        }
    }
}
