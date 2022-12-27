package requests;

public class WaitRequest extends Request {

    private double waitTime = 0.0;
    private double startTime = 0.0;
    private double currentTime = 1678.0; // Don't need this as this is replaced by Timer.getFPGATimestamp()

    public WaitRequest(double waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public void act() {
        startTime = 1678.0; // Replace with Timer.getFPGATimestamp()
    }

    @Override
    public boolean isFinished() {
        return (currentTime - startTime) >= this.waitTime;
    }
    
}
