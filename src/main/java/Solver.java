import com.google.common.collect.Lists;

import java.util.*;

/**
 * A solver for the puzzle
 *
 * Created by johny.lam on 1/28/17.
 */
public class Solver {

    private final Board board;

    public Solver(Board board) {
        this.board = board;
    }

    /**
     * Solves the given board using breadth first search to find the closest path from the initial board
     * configuration to the solved state
     *
     * @return first board found that is in a solved state
     */
    public Board solve() {
        Set<Board> seenBoards = new HashSet<>();
        Queue<Board> nextBoards = new LinkedList<>();
        nextBoards.add(this.board);
        seenBoards.add(this.board);

        Board currentBoard = null;
        while (!nextBoards.isEmpty()) {
            currentBoard = nextBoards.poll();

            if (currentBoard.isSolved()) {
                break;
            }


            List<Block> blocks = currentBoard.getBlocks();
            for (int i = 0; i < blocks.size(); i++) {
                Block b = blocks.get(i);
                List<Block> otherBlocks = Lists.newArrayList(blocks.subList(0, i));
                if (i + 1 < blocks.size()) {
                    otherBlocks.addAll(blocks.subList(i + 1, blocks.size()));
                }

                List<Block> nextMoves = currentBoard.possibleMoves(b);
                for (Block newBlock : nextMoves) {
                    List<Block> newBlockSet = Lists.newArrayList(otherBlocks);
                    newBlockSet.add(newBlock);
                    Board newBoard = currentBoard.moveBlocks(newBlockSet, new Transition(b, newBlock));
                    if (!seenBoards.contains(newBoard)) {
                        nextBoards.add(newBoard);
                        seenBoards.add(newBoard);
                    }
                }

            }
        }

        return currentBoard;
    }

    /**
     * Helper method that take a board and prints of the transitions that will solve it
     * @param board the board to solve
     */
    public static void solveBoard(Board board) {
        List<Transition> solution = new Solver(board).solve().getTransitions();

        System.out.println("Number of moves: " + solution.size());

        for (Transition t : solution) {
            System.out.println(t);
        }
    }
}
