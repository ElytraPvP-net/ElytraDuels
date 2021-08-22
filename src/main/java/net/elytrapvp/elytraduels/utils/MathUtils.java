package net.elytrapvp.elytraduels.utils;

public class MathUtils {
    public static int percent(double currentValue, double maxValue){
        double percent = (currentValue/maxValue) *100;
        return (int) percent;
    }
}
