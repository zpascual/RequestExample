package subsystems;

import requests.Request;

public class Intake {

    private enum State {
        SHOOTING(0.75),
        OFF(0.0);

        double speed;
        State(double speed){
            this.speed = speed;
        }
    }

    private State currentState = State.OFF;

    private Motor intakeMotor = new Motor();

    public Intake() {
        intakeMotor.demand = 0.0;
    }

    private void setDemand(double demand) {
        intakeMotor.demand = demand;
    }

    private void conformToState(State desiredState) {
        if (currentState != desiredState) {
            currentState = desiredState;
            setDemand(desiredState.speed);
        }
    }

    public Request shootingReqeuest() {
        return new Request() {

            @Override
            public void act() {
                conformToState(State.SHOOTING);                
            }

            @Override
            public boolean isFinished() {
                return true;
            }
            
        };
    }

    public Request offRequest() {
        return new Request() {

            @Override
            public void act() {
                conformToState(State.OFF);                
            }

            @Override
            public boolean isFinished() {
                return true;
            }
            
        };
    }

    /**
     * Fake motor class
     */
    class Motor {
        double demand;
    }
    
}
