package object;

import javax.imageio.ImageIO;
import java.io.IOException;
// 1 class = 1 OBJ
public class OBJ_Key extends  SuperObject{
    public OBJ_Key(){
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objimg/key.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
