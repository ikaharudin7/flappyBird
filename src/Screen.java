import bagel.Font;
import bagel.Window;

/** Parent class for game state screens.
 * Win screen, collision screen and level up screen inherit from this class.
 * @author Ian Kaharudin
 */
public abstract class Screen {
    protected final Font font = new Font("res/font/slkscr.ttf", 48);
    protected final int GAP = 75;   // represents gap between the lines of text
    protected double centreY = Window.getHeight() / 2.0;    // Y coordinate for text in the centre.
    protected String finalScore = "FINAL SCORE: ";

    /** Method for projecting the screen. Overridden by subclasses.
     * @param score Current player's score.
     */
    public void projectScreen(int score) {}
}
