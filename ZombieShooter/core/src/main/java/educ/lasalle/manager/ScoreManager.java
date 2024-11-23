package educ.lasalle.manager;

import java.io.*;

public class ScoreManager {
    private static final int MAX_SCORE = 100;
    private int score;

    public ScoreManager() {
        this.score = 0;
    }

    public void increaseScore(int amount) {
        score += amount;
        if (score > MAX_SCORE) {
            score = 0;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void serializeScore(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("score.ser")))
        {
            oos.writeObject(score);
            System.out.println("Score saved successfully");
        } catch (IOException e) {
            System.out.println("Cannot save score");
        }
    }
}
