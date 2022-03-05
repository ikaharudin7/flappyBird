import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

/** Represents the bird object, which is controlled by the player
 * @author Ian Kaharudin
 */
public class Bird implements Move {

    // Constants
    private final Image wingDown0 = new Image("res/level-0/birdWingDown.png");
    private final Image wingUp0 = new Image("res/level-0/birdWingUp.png");
    private final Image wingDown1 = new Image("res/level-1/birdWingDown.png");
    private final Image wingUp1 = new Image("res/level-1/birdWingUp.png");
    private final double BIRD_WIDTH = wingDown0.getWidth();
    private final double ACCEL = 0.4;
    private final double SPEED_THRESHOLD = 9.6;
    private final double FLY_SPEED = -6;
    private final double STARTX = 200;
    private final double STARTY = 350;
    private final int FLAP_FREQ = 10;

    // Variables
    private int frameCount = 0;
    private double SPEED = 0;
    private Point playerLoc = new Point(STARTX, STARTY);

    /** Getter for the width of the bird sprite
     * @return double This returns the width of the bird sprite
     */
    public double getWidth() {
        return BIRD_WIDTH;
    }

    /** Setter for bird location, and resets speed back to 0
     */
    public void setPlayerLoc() {
        playerLoc = new Point(STARTX, STARTY);
        SPEED = 0;
    }

    /** Keeps track of frame count and flickers the fire for every 20 flames
     * @param level Inputs the current level
     * @param count Count is the frame count
     */
    public void flickerSprite(int level, double count) {
        Image wingUp, wingDown;
        if (level==1) {
            wingUp = wingUp1;
            wingDown = wingDown1;
        }
        else {
            wingUp = wingUp0;
            wingDown = wingDown0;
        }
        if (frameCount%FLAP_FREQ==0) {
            wingUp.draw(playerLoc.x, playerLoc.y);
        } else {
            wingDown.draw(playerLoc.x, playerLoc.y);
        }
        frameCount += 1;
    }

    /** Updating the fall gravity for the bird, according to specification
     */
    public void updateFall() {
        double y = playerLoc.y;
        if (SPEED<=SPEED_THRESHOLD) {
            SPEED += ACCEL;
        }
        y += SPEED;
        playerLoc = new Point(STARTX, y);
    }

    /** Logic for updating the speed when spacebar is pressed
     * @param level int containing the level
     */
    public void moveObject(int level) {
        double y = playerLoc.y;
        SPEED = FLY_SPEED;     // Speed of each fly
        y += SPEED;
        playerLoc = new Point(STARTX, y);
    }

    /** The getter that returns the current player location
     * @return point This point contains the player location
     */
    public Point getPlayerLoc() {
        return playerLoc;
    }

    /** The getter that returns the rectangle around the bird's sprite
     * @param point This is the centre location of the bird's sprite
     * @return Rectangle This is the rectangle around the bird's sprite
     */
    public Rectangle getbirdBox(Point point) {
        return wingUp0.getBoundingBoxAt(point);
    }
}
