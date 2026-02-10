import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class TextureCache {
    private HashMap<String, Image> map = new HashMap<String, Image>();
    
    public Image getImg(String path) {
        if(map.containsKey(path)) {
            return map.get(path);
        }

        Image img = null;
        try {
            img = ImageIO.read(new File(path));
            map.put(path, img);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
