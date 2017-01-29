import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by pikachu on 1/28/17.
 */
public class Solver {

    private final Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public Board findBestSolution(Set<Board> solutions) {
        int bestSolutionLength = Integer.MAX_VALUE;
        Board bestSolution = null;

        for (Board b : solutions) {
            List<Transition> solution = b.getTransitions();

            if (solution.size() < bestSolutionLength) {
                bestSolutionLength = solution.size();
                bestSolution = b;
            }
        }
        return bestSolution;
    }

    public Set<Board> solve() {
        Set<Board> solutions = new HashSet<>();
        Set<Board> seenBoards = new HashSet<>();
        Queue<Board> nextBoards = new LinkedList<>();
        nextBoards.add(this.board);
        seenBoards.add(this.board);

        Board currentBoard;
        while (!nextBoards.isEmpty()) {
            currentBoard = nextBoards.poll();

            if (currentBoard.isSolved()) {
                solutions.add(currentBoard);
                continue;
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

        return solutions;

    }
}
