import bagel.Image;
import java.lang.Math;
import java.util.Random;

import bagel.DrawOptions;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/** Class containing the implementations of the constantly appearing pipes and the
 * rectangles used for collision detection
 * @author Ian Kaharudin
 */

public class Pipe implements Move, ChangeSpeed {
    // Pipe constants
    private final Image plasticPipeTop = new Image("res/level/plasticpipe.png");
    private final Image plasticPipeBottom = new Image("res/level/plasticpipe.png");
    private final Image metalPipeTop = new Image("res/level-1/steelpipe.png");
    private final Image metalPipeBottom = new Image("res/level-1/steelpipe.png");
    private final Image botFlame = new Image("res/level-1/flame.png");
    private final Image topFlame = new Image("res/level-1/flame.png");
    private final DrawOptions rotation = new DrawOptions();
    private Rectangle topFlameBox = topFlame.getBoundingBox();
    private Rectangle botFlameBox = botFlame.getBoundingBox();

    // Constants for the pipe gaps and fire
    private double stepSize = 5;     // 5 instead of 3, for FPS reasons
    private final int GAP = 168;
    private final int HIGH_GAP = 100;
    private final int MEDIUM_GAP = 300;
    private final int LOW_GAP = 500;
    private final double Y_BOT_HIGH = HIGH_GAP + GAP;
    private final double Y_TOP_HIGH = HIGH_GAP - plasticPipeTop.getHeight();
    private final double Y_BOT_MED = MEDIUM_GAP + GAP;
    private final double Y_TOP_MED = MEDIUM_GAP - plasticPipeTop.getHeight();
    private final double Y_BOT_LOW = LOW_GAP + GAP;
    private final double Y_TOP_LOW = LOW_GAP - plasticPipeTop.getHeight();
    private final double FLAME_HEIGHT = botFlame.getHeight();

    // Variables
    private double Y_BOT;
    private double Y_TOP;
    private double X = Window.getWidth();
    private boolean plastic = true;
    private int flameCount = 0;
    private boolean renderFlame = false;

    // Constants
    private final int FLAME_FREQ = 20;  // instead of 20 for fps reasons
    private final int NUM_HEIGHTS = 3;
    private final int NUM_PIPES = 2;
    private final int PIPE_WIDTH = 65;
    private final double FACTOR = 1.5;

    // Integers used for randomizing the heights of the gaps
    private Random rand = new Random();
    private Random randY = new Random();
    private double YLoc = randY.nextInt(LOW_GAP - HIGH_GAP) + HIGH_GAP;
    private int randInt = rand.nextInt(NUM_HEIGHTS);
    private int pipeInt = rand.nextInt(NUM_PIPES);

    /** Default constructor for pipe
     */
    public void Pipe() {}

    /** Getter for the current the stepsize. Taken from ChangeSpeed interface
     * @return int representing the step size for the pipes
     */
    public double getStepSize() {
        return stepSize;
    }

    /** Draw the pipes constantly moving left, according to the stepsize.
     * @param level takes the level of the current game
     */
    public void moveObject(int level) {
        Image top, bot;
        top = plasticPipeTop;
        bot = plasticPipeBottom;
        // Randomize the pipe type depending on the different kinds of levels
        if (level==1) {
            if (pipeInt==1) {
                top = plasticPipeTop;
                bot = plasticPipeBottom;
            } else {
                top = metalPipeTop;
                bot = metalPipeBottom;
                plastic = false;
            }
            Y_BOT = YLoc - metalPipeBottom.getHeight();
            Y_TOP = YLoc + GAP;
            top.drawFromTopLeft(X, Y_TOP, rotation.setRotation(Math.PI));
            bot.drawFromTopLeft(X, Y_BOT);
        }

        // Render the gaps depending on the height option assigned on lines 29-32
        else if (randInt==0) {
            bot.drawFromTopLeft(X, Y_BOT_HIGH, rotation.setRotation(Math.PI));
            top.drawFromTopLeft(X, Y_TOP_HIGH);
            Y_BOT = Y_BOT_HIGH;
            Y_TOP = Y_TOP_HIGH;
        } else if (randInt==1) {
            bot.drawFromTopLeft(X, Y_BOT_MED, rotation.setRotation(Math.PI));
            top.drawFromTopLeft(X, Y_TOP_MED);
            Y_TOP = Y_TOP_MED;
            Y_BOT = Y_BOT_MED;
        } else {
            bot.drawFromTopLeft(X, Y_BOT_LOW, rotation.setRotation(Math.PI));
            top.drawFromTopLeft(X, Y_TOP_LOW);
            Y_TOP = Y_TOP_LOW;
            Y_BOT = Y_BOT_LOW;
        }
        X -= stepSize;
    }

    /** Logic for flickering the flame. Taken from ChangeSpeed interface.
     * @param level current level of the game.
     * @param count takes into account the current frame count.
     */
    @Override
    public void flickerSprite(int level, double count) {
        if (pipeInt==0 && level==1 && renderFlame) {
            botFlame.drawFromTopLeft(X, Y_TOP - FLAME_HEIGHT, rotation.setRotation(Math.PI));
            topFlame.drawFromTopLeft(X, Y_TOP - GAP);
            botFlameBox = botFlame.getBoundingBoxAt(new Point(X+PIPE_WIDTH/2, Y_TOP-FLAME_HEIGHT/2));
            topFlameBox = topFlame.getBoundingBoxAt(new Point(X+PIPE_WIDTH/2, Y_TOP-GAP+FLAME_HEIGHT/2));
        }
        // Logic for the flickering
        if (count%FLAME_FREQ==0 && renderFlame) {
            renderFlame = false;
        } else if (count%FLAME_FREQ==0 && !renderFlame) {
            renderFlame = true;
        }
    }

    /** Setter for the stepSize of the pipes, according to the timescale level.
     * @param speedNum takes in the current timescale level
     */
    public void setSpeed(double speedNum) {
        stepSize = 5 * Math.pow(FACTOR, speedNum - 1);
    }

    /** Getter for rectangle of the top flame
     * @return Rectangle of the flame image.
     */
    public Rectangle getTopFlameBox() {
        return topFlameBox;
    }

    /** Getter for rectangle of the bottom flame
     * @return Rectangle of the flame image.
     */
    public Rectangle getBotFlameBox() {
        return botFlameBox;
    }

    /** Get rectangle of top pipe
     * @return Rectangle of the pipe image
     */
    public Rectangle getTopBox() {
        return new Rectangle(new Point(X, Y_TOP), plasticPipeTop.getWidth(), plasticPipeTop.getHeight());
    }

    /** Get rectangle of bottom pipe
     * @return Rectangle of the pipe image
     */
    public Rectangle getBottomBox() {
        return new Rectangle(new Point(X, Y_BOT), plasticPipeBottom.getWidth(), plasticPipeBottom.getHeight());
    }

    /** Get centre X value of the pipes
     * @return Double containing the central X value of the pipe
     */
    public double getX() {
        return (X + X + plasticPipeBottom.getWidth())/2;
    }

    /** Returns whether or not a pipe is plastic
     * @return boolean - true if it is plastic.
     */
    public boolean isPlastic() {
        return plastic;
    }

}
