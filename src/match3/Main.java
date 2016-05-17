package match3;

/**
 * Created by kyle on 5/17/16.
 */
public class Main {
    public static Graphics graphics;
    public static void main(String[] args) {
        graphics = new Graphics();
        new Thread(graphics).start();
    }
}
