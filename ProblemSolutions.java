/******************************************************************
 *
 *   Evan Bowser / 002
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values        - int[] array to be sorted.
     * @param ascending     - if true,method performs an ascending sort, else descending.
     *                        There are two method signatures allowing this parameter
     *                        to not be passed and defaulting to 'true (or ascending sort).
     */

    public  void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending ) {

        int n = values.length;

        for (int i = 0; i < n - 1; i++) {

            int index = i;  // Assume current index holds the correct value

        // Find the minimum or maximum element in the remaining subarray
        for (int j = i + 1; j < n; j++) {
            if (ascending) {
                if (values[j] < values[index]) {
                    index = j;
                }
            } else {
                if (values[j] > values[index]) {
                    index = j;
                }
            }
        }

        // Swap the found element into its correct position
        if (index != i) {
            int temp = values[i];
            values[i] = values[index];
            values[index] = temp;
        }

        }

    } // End class selectionSort


    /**
     *  Method mergeSortDivisibleByKFirst
     *
     *  This method will perform a merge sort algorithm. However, all numbers
     *  that are divisible by the argument 'k', are returned first in the sorted
     *  list. Example:
     *        values = { 10, 3, 25, 8, 6 }, k = 5
     *        Sorted result should be --> { 10, 25, 3, 6, 8 }
     *
     *        values = { 30, 45, 22, 9, 18, 39, 6, 12 }, k = 6
     *        Sorted result should be --> { 30, 18, 6, 12, 9, 22, 39, 45 }
     *
     * As shown above, this is a normal merge sort operation, except for the numbers
     * divisible by 'k' are first in the sequence.
     *
     * @param values    - input array to sort per definition above
     * @param k         - value k, such that all numbers divisible by this value are first
     */

    public void mergeSortDivisibleByKFirst(int[] values, int k) {

        // Protect against bad input values
        if (k == 0)  return;
        if (values.length <= 1)  return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length-1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first
     */

     private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right) {
        // Merges two sorted subarrays [left..mid] and [mid+1..right],
        // ensuring elements divisible by k appear first (in original order).
    
        int[] temp = new int[right - left + 1]; // Temporary array to store merged result
        int index = 0;
    
        int i = left;      // Pointer for left half
        int j = mid + 1;   // Pointer for right half
    
        // Pass 1: Merge all elements divisible by k, preserving relative order
        while (i <= mid && j <= right) {
            boolean iDiv = arr[i] % k == 0;
            boolean jDiv = arr[j] % k == 0;
    
            if (iDiv && jDiv) {
                temp[index++] = arr[i++];
            } else if (iDiv) {
                temp[index++] = arr[i++];
            } else if (jDiv) {
                temp[index++] = arr[j++];
            } else {
                break; // Stop once both pointers hit non-divisible numbers
            }
        }
    
        // Collect remaining divisible elements from left side
        while (i <= mid && arr[i] % k == 0) {
            temp[index++] = arr[i++];
        }
    
        // Collect remaining divisible elements from right side
        while (j <= right && arr[j] % k == 0) {
            temp[index++] = arr[j++];
        }
    
        // Pass 2: Merge remaining (non-divisible) elements in sorted order
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[index++] = arr[i++];
            } else {
                temp[index++] = arr[j++];
            }
        }
    
        // Append leftovers from either side
        while (i <= mid) {
            temp[index++] = arr[i++];
        }
    
        while (j <= right) {
            temp[index++] = arr[j++];
        }
    
        // Copy merged content back into the original array segment
        System.arraycopy(temp, 0, arr, left, temp.length);
    }


    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order.
     * If the mass of the planet is greater than or equal to the mass of the asteroid, the
     * asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the
     * planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     *
     * Example 1:
     *   Input: mass = 10, asteroids = [3,9,19,5,21]
     *   Output: true
     *
     * Explanation: One way to order the asteroids is [9,19,5,3,21]:
     * - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
     * - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
     * - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
     * - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
     * - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
     * All asteroids are destroyed.
     *
     * Example 2:
     *   Input: mass = 5, asteroids = [4,9,23,4]
     *   Output: false
     *
     * Explanation:
     * The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
     * After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
     * This is less than 23, so a collision would not destroy the last asteroid.
     *
     * Constraints:
     *     1 <= mass <= 105
     *     1 <= asteroids.length <= 105
     *     1 <= asteroids[i] <= 105
     *
     * @param mass          - integer value representing the mass of the planet
     * @param asteroids     - integer array of the mass of asteroids
     * @return              - return true if all asteroids destroyed, else false.
     */

    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {

        // Sort asteroids so we try to destroy smaller ones first
        Arrays.sort(asteroids);

    // Iterate through each asteroid
        for (int i = 0; i < asteroids.length; i++) {
        // If asteroid is too massive to destroy, return false immediately
            if (mass < asteroids[i]) {
            return false;
            }

        // Otherwise, destroy the asteroid and gain its mass
        mass += asteroids[i];
    }

    // All asteroids successfully destroyed
    return true;
    }


    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time, provided the
     * sum of the weight of those people is at most limit. Return the minimum number
     * of rescue sleds to carry every given person.
     *
     * Example 1:
     *    Input: people = [1,2], limit = 3
     *    Output: 1
     *    Explanation: 1 sled (1, 2)
     *
     * Example 2:
     *    Input: people = [3,2,2,1], limit = 3
     *    Output: 3
     *    Explanation: 3 sleds (1, 2), (2) and (3)
     *
     * Example 3:
     *    Input: people = [3,5,3,4], limit = 5
     *    Output: 4
     *    Explanation: 4 sleds (3), (3), (4), (5)
     *
     * @param people    - an array of weights for people that need to go in a sled
     * @param limit     - the weight limit per sled
     * @return          - the minimum number of rescue sleds required to hold all people
     */

     public static int numRescueSleds(int[] people, int limit) {
        // Calculates the minimum number of sleds needed to rescue everyone.
        // Each sled can carry at most two people and cannot exceed the weight limit.
    
        Arrays.sort(people); // Sort people by weight (lightest to heaviest)
    
        int left = 0; // Pointer to the lightest person
        int right = people.length - 1; // Pointer to the heaviest person
        int sledCount = 0; // Total sleds used
    
        while (left <= right) {
            // Try to pair the lightest and heaviest person together
            if (people[left] + people[right] <= limit) {
                left++; // Pair successful, move to next lightest person
            }
    
            // In all cases, the heaviest person gets on a sled
            right--;
    
            // One sled used in this iteration
            sledCount++;
        }
    
        return sledCount;
    }
    

} // End Class ProblemSolutions
