package RequestExample.requests;

import java.util.ArrayList;
import java.util.List;

public class SequentialRequest extends Request {

    List<Request> requests = new ArrayList<>();
    Request currentRequest = null;

    /**
     * Adds in a list of requests into the local requests list using variable arguments.
     * @param reqs List of Request type arguments delineated by a comma. Can range from 0 - inf.
     */
    public SequentialRequest(Request... reqs) {
        for (Request req : reqs) {
            this.requests.add(req);
        }
    }

    /**
     * Adds a list of requets into the local request list using a set Java list.
     * @param reqs List of Request type arguments.
     */
    public SequentialRequest(List<Request> reqs) {
        for (Request req : reqs) {
            this.requests.add(req);
        }
    }

    @Override
    public void act() {
        currentRequest = requests.remove(0);
        currentRequest.act();        
    }

    @Override
    public boolean isFinished() {
        if (currentRequest.isFinished() && requests.isEmpty()) {
            currentRequest = null;
            return true;
        } else if (currentRequest.isFinished()) {
            currentRequest = requests.remove(0);
            currentRequest.act();
        }
        return false;
    }
    
}
