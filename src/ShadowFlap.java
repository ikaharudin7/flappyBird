import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.ceil;

/**
 * Class for ShadowFlap
 * Please fill in your name below
 * @author: 'Ian Kaharudin'
 */
public class ShadowFlap extends AbstractGame {
    // Variables for screens
    /** Constant strings and images to display some screens and their messages
    */
    private final Image background;
    private final Image nightBackground;
    private final Font font = new Font("res/font/slkscr.ttf", 48);
    private final String BEGINNING = "PRESS SPACE TO START";
    private final String SHOOT = "PRESS 'S' TO SHOOT";

    /** Game objects, to be used in the game
     */
    // Game elements
    private final Bird bird = new Bird();
    private Pipe pipes = new Pipe();
    private Score score = new Score();
    private final Collision collision = new Collision();
    private final Win win = new Win();
    private final LevelUp levelUp = new LevelUp();
    private final Health health = new Health();
    private Rectangle birdBox;
    private Rectangle topPipeBox;
    private Rectangle bottomPipeBox;
    private Weapon heldItem = null;
    private Timescale timescale = new Timescale();
    ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
    ArrayList<Weapon> weaponList = new ArrayList<Weapon>();

    /** Variables to keep track of game states, and object states, and frame count
     */
    // Variables and game state
    private int level = 0;
    private int lives = 3;
    private double frameCount = 0;
    private double count = 0;
    private int L2_LIVES = 6;
    private double yLaunch;
    private Random rand = new Random();
    private int weaponType;
    private boolean leveledUp = false;
    private boolean pressSpace = true;
    private boolean gotItem = false;
    private boolean thrown = false;
    private boolean beingThrown = false;
    private int weaponRange = 0;
    private double pipeFreq = 120;      // 120 instead of 100 for FPS reasons
    private double weaponFreq = 280;
    private double weaponStagger = 60;
    private double generalCount = 0;

    /** Constants storing values for levelling up, timescale, and weapon distance
     */
    private final int LEVELUP_LENGTH = 100;      // 100 instead of 20 for FPS reason
    private final int UPGRADE = 10; // Score to level up
    private final int WINNING = 30; // Winning score
    private final int ROCK_DIST = 25;
    private final int BOMB_DIST = 50;
    private final int NUM_WEAPONS = 2;
    private final double FACTOR = 1.5;

    /** Default constructor for the game
     */
    public ShadowFlap() {
        super(1024, 768, "Shadow Flap");
        background = new Image("res/level-0/background.png");
        nightBackground = new Image("res/level-1/background.png");
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (!leveledUp) {
            background.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }
        if (leveledUp) {
            nightBackground.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            if (level==2) {
                win.projectScreen(score.getScore());
            }
            if (input.wasPressed(Keys.ESCAPE) && level==2) {
                Window.close();
            }
        }


        // This segment contains logic for which starting screens should spawn.
        if (pressSpace) {
            // Level 0  logic
            if (level==0) {
                font.drawString(BEGINNING, (Window.getWidth() - font.getWidth(BEGINNING)) / 2,
                                Window.getHeight() / 2.0);
                if (input.wasPressed(Keys.SPACE)) {
                    pressSpace = false;
                }
            } // Level 1 Screen
            else if (level==1) {
                // Display the level up screen
                if (count<LEVELUP_LENGTH) {
                    levelUp.projectScreen(score.getScore());
                    count++;
                } // Display the level1 starting screen.
                else {
                    leveledUp = true;
                    font.drawString(BEGINNING, (Window.getWidth() - font.getWidth(BEGINNING)) / 2,
                            Window.getHeight() / 2.0);
                    font.drawString(SHOOT, (Window.getWidth() - font.getWidth(SHOOT)) / 2.0,
                            Window.getHeight() / 2.0 + win.GAP);
                    if (input.wasPressed(Keys.SPACE)) {
                        pressSpace = false;
                    }
                }
            }
        }


        // If the score is 10 and the level is 0, change the game state variables to level1.
        if (score.getScore()==UPGRADE && level==0 || score.getScore()==WINNING) {
            if (level==0) {
                level = 1;
                frameCount = 0;
                lives = L2_LIVES;
                score.setScore();
                pressSpace = true;
                pipeList.clear();
                bird.setPlayerLoc();

                // Reset timescale to original values above^^
                timescale.setTimeScale();
                pipeFreq = 100;
                weaponFreq = 300;
                weaponStagger = 60;
            } else {
                pressSpace = false;
                level = 2;
            }
        }


        // When space is pressed and game play is beginning
        else if (!pressSpace && !collision.isDead()) {    // 65 is the pipe width
            // Physics of bird falling and flying
            bird.flickerSprite(level, frameCount);
            bird.updateFall();
            if (input.wasPressed(Keys.SPACE)) {
                bird.moveObject(level);
            }

            // Render the pipes in the ArrayList every 100 frames and delete the pipes once they go out the screen
            if (frameCount%pipeFreq==0 || pipeList.size()==0) {
                pipeList.add(new Pipe());
                frameCount = 0;
            }

            for (Pipe pipe : pipeList) {
                pipe.moveObject(level);
                pipe.flickerSprite(level, generalCount);
                pipe.setSpeed(timescale.getSpeedNum());     // Apply timescale
            }

            pipes = pipeList.get(0);
            if (pipes.getX()<0) {
                pipeList.remove(0);
            }


            // Create rectangles for collision detection
            birdBox = bird.getbirdBox(bird.getPlayerLoc());
            topPipeBox = pipes.getTopBox();
            bottomPipeBox = pipes.getBottomBox();


            // Render the weapons every 300 frames with same logic as pipes
            if (frameCount%pipeFreq==pipeFreq/2 && level==1) {

                // Check weapon types with randomizer
                weaponType = rand.nextInt(NUM_WEAPONS);
                if (weaponType==0) {
                    weaponList.add(new Bomb());
                }
                else {
                    weaponList.add(new Rock());
                }
                weaponList.get(weaponList.size() - 1).setLocation();
            }


            // Check if weapon is picked up or not, and whether or not it can be picked up
            for (int i=0; i<weaponList.size(); i++) {
                if (weaponList.get(i).getThrown()) {
                    continue;
                }
                weaponList.get(i).setSpeed(timescale.getSpeedNum());
                // If the bird is able to collect an item and it has not been thrown
                if (weaponList.get(i).checkPickedUp(birdBox, gotItem) && !gotItem && !weaponList.get(i).getThrown()) {
                    gotItem = true;
                    heldItem = weaponList.get(i);
                }
                // If weapon is not collected yet
                else if (!weaponList.get(i).isHeld()) {
                    weaponList.get(i).moveObject(level);
                }

                // Delete the weapon if it's moved past the screen already
                if (weaponList.size()>0 && weaponList.get(i).getX()<0) {
                    weaponList.remove(i);
                    i--;
                }
            }


            // When the bird is holding item, it follows the bird's location, unless if thrown
            if (gotItem && heldItem!=null) {
                if (input.wasPressed(Keys.S) && !thrown) {
                    thrown = true;
                    beingThrown = true;
                    heldItem.setThrown();
                    yLaunch = bird.getPlayerLoc().y;
                    heldItem.setXLoc(birdBox.right());
                    weaponRange = 0;
                }

                // If item has not yet been thrown
                else if (!heldItem.getThrown()) {
                    heldItem.setLocation(bird.getPlayerLoc());
                    heldItem.renderWeapon(new Point(birdBox.right(), bird.getPlayerLoc().y));
                }

                // Logic for rendering item once it has been thrown.
                else if (heldItem.getThrown()) {
                    heldItem.throwable(yLaunch);

                    // Check for collision detection between pipe and weapon
                    if (heldItem.getWeaponBox().intersects(bottomPipeBox)
                            || heldItem.getWeaponBox().intersects(topPipeBox)) {
                        // Rocks can only hit
                        if ((heldItem instanceof Rock && pipes.isPlastic()) || heldItem instanceof Bomb) {
                            score.incrementScore();
                            pipeList.remove(0);
                        }
                        weaponList.remove(heldItem);
                        heldItem = null;
                        gotItem = false;
                        thrown = false;
                    }
                    // Delete weapon once it has passed its distance, depending on whether bomb or rock
                    else if (heldItem instanceof Bomb && weaponRange==BOMB_DIST) {
                        weaponList.remove(heldItem);
                        heldItem = null;
                        gotItem = false;
                        beingThrown = true;
                        thrown = false;
                        weaponRange = 0;
                    } // Apply logic for the rock
                    else if (heldItem instanceof Rock && weaponRange==ROCK_DIST) {
                        weaponList.remove(heldItem);
                        heldItem = null;
                        gotItem = false;
                        beingThrown = true;
                        thrown = false;
                        weaponRange = 0;
                    }
                }
            }
            frameCount++;
            weaponRange++;
            generalCount++;
            score.renderScore(birdBox.centre().x, topPipeBox.right(), pipes.getStepSize()); // Render the score


            // if there was an intersection, or if it crossed the upper and lower boundaries, OR TOUCHED FIRE
            if (birdBox.intersects(topPipeBox) | birdBox.intersects(bottomPipeBox) | bird.getPlayerLoc().y<=0 |
                    bird.getPlayerLoc().y>=Window.getHeight() || birdBox.intersects(pipes.getBotFlameBox()) ||
                    birdBox.intersects(pipes.getTopFlameBox())) {
                lives--;
                // If out of bounds, respawn bird and set speed to 0
                if (bird.getPlayerLoc().y<=0 | bird.getPlayerLoc().y>=Window.getHeight()) {
                    bird.setPlayerLoc();
                }
                else {
                    pipeList.remove(0);
                }
                // Check if there are any lives left
                if (lives==0) {
                    collision.setCrash();
                }
            }

            // Adjust timescale, depending on whether L or K is pressed.
            if (input.wasPressed(Keys.L)) {
                if (timescale.getSpeedNum()<5) {
                    pipeFreq = ceil(pipeFreq / FACTOR);
                    weaponFreq = ceil(weaponFreq / FACTOR);
                    weaponStagger = ceil(weaponStagger / FACTOR);
                }
                timescale.increaseSpeed();
            } else if (input.wasPressed(Keys.K)) {
                if (timescale.getSpeedNum()>1) {
                    pipeFreq = ceil(pipeFreq * FACTOR);
                    weaponFreq = ceil(weaponFreq * FACTOR);
                    weaponStagger = ceil(weaponStagger * FACTOR);

                }
                timescale.decreaseSpeed();
            }

            health.renderFullHearts(level, lives);
        }


        // If all lives are lost, project death screen
        else if (collision.isDead()) {
            collision.projectScreen(score.getScore());
        }
    }
}
