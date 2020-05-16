package tools;

/**
 * Created by finne on 26.01.2018.
 */
public class ArrayTools {

    public static double[][][] copyArray(double[][][] array) {
        double[][][] out = new double[array.length][array[0].length][array[0][0].length];
        for(int i = 0; i < out.length; i++) {
            for(int n = 0; n<  out[0].length; n++) {
                for(int k = 0; k < out[0][0].length; k++) {
                    out[i][n][k] = array[i][n][k];
                }
            }
        }
        return out;
    }

    public static double[] convertFlattenedArray(double[][][] array) {
        return array[0][0];
    }

    public static double[][][] createComplexFlatArray(double... input) {
        return new double[][][]{{input}};
    }

    public static double[][][] createRandomArray(int depth, int width, int height, double lower, double upper) {
        double[][][] out = new double[depth][width][height];
        for(int i = 0; i < out.length; i++) {
            out[i] = createRandomArray(width, height, lower, upper);
        }
        return out;
    }

    public static double[][] createRandomArray(int width, int height, double lower, double upper) {
        double[][] out = new double[width][height];
        for(int i = 0; i < out.length; i++) {
            out[i] = createRandomArray(height, lower, upper);
        }
        return out;
    }

    public static double[] createRandomArray(int height, double lower, double upper) {
        double[] out = new double[height];
        for(int i = 0; i < out.length; i++) {
            out[i] = randomValue(lower, upper);
        }
        return out;
    }


    public static void randomiseArray(double[][][] array, double lower, double upper) {
        for(int i = 0; i < array.length; i++) {
            randomiseArray(array[i], lower, upper);
        }
    }

    public static void randomiseArray(double[][] array, double lower, double upper) {
        for(int i = 0; i < array.length; i++) {
            randomiseArray(array[i], lower, upper);
        }
    }

    public static void randomiseArray(double[] array, double lower, double upper) {
        for(int i = 0; i < array.length; i++) {
            array[i] = randomValue(lower, upper);
        }
    }


    public static Integer[] randomValues(int lowerBound, int upperBound, int amount) {

        lowerBound --;

        if(amount > (upperBound-lowerBound)){
            return null;
        }

        Integer[] values = new Integer[amount];
        for(int i = 0; i< amount; i++){
            int n = (int)(Math.random() * (upperBound-lowerBound+1) + lowerBound);
            while(containsValue(values, n)){
                n = (int)(Math.random() * (upperBound-lowerBound+1) + lowerBound);
            }
            values[i] = n;
        }
        return values;
    }

    public static <T extends Comparable<T>> boolean containsValue(T[] ar, T value){
        for(int i = 0; i < ar.length; i++){
            if(ar[i] != null){
                if(value.compareTo(ar[i]) == 0){
                    return true;
                }
            }

        }
        return false;
    }

    public static int indexOfHighestValue(double[] values){
        int index = 0;
        for(int i = 1; i < values.length; i++){
            if(values[i] > values[index]){
                index = i;
            }
        }
        return index;
    }

    public static double randomValue(double lower, double upper) {
        return Math.random() * (upper - lower) + lower;
    }
}
