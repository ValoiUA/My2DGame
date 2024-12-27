package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
// 1 class = 1 OBJ
public class OBJ_Key extends  SuperObject{

    GamePanel gp;

    public OBJ_Key(){

        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objimg/key.png"));
            uTool.scaleImage(image,gp)
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
