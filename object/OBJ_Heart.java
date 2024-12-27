package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;

    public OBJ_Heart(GamePanel gp){

        this.gp = gp;

        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objimg/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/images/objimg/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/images/objimg/heart_blank .png"));
            UtilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
            UtilityTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            UtilityTool.scaleImage(image3, gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}