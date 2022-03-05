import bagel.Window;

/** Class representing the screen when the player levels up
 * @author Ian Kaharudin
 */
public class LevelUp extends Screen {
    private final String LEVELUP = "LEVEL-UP!";

    /** Method for projecting the screen once level 0 is finished
     * @param score This is the current score of the player
     */
    public void projectScreen(int score) {
        double xCongrats = (Window.getWidth()-font.getWidth(LEVELUP))/2;
        double xFinal = (Window.getWidth()-font.getWidth(finalScore+Integer.toString(score)))/2;
        double yFinal = centreY + GAP;

        font.drawString(LEVELUP, xCongrats, centreY);
    }
}
