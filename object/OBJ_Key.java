package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;


// 1 class = 1 OBJ
public class OBJ_Key extends  SuperObject{



    public OBJ_Key(GamePanel gp){



        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objimg/key.png"));
            UTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}