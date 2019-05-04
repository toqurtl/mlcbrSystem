package ga;

public class GaUtils {
    public static boolean compareTo(double[] arr1, double[] arr2){
        boolean temp = true;
        for(int i=0;i<arr1.length;i++) {
            if(Double.compare(arr1[i], arr2[i])!=0) {
                temp = false;
                break;
            }
        }
        return temp;
    }

}
