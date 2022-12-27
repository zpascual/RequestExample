package util;

public class Util {

    public static double placeInAppropriate0To360Scope(double scopeReference, double newAngle){
    	double lowerBound;
        double upperBound;
        double lowerOffset = scopeReference % 360;
        if(lowerOffset >= 0){
        	lowerBound = scopeReference - lowerOffset;
        	upperBound = scopeReference + (360 - lowerOffset);
        }else{
        	upperBound = scopeReference - lowerOffset; 
        	lowerBound = scopeReference - (360 + lowerOffset);
        }
        while(newAngle < lowerBound){
        	newAngle += 360; 
        }
        while(newAngle > upperBound){
        	newAngle -= 360; 
        }
        if(newAngle - scopeReference > 180){
        	newAngle -= 360;
        }else if(newAngle - scopeReference < -180){
        	newAngle += 360;
        }
        return newAngle;
    }

    public static double boundAngle0to360Degrees(double angle){
        // Naive algorithm
        while(angle >= 360.0) {angle -= 360.0;}
        while(angle < 0.0) {angle += 360.0;}
        return angle;
    }

    public static boolean inRange(double value, double min, double max) {
        return min <= value && value <= max;
    }
}
