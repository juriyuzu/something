package utilities;

import java.util.Random;

public class WeightedRandom {
    public static int weightedRandom(int[] weight, int size) {
        // Calculate the total weight
        int totalWeight = 0;
        for (int i = 0; i < size; i++) {
            totalWeight += weight[i];
        }

        // Generate a random number between 0 and totalWeight
        Random rand = new Random();
        int randomWeight = rand.nextInt(totalWeight);

        // Find the index corresponding to the random weight
        int cumulativeWeight = 0;
        for (int i = 0; i < size; i++) {
            cumulativeWeight += weight[i];
            if (randomWeight < cumulativeWeight) {
                return i;
            }
        }

        // Should never reach here if weights are positive
        return -1;
    }

    public static void main(String[] args) {
        int[] weights = {1, 2, 3, 4};
        int size = weights.length;
        int index = weightedRandom(weights, size);
        System.out.println("Random index: " + index);
    }
}
