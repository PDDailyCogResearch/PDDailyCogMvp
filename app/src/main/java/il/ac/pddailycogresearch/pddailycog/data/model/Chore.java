package il.ac.pddailycogresearch.pddailycog.data.model;

/**
 * Created by שני on 08/12/2017.
 */

public class Chore {
    private Integer choreNum;
    private Integer currentPartNum;

    public Chore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class) - Firebase requirement
    }

    public Integer getChoreNum() {
        return choreNum;
    }

    public void setChoreNum(Integer choreNum) {
        this.choreNum = choreNum;
    }

    public Integer getCurrentPartNum() {
        return currentPartNum;
    }

    public void setCurrentPartNum(Integer currentPartNum) {
        this.currentPartNum = currentPartNum;
    }
}
