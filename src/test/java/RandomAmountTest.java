import org.junit.jupiter.api.Test;

import java.util.Random;

public class RandomAmountTest {

    @Test
    public void testRandomAmount() {
        System.out.println(getRandomAmount(1, 5));
        System.out.println(getRandomAmount(1, 5));
        System.out.println(getRandomAmount(1, 5));
        System.out.println(getRandomAmount(1, 5));
        System.out.println(getRandomAmount(1, 10));
    }

    private int getRandomAmount(int min, int max) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(max - min + 1) + min;

        while (random < min) {
            random = randomGenerator.nextInt(max - min + 1) + min;
        }

        return random;
    }

}
