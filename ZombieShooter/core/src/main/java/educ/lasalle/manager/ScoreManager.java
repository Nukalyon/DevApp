package educ.lasalle.manager;

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
}
