import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a board containing blocks
 *
 * Created by johny.lam on 1/28/17.
 */
public class Board {

    private final ImmutableList<Block> blocks;
    /* The Block that needs to exit the board for it to be solved */
    private final Block specialBlock;
    /* The representation of the board and which units are occupied by which block */
    private final Block[][] board;
    private final Transition transition;
    private final Board parent;

    /**
     * Create a new board
     * @param blocks the blocks contained on thsib oard
     * @param rowLength how long each row is on the board
     * @param columnLength how long each column is on the board
     * @param transition which block moved to transition this board configuration
     * @param parent the board representing the previous configuration before the transition block moved
     */
    public Board(List<Block> blocks, int rowLength, int columnLength, Transition transition, Board parent) {
        this.blocks = ImmutableList.copyOf(blocks);
        this.board = new Block[rowLength][columnLength];
        this.transition = transition;
        this.parent = parent;

        Block specialBlock = null;
        for (Block b : blocks) {
            for (Position p : b.getPositions()) {
                if (p.getX() < board.length) {
                    board[p.getX()][p.getY()] = b;
                }
            }

            if (b.isSpecial()) {
                specialBlock = b;
            }
        }
        this.specialBlock = specialBlock;
    }

    /**
     * Creates a new block from this board by moving a block
     * @param newBlocks the blocks for the new configuration
     * @param transition the current and next configuration for the block that moved
     * @return
     */
    public Board moveBlocks(List<Block> newBlocks, Transition transition) {
        int rowLength = this.board.length;
        int columnLength = this.board[0].length;

        return new Board(newBlocks, rowLength, columnLength, transition, this);
    }

    public ImmutableList<Block> getBlocks() {
        return blocks;
    }

    public Transition getTransition() {
        return transition;
    }

    public Board getParent() {
        return parent;
    }

    /**
     * @return Whether or not the board is in a solved configuration
     */
    public boolean isSolved() {
        List<Position> specialBlockPositions = this.specialBlock.getPositions();
        Position tail = specialBlockPositions.get(specialBlockPositions.size() - 1);
        return tail.getX() >= this.board.length;
    }

    /**
     * Checks whether or not the block can be moved to its new position
     * @param b the block to check
     * @param newPosition the new position the block occupies
     * @return whether or not the move is allowed
     */
    private boolean isAllowedMove(Block b, List<Position> newPosition) {
        Position oldStart = b.getPositions().get(0);
        Position oldEnd = b.getPositions().get(b.getPositions().size() - 1);
        Position newStart = newPosition.get(0);
        Position newEnd = newPosition.get(newPosition.size() - 1);

        int start, end;
        if (b.getOrientation() == Orientation.HORIZONTAL) {
            start = newStart.getX();
            end = oldStart.getX();

            if (end < start) {
                start = oldEnd.getX();
                end = newEnd.getX();
            }

        } else {
            start = newStart.getY();
            end = oldStart.getY();

            if (end < start) {
                start = oldEnd.getY();
                end = newEnd.getY();
            }
        }

        for (int i = start; i <= end; i++) {
            if (b.isSpecial() && i >= this.board.length) {
                continue;
            }

            Block blockAtPosition;
            if (b.getOrientation() == Orientation.HORIZONTAL) {
                blockAtPosition = this.board[i][oldStart.getY()];
            } else {
                blockAtPosition = this.board[oldStart.getX()][i];
            }
            if (blockAtPosition != null && !blockAtPosition.equals(b)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return Returns a list of transitions leading from the initial board configuration to the current configuration
     */
    public List<Transition> getTransitions() {
        LinkedList<Transition> transitions = new LinkedList<>();
        Board b = this;

        transitions.addFirst(b.getTransition());
        while (b.getParent() != null) {
            b = b.getParent();
            transitions.addFirst(b.getTransition());
        }

        return Transition.compress(transitions);
    }

    /**
     * Generate the blocks that can come about as a result of moving the given block
     * @param block the block to move
     * @return a list of blocks that can result from moving the given block
     */
    public List<Block> possibleMoves(Block block) {
        List<Block> result = new ArrayList<>();

        Position head = block.getPositions().get(0);

        if (block.getOrientation() == Orientation.VERTICAL) {
            int y = head.getY();
            for (int i = 1; i <= this.board.length; i++) {
                int newY = y - i;
                if (newY > -1) {
                    List<Position> newPositions = new ArrayList<>();
                    for (Position p : block.getPositions()) {
                        newPositions.add(new Position(p.getX(), p.getY() - i));
                    }
                    if (isAllowedMove(block, newPositions)) {
                        result.add(block.moveBlock(newPositions.get(0)));
                    }
                } else {
                    break;
                }
            }
        } else {
            int x = head.getX();
            for (int i = 1; i <= this.board.length; i++) {
                int newX = x - i;
                if (newX > -1) {
                    List<Position> newPositions = new ArrayList<>();
                    for (Position p : block.getPositions()) {
                        newPositions.add(new Position(p.getX() - i, p.getY()));
                    }
                    if (isAllowedMove(block, newPositions)) {
                        result.add(block.moveBlock(newPositions.get(0)));
                    }
                } else {
                    break;
                }
            }
        }

        Position tail = block.getPositions().get(block.getPositions().size() - 1);
        if (block.getOrientation() == Orientation.VERTICAL) {
            int y = tail.getY();
            for (int i = 1; i <= this.board.length; i++) {
                int newY = y + i;
                if (newY < this.board.length) {
                    List<Position> newPositions = new ArrayList<>();
                    for (Position p : block.getPositions()) {
                        newPositions.add(new Position(p.getX(), p.getY() + i));
                    }
                    if (isAllowedMove(block, newPositions)) {
                        result.add(block.moveBlock(newPositions.get(0)));
                    }
                } else {
                    break;
                }
            }
        } else {
            int x = tail.getX();
            for (int i = 1; i <= this.board.length; i++) {
                int newX = x + i;
                boolean solved = block.isSpecial() && tail.getY() == 2 && newX > 5;
                if (newX < this.board[0].length || solved) {
                    List<Position> newPositions = new ArrayList<>();
                    for (Position p : block.getPositions()) {
                        newPositions.add(new Position(p.getX() + i, p.getY()));
                    }
                    if (isAllowedMove(block, newPositions)) {
                        result.add(block.moveBlock(newPositions.get(0)));
                    }
                    if (solved) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        return Arrays.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        return "Board{\n" +
                "blocks=" + blocks +
                "\n board=" + Arrays.deepToString(board) +
                "\n transition=" + transition +
                '}';
    }
}
