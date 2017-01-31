import com.google.common.collect.ImmutableList;

/**
 * Represents a block on the board
 *
 * Created by johny.lam on 1/28/17.
 */
public class Block {

    private final Orientation orientation;
    private final int length;
    private final ImmutableList<Position> positions;
    private final boolean isSpecial;

    /**
     * Create a new Block
     * @param orientation whether the block is horizontal or vertical
     * @param length how many units long is the block
     * @param head the position of the first unit that this block occupies on the board with the top left being the origin
     * @param isSpecial whether or not this block is the block that needs to exit the board to solve the puzzle
     */
    public Block(Orientation orientation, int length, Position head, boolean isSpecial) {
        this.orientation = orientation;
        this.length = length;
        this.isSpecial = isSpecial;

        ImmutableList.Builder<Position> builder = ImmutableList.builder();
        builder.add(head);
        for (int i = 1; i < this.length; i++) {
            if (this.orientation == Orientation.HORIZONTAL) {
                builder.add(new Position(head.getX() + i, head.getY()));
            } else {
                builder.add(new Position(head.getX(), head.getY() + i));
            }
        }
        this.positions = builder.build();
    }

    public Block(Orientation orientation, int length, Position head) {
        this(orientation, length, head, false);
    }

    /**
     * Creates a new block that represents moving this block to the new position
     * @param newPosition the position of the first unit that this block now occupies on the board with the top left being the origin
     * @return a new Block at the new position
     */
    public Block moveBlock(Position newPosition) {
        return new Block(this.getOrientation(), this.getLength(), newPosition, this.isSpecial());
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getLength() {
        return length;
    }

    public ImmutableList<Position> getPositions() {
        return positions;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        if (length != block.length) return false;
        if (isSpecial != block.isSpecial) return false;
        if (orientation != block.orientation) return false;
        return positions != null ? positions.equals(block.positions) : block.positions == null;
    }

    @Override
    public int hashCode() {
        int result = orientation != null ? orientation.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        result = 31 * result + (isSpecial ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Block{" +
                "orientation=" + orientation +
                ", length=" + length +
                ", positions=" + positions +
                ", isSpecial=" + isSpecial +
                '}';
    }
}
