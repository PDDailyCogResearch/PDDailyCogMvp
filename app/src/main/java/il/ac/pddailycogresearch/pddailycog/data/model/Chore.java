package il.ac.pddailycogresearch.pddailycog.data.model;

import java.io.Serializable;

/**
 * Created by שני on 08/12/2017.
 */

public class Chore implements Serializable {
    public static class PartsConstants {
        public static Integer INSTRUCTION = 1;
        public static Integer TAKE_PICTURE = 2;
        public static Integer CHECK_PICTURE = 3;
        public static Integer TEXT_INPUT = 4;

        public static Integer PARTS_AMOUNT = 4;
    }
    public Chore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class) - Firebase requirement
    }

    public Chore(Integer choreNum) {
        this.choreNum = choreNum;
        this.currentPartNum=1;
        this.completed = false;
        this.instrcClicksNum = 0;
    }

    private Boolean completed;

    private Integer choreNum;

    private Integer currentPartNum;

    private Integer instrcClicksNum;

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean isCompleted() {
        return completed;
    }



    public void setChoreNum(Integer choreNum) {
        this.choreNum = choreNum;
    }

    public Integer getChoreNum() {
        return choreNum;
    }



    public Integer getCurrentPartNum() {
        return currentPartNum;
    }

    public void setCurrentPartNum(Integer currentPartNum) {
        this.currentPartNum = currentPartNum;
    }

    public Integer getInstrcClicksNum() {
        return instrcClicksNum;
    }

    public void setInstrcClicksNum(Integer instrcClicksNum) {
        this.instrcClicksNum = instrcClicksNum;
    }

    public void increaseInstrcClicksNum() {
        this.instrcClicksNum++;
    }

}
