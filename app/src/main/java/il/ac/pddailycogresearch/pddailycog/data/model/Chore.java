package il.ac.pddailycogresearch.pddailycog.data.model;

/**
 * Created by שני on 08/12/2017.
 */

public class Chore {
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

    private Integer choreNum;
    private ChoreParts currentPartNum;

    public Chore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class) - Firebase requirement
    }

    public Integer getChoreNum() {
        return choreNum;
    }

    public void setChoreNum(Integer choreNum) {
        this.choreNum = choreNum;
    }

    public ChoreParts getCurrentPartNum() {
        return currentPartNum;
    }

    public void setCurrentPartNum(ChoreParts currentPartNum) {
        this.currentPartNum = currentPartNum;
    }
}
