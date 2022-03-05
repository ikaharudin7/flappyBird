import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/** Class representing the rock class. One of the weapons which only destroys the plastic pipes.
 * Only appears at level 1
 * @ author Ian Kaharudin
 */
public class Rock extends Weapon {
    private Image weaponImage = new Image("res/level-1/rock.png");
    private Rectangle weaponBox = weaponImage.getBoundingBoxAt(weaponLoc);

    /** Default constructor for the rock class
     */
    public void Rock() {};

    /** Renders the object via moveObject method in Move interface
     * @param level Level of the current game.
     */
    @Override
    public void moveObject(int level) {
        if (level==1) {
            weaponImage.draw(XLoc, YLoc);
            weaponLoc = new Point(XLoc, YLoc);
            weaponBox = weaponImage.getBoundingBoxAt(weaponLoc);
            XLoc -= stepSize;
        }
    }

    /** Method which takes bird rectangle as argument, and checks whether they have intersected/been picked up
     * @param birdBox
     * @param gotItem
     * @return boolean Returns true if an intersection has been detected
     */
    @Override
    // Takes bird rectangle as argument, and checks whether they have touched
    public boolean checkPickedUp(Rectangle birdBox, boolean gotItem) {
        if (weaponBox.intersects(birdBox) && gotItem==false) {
            pickedUp = true;
        }
        return pickedUp;
    }

    /** Method for rendering the weapon at a specific point passed into the argument
     * @param point This is the coordinates of the weapon's current location
     */
    @Override
    // Renders the weapon at a given location
    public void renderWeapon(Point point) {
        weaponImage.draw(point.x, point.y);
    }

    /** Method to render weapon after it has been thrown.
     * @param yLaunch The double which contains the y-coord at which the weapon has been thrown.
     */
    @Override
    // Renders the weapon after being thrown
    public void throwable(double yLaunch) {
        weaponImage.draw(XLoc, yLaunch);
        weaponLoc = new Point(XLoc, yLaunch);
        weaponBox = weaponImage.getBoundingBoxAt(weaponLoc);
        XLoc += stepSize;
    }

    /** Returns rectangle around the weapon
     * @return Rectangle which returns the rectangle around the weaponImage.
     */
    public Rectangle getWeaponBox() {
        return weaponBox;
    }

    @Override
    public void flickerSprite(int level, double count) {}
}
