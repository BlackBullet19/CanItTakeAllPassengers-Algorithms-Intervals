import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Solution {

    public static void main(String[] args) {
        int[][] trips = new int[][]{{2, 1, 5,}, {3, 3, 7}, {4, 8, 9}, {2, 9, 10}, {2, 9, 11}, {5, 11, 15}};
        int maximalPassengersCount = 5;
        System.out.println(solutionWithAlgo(maximalPassengersCount, trips));
        System.out.println(solutionBruteForce(maximalPassengersCount, trips));
    }

    public static boolean solutionWithAlgo(int maximalPassengerCount, int[][] trips) {
        if (trips.length < 2 && trips[0][0] <= maximalPassengerCount) {
            return true;
        }
        int currentPassengerCount = 0;
        Map<Integer, Integer> result = new TreeMap<>();
        for (int[] trip : trips) {
            result.put(trip[1], result.getOrDefault(trip[1], 0) + trip[0]);
            result.put(trip[2], result.getOrDefault(trip[2], 0) - trip[0]);
        }
        for(int passengerCount : result.values()) {
            currentPassengerCount += passengerCount;
            if(currentPassengerCount > maximalPassengerCount){
                return false;
            }
        }
        return true;
        //TimeComplexity -> O(n logn)
        //SpaceComplexity -> O(n)
    }

    public static boolean solutionBruteForce(int maximalPassengerCount, int[][] trips) {
        int currentPassengerCount = 0;
        Arrays.sort(trips, Comparator.comparingInt(a -> a[1]));
        int currentUsedInterval = 0;
        for (int i = 0; i < trips.length-1; i++) { // 3, 3, 7
            if(currentUsedInterval <= trips[i+1][1]){
                currentPassengerCount = 0;
            }
            currentPassengerCount += trips[i][0];
            if(isPassengersTooMuch(maximalPassengerCount, currentPassengerCount)){
                return false;
            }
            if(trips[i+1][0] > maximalPassengerCount){
                return false;
            }
            if(trips[i][2] > trips[i+1][1]){
                currentPassengerCount += trips[i+1][0];
                currentUsedInterval = Math.max(trips[i][2], trips[i+1][2]);
                if(isPassengersTooMuch(maximalPassengerCount, currentPassengerCount)){
                    return false;
                }
            }
        }
        return true;
        // TimeComplexity O(n logn)
        // SpaceComplexity 0(1)
    }

    public static boolean isPassengersTooMuch(int maximalPassengerCount, int currentPassengerCount){
        return currentPassengerCount > maximalPassengerCount;
    }
}
