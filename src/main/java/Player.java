import java.util.ArrayList;
import java.util.List;

/**
 * @Author: py
 */
public class Player {

    private String name;
    private List<Integer> inputNums = new ArrayList<Integer>();
    private Integer score = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getInputNums() {
        return inputNums;
    }

    public void addInputNum(Integer input) {
        inputNums.add(input);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void reset(){
        inputNums.clear();
        score = 0;
    }
}
