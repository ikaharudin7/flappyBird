import bagel.Font;
import bagel.Window;

/** This represents the class which projects the screen when the game is own
 *  Only appears after level 1
 * @author Ian Kaharudin
 */
public class Win extends Screen{
    private final String congrats = "Congratulations!";

    /** Default constructor for the win screen
     */
    public Win() {}   // Constructor


    /** Projects the screen with the text, after the player has won
     * @param score This represents the player's current score.
     */
    public void projectScreen(int score) {
        double xCongrats = (Window.getWidth()-font.getWidth(congrats))/2;
        double yCongrats = Window.getHeight() / 2.0;
        double xFinal = (Window.getWidth()-font.getWidth(finalScore+Integer.toString(score)))/2;
        double yFinal = yCongrats + GAP;

        font.drawString(congrats, xCongrats, yCongrats);
        font.drawString(finalScore + Integer.toString(score), xFinal, yFinal);
    }
}
