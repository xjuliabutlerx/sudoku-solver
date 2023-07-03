package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FrequencyChecklistTest {

    @Test
    void checklistNotNullUponInstantiation() {
        FrequencyChecklist freq = new FrequencyChecklist();
        Assertions.assertNotNull(freq.getFreq());

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(0, freq.getFreq()[i]);
        }
    }

    @Test
    void incrementAllFreqs() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(2);
        freq.incrementFreq(3);
        freq.incrementFreq(4);
        freq.incrementFreq(5);
        freq.incrementFreq(6);
        freq.incrementFreq(7);
        freq.incrementFreq(8);
        freq.incrementFreq(9);

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(1, freq.getFreq()[i]);
        }
    }

    @Test
    void thereIsANumWithFreqOfOne() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);

        Assertions.assertTrue(freq.freqIsOne());
    }

    @Test
    void getNumWithFreqOne() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(1);

        freq.incrementFreq(2);

        freq.incrementFreq(3);
        freq.incrementFreq(3);

        freq.incrementFreq(4);
        freq.incrementFreq(4);

        freq.incrementFreq(5);
        freq.incrementFreq(5);

        freq.incrementFreq(6);
        freq.incrementFreq(6);

        freq.incrementFreq(7);
        freq.incrementFreq(7);

        freq.incrementFreq(8);
        freq.incrementFreq(8);

        freq.incrementFreq(9);
        freq.incrementFreq(9);

        Assertions.assertTrue(freq.freqIsOne());
        Assertions.assertEquals(2, freq.getNumWithFreqOfOne());
    }

    @Test
    void getNumWithFreqOneDoesNotExistA() {
        FrequencyChecklist freq = new FrequencyChecklist();

        Assertions.assertFalse(freq.freqIsOne());
    }

    @Test
    void getNumWithFreqOneDoesNotExistB() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(1);

        freq.incrementFreq(2);
        freq.incrementFreq(2);

        freq.incrementFreq(3);
        freq.incrementFreq(3);

        freq.incrementFreq(4);
        freq.incrementFreq(4);

        freq.incrementFreq(5);
        freq.incrementFreq(5);

        freq.incrementFreq(6);
        freq.incrementFreq(6);

        freq.incrementFreq(7);
        freq.incrementFreq(7);

        freq.incrementFreq(8);
        freq.incrementFreq(8);

        freq.incrementFreq(9);
        freq.incrementFreq(9);

        Assertions.assertFalse(freq.freqIsOne());
        Assertions.assertEquals(0, freq.getNumWithFreqOfOne());
    }

    @Test
    void getNumWithFreqOneDoesNotExistC() {
        FrequencyChecklist freq = new FrequencyChecklist();

        Assertions.assertFalse(freq.freqIsOne());
        Assertions.assertEquals(0, freq.getNumWithFreqOfOne());
    }

    @Test
    void getNumWhenTwoNumsHaveFreqOfOne() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(1);

        freq.incrementFreq(2);
        freq.incrementFreq(2);

        freq.incrementFreq(3);
        freq.incrementFreq(3);

        freq.incrementFreq(4);
        freq.incrementFreq(4);

        freq.incrementFreq(5);
        freq.incrementFreq(5);

        freq.incrementFreq(6);

        freq.incrementFreq(7);

        freq.incrementFreq(8);
        freq.incrementFreq(8);

        freq.incrementFreq(9);
        freq.incrementFreq(9);

        Assertions.assertTrue(freq.freqIsOne());
        Assertions.assertEquals(6, freq.getNumWithFreqOfOne());
    }

    @Test
    void decrementFreq() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(2);
        freq.incrementFreq(3);
        freq.incrementFreq(4);
        freq.incrementFreq(5);
        freq.incrementFreq(6);
        freq.incrementFreq(7);
        freq.incrementFreq(8);
        freq.incrementFreq(9);

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(1, freq.getFreq()[i]);
        }

        try {
            freq.decrementFreq(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(0, freq.getFreq()[1]);
    }

    @Test
    void decrementFreqStaysAt0() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(2);
        freq.incrementFreq(3);
        freq.incrementFreq(4);
        freq.incrementFreq(5);
        freq.incrementFreq(6);
        freq.incrementFreq(7);
        freq.incrementFreq(8);
        freq.incrementFreq(9);

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(1, freq.getFreq()[i]);
        }

        freq.decrementFreq(2);

        Assertions.assertEquals(0, freq.getFreq()[1]);

        freq.decrementFreq(2);

        Assertions.assertEquals(0, freq.getFreq()[1]);
    }

    @Test
    void decrementAllFreqs() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(2);
        freq.incrementFreq(3);
        freq.incrementFreq(4);
        freq.incrementFreq(5);
        freq.incrementFreq(6);
        freq.incrementFreq(7);
        freq.incrementFreq(8);
        freq.incrementFreq(9);

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(1, freq.getFreq()[i]);
        }

        freq.decrementAllFreqs();

        for (int i = 0; i < 0; i++) {
            Assertions.assertEquals(0, freq.getFreq()[i]);
        }

        freq.decrementAllFreqs();

        for (int i = 0; i < 0; i++) {
            Assertions.assertEquals(0, freq.getFreq()[i]);
        }
    }

    @Test
    void reset() {
        FrequencyChecklist freq = new FrequencyChecklist();

        freq.incrementFreq(1);
        freq.incrementFreq(2);
        freq.incrementFreq(3);
        freq.incrementFreq(4);
        freq.incrementFreq(5);
        freq.incrementFreq(6);
        freq.incrementFreq(7);
        freq.incrementFreq(8);
        freq.incrementFreq(9);

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(1, freq.getFreq()[i]);
        }

        freq.reset();

        for (int i = 0; i < 9; i++) {
            Assertions.assertEquals(0, freq.getFreq()[i]);
        }
    }
}