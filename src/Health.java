import bagel.Image;

/** This represents the Health class, which renders the health of the bird
 * @author Ian Kaharudin
 */
public class Health {
    private final Image fullLife = new Image("res/level/fullLife.png");
    private final Image noLife = new Image("res/level/noLife.png");
    private final int INITX = 100;
    private final int INITY = 15;
    private final int GAP = 50;

    /** Render the full heart and empty
     * @param level This is the current level of the game.
     * @param lives This is the current number of lives of the player.
     */
    public void renderFullHearts(int level, int lives) {
        int lives1 = 6, maxLives = 3, i, j;
        if (level==1) {
            maxLives = lives1;
        }
            for (i=0; i<lives; i++) {
                fullLife.drawFromTopLeft(INITX+i*GAP, INITY);
            }
            for (j=lives; j< maxLives; j++) {
                noLife.drawFromTopLeft(INITX+j*GAP, INITY);
            }
    }

}
