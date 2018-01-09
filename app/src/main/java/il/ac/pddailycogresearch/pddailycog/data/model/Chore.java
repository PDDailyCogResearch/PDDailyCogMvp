package il.ac.pddailycogresearch.pddailycog.data.model;

import java.io.Serializable;

/**
 * Created by שני on 08/12/2017.
 */

public class Chore implements Serializable {
    public static final class PartsConstants {
        public static final int INSTRUCTION = 1;
        public static final int TAKE_PICTURE = 2;
        public static final int TEXT_INPUT = 3;

        public static final int PARTS_AMOUNT = 3;
    }

    public Chore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class) - Firebase requirement
    }

    public Chore(Integer choreNum) {
        this.choreNum = choreNum;
        this.currentPartNum=1;
        this.completed = false;
        this.instrcClicksNum = 0;
        this.takePicClickNum=0;
    }

    private Boolean completed;

    private Integer choreNum;

    private Integer currentPartNum;

    private Integer instrcClicksNum;

    private Integer takePicClickNum;

    private String resultImg;

    private String resultText;


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

    public Integer getTakePicClickNum() {
        return takePicClickNum;
    }

    public void setTakePicClickNum(Integer takePicClickNum) {
        this.takePicClickNum = takePicClickNum;
    }

    public void increaseTakePicClickNum() {
        this.takePicClickNum++;
    }

    public String getResultImg() {
        return resultImg;
    }

    public void setResultImg(String resultImg) {
        this.resultImg = resultImg;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

}
