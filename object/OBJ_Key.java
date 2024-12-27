package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.font.GlyphMetrics;
import java.io.IOException;
// 1 class = 1 OBJ
public class OBJ_Key extends  SuperObject{

    GamePanel gp;

    public OBJ_Key(GamePanel gp){

        this.gp = gp;

        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objimg/key.png"));
            UtilityTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}