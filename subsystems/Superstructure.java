package RequestExample.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import RequestExample.requests.ParallelRequest;
import RequestExample.requests.Request;
import RequestExample.requests.SequentialRequest;
import RequestExample.requests.WaitRequest;

public class Superstructure {

    private Turret turret = new Turret();
    private Intake intake = new Intake();

    private Request activeRequest = null;
    private List<Request> queuedReqeusts = new ArrayList<>(0);
    private boolean hasNewReqeust = false;
    private boolean hasAllReqeustsCoompleted = false;

    private void setActiveRequest(Request req) {
        activeRequest = req;
        hasNewReqeust = true;
        hasAllReqeustsCoompleted = false;
    }

    private void clearRequestQueue() {
        queuedReqeusts.clear();
    }
    
    private void setRequestQueue(List<Request> reqs) {
        clearRequestQueue();
        for (Request req : reqs) {
            queuedReqeusts.add(req);
        }
    }

    private void setRequestQueue(Request req) {
        setRequestQueue(Arrays.asList(req));
    }

    private void lambdaRunRequest(Request req) {
        setActiveRequest(req);
        clearRequestQueue();
    }

    private void runRequestWithQueue(Request activeReq, Request queueReq) {
        setActiveRequest(activeRequest);
        setRequestQueue(queueReq);
    }

    private void addRequestToQueue(Request req) {
        queuedReqeusts.add(req);
    }   

    private void replaceRequestQueue(Request req) {
        setRequestQueue(req);
    }
    
    /**
     * This goes in the onLoop method of the superstructure loop but didn't want to recreate
     * all of the stuff to make the loop function properly without the robot.
     */
    public void onLoop() {
        while(true) {
            if (hasNewReqeust && activeRequest != null) {
                activeRequest.act();
                hasNewReqeust = false;
            }
            
            if (activeRequest == null && queuedReqeusts.isEmpty()) {
                hasAllReqeustsCoompleted = true;
            } else if (activeRequest == null) {
                setActiveRequest(queuedReqeusts.remove(0));
            } else if (activeRequest.isFinished()) {
                activeRequest = null;
            }
        }
    }

    public SequentialRequest shootingRequest() {
        return new SequentialRequest(
            turret.angleReqeust(35.0, 1.0, true),
            intake.shootingReqeuest()
        );
    }

    public ParallelRequest offRequest() {
        return new ParallelRequest(
            turret.angleReqeust(0.0, 1.0, false),
            intake.offRequest()
        );
    }

    
}
