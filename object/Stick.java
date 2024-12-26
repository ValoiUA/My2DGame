package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Stick extends SuperObject {
    public Stick() {
        name = "Stick";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objimg/Stick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
