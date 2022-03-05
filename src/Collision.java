import bagel.Font;
import bagel.Window;

/** Inheriting from Screen class, this class contains methods to project the screen when player loses the game
 * @author Ian Kaharudin
 */
public class Collision extends Screen {
    private final String gameOver = "GAME OVER";
    private final String finalScore = "FINAL SCORE: ";
    private boolean dead = false;

    /** Default constructor for the collision screen
     */
    public Collision() {}   // Constructor

    /** Renders the screen if the player has lost all their lives
     * @param score This is the current score.
     */
    public void projectScreen(int score) {
        double xGameOver = (Window.getWidth()-font.getWidth(gameOver))/2;
        double xFinal = (Window.getWidth()-font.getWidth(finalScore+Integer.toString(score)))/2;
        double yFinal = centreY + GAP;
        font.drawString(gameOver, xGameOver, centreY);
        font.drawString(finalScore + Integer.toString(score), xFinal, yFinal);
    }

    /** Getter for the crash status
     */
    public void setCrash() {
        dead = true;
    }

    /** Change the crash status
     * @return boolean which returns whether the player is dead or not
     */
    public boolean isDead() {
        return dead;
    }
}
