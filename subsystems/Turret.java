package RequestExample.subsystems;

import RequestExample.Constants;
import RequestExample.requests.Request;
import RequestExample.util.Util;

public class Turret {

    public enum State {
        OPEN_LOOP,
        POSITION
    }

    private State currentState = State.OPEN_LOOP;

    private Motor turretMotor = new Motor();
    private double targetAngle = 0.0;

    PeriodicIO periodicIO  = new PeriodicIO();

    public void setState(State newState) {
        if (newState != currentState) {
            currentState = newState;
        }
    }

    public Turret() {
        turretMotor.mode = ControlMode.PercentOutput;
    }

    private void setAngle(double degrees) {
        targetAngle = boundToRange(degrees);
        periodicIO.controlMode = ControlMode.MotionMagic;
        periodicIO.demand = turretDegreeAngleToIncUnits(degrees);
    }

    private void setPosition(double angle) {
        setState(State.POSITION);
        setAngle(angle);
    }

    public double boundToRange(double angle) {
        angle = Util.placeInAppropriate0To360Scope(getAngle(), angle);

        if(!Util.inRange(angle, Constants.Turret.minAngle, Constants.Turret.maxAngle)) 
            angle = (angle < Constants.Turret.minAngle) ? (angle + 360.0) : (angle - 360.0);

        return angle;
            
    }

    private double turretDegreeAngleToIncUnits(double angle) {
        return angle * Constants.Turret.ratio;
    }

    private double getAngle() {
        return periodicIO.posiiton;
    }

    private boolean hasReachedAngle() {
        return Math.abs(getAngle() - targetAngle) < Constants.Turret.angleTolarance;
    }


    public Request angleReqeust(double angle, double speedScalar, boolean waitForAngle) {
        return new Request() {

            @Override
            public void act() {
                turretMotor.cruiseVelocity = Constants.Turret.maxEncVelocity * speedScalar;
                setPosition(angle);
            }

            @Override
            public boolean isFinished() {
                if (waitForAngle) {
                    return hasReachedAngle();
                }
                return true;
            }
            
        };
    }

    class PeriodicIO {
        //Inputs
        public double posiiton;
        public double velocity;
        public double current;
        public double voltage;

        //Outputs
        public double demand = 0.0;
        public ControlMode controlMode = ControlMode.PercentOutput;
    }

    /**
     * Fake Motor Control Modes
     */
    public enum ControlMode {
        PercentOutput, 
        MotionMagic
    }

    /**
     * Fake motor class
     */
    class Motor {
        double speed;
        double ticks;
        ControlMode mode;
        double cruiseVelocity;
    }
    
}
