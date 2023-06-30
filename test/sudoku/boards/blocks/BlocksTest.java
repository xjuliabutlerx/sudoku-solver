package sudoku.boards.blocks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlocksTest {

    protected Blocks block;

    @BeforeEach
    void setUp() {
        block = Blocks.getBlocks();
    }

    @Test
    void instanceNotNull() {
        Assertions.assertNotNull(block);
    }

    @Test
    void hasValidBlockIds() {
        Assertions.assertEquals(1, block.getBlockId(0, 0));
        Assertions.assertEquals(2, block.getBlockId(1, 4));
        Assertions.assertEquals(3, block.getBlockId(2, 8));

        Assertions.assertEquals(4, block.getBlockId(3, 0));
        Assertions.assertEquals(5, block.getBlockId(4, 4));
        Assertions.assertEquals(6, block.getBlockId(5, 8));

        Assertions.assertEquals(7, block.getBlockId(6, 0));
        Assertions.assertEquals(8, block.getBlockId(7, 4));
        Assertions.assertEquals(9, block.getBlockId(8, 8));
    }

    @Test
    void hasInvalidBlockIds() {
        Assertions.assertEquals(0, block.getBlockId(-1, 0));
        Assertions.assertEquals(0, block.getBlockId(1, -1));
        Assertions.assertEquals(0, block.getBlockId(-4, -8));
        Assertions.assertEquals(0, block.getBlockId(10, 12));
        Assertions.assertEquals(0, block.getBlockId(22, -4));
        Assertions.assertEquals(0, block.getBlockId(-3, 10));
    }

    @Test
    void getBlockIndicesFromValidParams() {
        Assertions.assertArrayEquals(new int[] {0, 2, 0, 2}, block.getBlockIndices(1));
        Assertions.assertArrayEquals(new int[] {0, 2, 3, 5}, block.getBlockIndices(2));
        Assertions.assertArrayEquals(new int[] {0, 2, 6, 8}, block.getBlockIndices(3));

        Assertions.assertArrayEquals(new int[] {3, 5, 0, 2}, block.getBlockIndices(4));
        Assertions.assertArrayEquals(new int[] {3, 5, 3, 5}, block.getBlockIndices(5));
        Assertions.assertArrayEquals(new int[] {3, 5, 6, 8}, block.getBlockIndices(6));

        Assertions.assertArrayEquals(new int[] {6, 8, 0, 2}, block.getBlockIndices(7));
        Assertions.assertArrayEquals(new int[] {6, 8, 3, 5}, block.getBlockIndices(8));
        Assertions.assertArrayEquals(new int[] {6, 8, 6, 8}, block.getBlockIndices(9));
    }

    @Test
    void getBlockIndicesFromInvalidParams() {
        Assertions.assertArrayEquals(new int[] {}, block.getBlockIndices(0));
        Assertions.assertArrayEquals(new int[] {}, block.getBlockIndices(-1));
        Assertions.assertArrayEquals(new int[] {}, block.getBlockIndices(10));
    }

    @Test
    void calculateIndexPadding() {
        Assertions.assertArrayEquals(new int[] {0, 0}, block.calculateIndexPadding(1));
        Assertions.assertArrayEquals(new int[] {0, 3}, block.calculateIndexPadding(2));
        Assertions.assertArrayEquals(new int[] {0, 6}, block.calculateIndexPadding(3));
        Assertions.assertArrayEquals(new int[] {3, 0}, block.calculateIndexPadding(4));
        Assertions.assertArrayEquals(new int[] {3, 3}, block.calculateIndexPadding(5));
        Assertions.assertArrayEquals(new int[] {3, 6}, block.calculateIndexPadding(6));
        Assertions.assertArrayEquals(new int[] {6, 0}, block.calculateIndexPadding(7));
        Assertions.assertArrayEquals(new int[] {6, 3}, block.calculateIndexPadding(8));
        Assertions.assertArrayEquals(new int[] {6, 6}, block.calculateIndexPadding(9));
    }
}