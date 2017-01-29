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
}
