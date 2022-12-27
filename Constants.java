package RequestExample;
public class Constants {

    /**
     * All values in degrees unless otherwise specified
     */
    public static class Turret {
        public static double minAngle = 10;
        public static double maxAngle = 290;
        public static double ratio = 8;
        public static double maxEncVelocity = 20000;
        public static double angleTolarance = 0.5;
    }

    public static class Intake {
        public static double shootingDemand = 0.25;
    }
}
