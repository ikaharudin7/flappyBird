import bagel.Input;
import bagel.Keys;

/** Class which contains functionality for the timescale mechanisms
 *  Speeds up and slows down the game
 * @author Ian Kaharudin
 */
public class Timescale {
    private double scale = 1;
    private final double FACTOR = 1.5;
    private int speedNum = 1;
    private final int UP_THRESHOLD = 5;
    private final int LOW_THRESHOLD = 1;

    /** Getter for the speedNum
     * @return int which represents the speed number setting
     */
    public int getSpeedNum() { return speedNum; }

    /** Called when L is pressed. Increases the speedNum level, and
     * the scale by 1.5x when called.
     */
    public void increaseSpeed() {
        if (speedNum<UP_THRESHOLD) {
            scale *= FACTOR;
            speedNum++;
        }
    }

    /** Called when k is pressed. Decreases the speedNum level, and
     * the scale by 1.5x when called.
     */
    public void decreaseSpeed() {
        if (speedNum>LOW_THRESHOLD) {
            scale /= FACTOR;
            speedNum--;
        }
    }

    /** Resets the timescale, when level 0 is finished
     */
    public void setTimeScale() {
        scale = 1;
        speedNum = 1;
    }

}
