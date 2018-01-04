package il.ac.pddailycogresearch.pddailycog.data.model;

import java.io.Serializable;

/**
 * Created by שני on 08/12/2017.
 */

public class Chore implements Serializable {
    public enum ChoreParts{
        INSTRUCTION, TAKE_PICTURE, CHECK_PICTURE, TEXT_INPUT;
        private static ChoreParts[] vals = values();
        public ChoreParts next()
        {
            if((this.ordinal()+1) < vals.length)
                return vals[this.ordinal()+1];
            else
                return null;
        }
    }
    public Chore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class) - Firebase requirement
    }
    private Boolean completed;

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean isCompleted() {
        return completed;
    }

    private Integer choreNum;

    public void setChoreNum(Integer choreNum) {
        this.choreNum = choreNum;
    }

    public Integer getChoreNum() {
        return choreNum;
    }


/*    private Integer currentPartNum;

    public Integer getCurrentPartNum() {
        return currentPartNum;
    }

    public void setCurrentPartNum(Integer currentPartNum) {
        this.currentPartNum = currentPartNum;
    }*/


    private ChoreParts currentPartNum;

    public ChoreParts getCurrentPartNum() {
        return currentPartNum;
    }

    public void setCurrentPartNum(ChoreParts currentPartNum) {
        this.currentPartNum = currentPartNum;
    }
}
