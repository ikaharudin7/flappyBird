import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/** Class representing the bomb class. One of the weapons which destroys the metal pipes.
 * Only appears at level 1
 * @ author Ian Kaharudin
 */
public class Bomb extends Weapon {
    private Image weaponImage = new Image("res/level-1/bomb.png");
    private Rectangle weaponBox = weaponImage.getBoundingBoxAt(weaponLoc);

    /** Constructor to create a bomb object
     */
    public void Bomb() {};

    /** Method which renders the object via moveObject method in Move interface.
     * The weapon travels 'stepSize' pixels left each frame.
     * @param level This is the current level of the game.
     */
    @Override
    public void moveObject(int level) {
        if (level == 1) {
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
    public void flickerSprite(int level, double count) {

    }
}


