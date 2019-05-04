package mlcbrUtils;

public class UtilInterface {

    public static void infinite(Infinitecheck condition){
        while(condition.condition()){

        }
    }

    @FunctionalInterface
    public interface Infinitecheck{
        public boolean condition();
    }
}
