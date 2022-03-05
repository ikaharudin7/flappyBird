import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;

/** Super class for the weapons. The bomb and rock inherit from this super class.
 * Weapons only appear at level 1.
 * @author Ian Kaharudin
 */
public abstract class Weapon implements Move, ChangeSpeed {
    protected Point weaponLoc = new Point();
    protected double XLoc;
    protected double YLoc;
    protected Random randX = new Random();
    protected Random rand = new Random();
    protected boolean pickedUp = false;
    protected boolean thrown = false;
    protected double stepSize = 5;      // Instead of 3 for FPS reasons
    protected final int NUM_WEAPONS = 2;
    protected final double YSPAWN_MIN = 100.0;
    protected final double YSPAWN_MAX = 500.0;
    protected final double FACTOR = 1.5;


    /** Default setter of the location
     */
    public void setLocation() {
        XLoc = Window.getWidth();
        YLoc = randX.nextInt((int) (YSPAWN_MAX - YSPAWN_MIN)) + YSPAWN_MIN;
        weaponLoc = new Point(XLoc, YLoc);
    }

    /** Setter for the location of weapon, to a given point
     * @param point The current location of the weapon
     */
    public void setLocation(Point point) {
        weaponLoc = point;
    }

    /** Get the stepSize and speed of the current location
     * @return double representing the stepSize.
     */
    public double getStepSize() {
        return stepSize;
    }

    /** Setter for the speed, timescale implementation
     * @param speedNum This is the timescale level
     */
    public void setSpeed(double speedNum) {
        stepSize = 5 * Math.pow(FACTOR, speedNum - 1);
    }

    /** Setter for the x location of the weapon
     */
    public void setXLoc(double x) {
        XLoc = x;
    }

    /** Setter for the thrown status
     */
    public void setThrown() {
        thrown = true;
    }

    /** Getter for whether the item has been thrown
     * @return boolean - true if weapon has been thrown
     */
    public boolean getThrown() {
        return thrown;
    }

    /** Get the X coordinate of weapon
     * @return double containing the x location of the weapon.
     */
    public double getX() {
        return weaponLoc.x;
    }

    /** Code taken from interface to move object
     * @param level Level of the current game.
     */
    public void moveObject(int level) {}

    /** Method for rendering the weapon at a specific point passed into the argument
     * @param point This is the coordinates of the weapon's current location
     */
    public void renderWeapon(Point point) {}

    /** Method to render weapon after it has been thrown.
     * @param yLaunch The double which contains the y-coord at which the weapon has been thrown.
     */
    public void throwable(double yLaunch) {}

    /** Returns whether or not an item is held
     * @return boolean - true if picked up, and false if not picked up
     */
    public boolean isHeld() {
        return pickedUp;
    }

    /** Method which takes bird rectangle as argument, and checks whether they have intersected/been picked up
     * @param birdBox
     * @param gotItem
     * @return boolean Returns true if an intersection has been detected
     */
    public abstract boolean checkPickedUp(Rectangle birdBox, boolean gotItem);

    /** Returns rectangle around the weapon
     * @return Rectangle which returns the rectangle around the weaponImage.
     */
    public abstract Rectangle getWeaponBox();
}
