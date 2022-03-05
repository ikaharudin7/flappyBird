import bagel.Font;

/** Class which keeps track of and renders the player's score.
 * @author Ian Kaharudin
 */
public class Score {
    private final Font font = new Font("res/font/slkscr.ttf", 48);
    private int score = 0;
    private final String scoreString = "Score: ";

    /** Default constructor for score
     */
    public Score() {}

    /** Method containing logic for rendering score
     * @param xBird x coordinate of the bird's location
     * @param xPipe x coordinate of the pipe's location
     * @param stepSize current stepSize (timescale adjusted)
     */
    public void renderScore(double xBird, double xPipe, double stepSize) {
        if (xPipe<=xBird && xBird<xPipe+stepSize) {  // When bird is past the pipe
            score++;
        }
        font.drawString(scoreString + score, 100, 100);
    }

    /** Setter for the score at the beginning of level
     */
    public void setScore() {
        score = 0;
    }

    /** Increments the score when bird passes a pipe
     */
    public void incrementScore() {
        score++;
    }

    /** Getter for the score.
     * @return int which represents the current player's score.
     */
    public int getScore() {
        return score;
    }
}
