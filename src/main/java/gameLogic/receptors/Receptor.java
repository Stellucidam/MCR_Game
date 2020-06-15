package gameLogic.receptors;

import gameLogic.commands.Command;
import gameLogic.commands.ConcreteCommand;
import gameLogic.commands.playersAction.PlayersAction;
import network.Messages;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Receptor {
    protected Command lastMove;
    protected String name;

    private String imgPath = "src/main/resources/design/images/creatures/empty.jpg";
    private FileInputStream fis;

    private Image image;
    private ImageView imageView;

    public Receptor(String name){
        this.name = name;
        initDisplay();
    }

    public Receptor() {
        this.name = "empty";
        initDisplay();
    }

    public String getName() {
        return name;
    }

    public abstract void playTurn(int turn, PlayersAction action);

    public void undoLastMove() {
        lastMove.undo(((ConcreteCommand)lastMove).getReceptor());
    }

    public void redoLastMove(){
        lastMove.execute(((ConcreteCommand)lastMove).getReceptor());
    }

    @Override
    public String toString() {
        return name;
    }

    private void initDisplay() {
        try {
            image = new Image(new FileInputStream(imgPath));
            imageView = new ImageView(image);
            imageView.setFitWidth(image.getWidth() * 0.2);
            imageView.setFitHeight(image.getHeight() * 0.2);
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image getImage() {
        return image;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
        initDisplay();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setTo(Receptor receptor) {
        System.out.println("I am a new receptor");
        if(receptor == null) {
            try {
                fis = new FileInputStream("src/main/resources/design/images/creatures/empty.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            initDisplay();
        } else {
            fis = receptor.fis;
            image = receptor.image;
            imageView = receptor.imageView;
        }
    }

    public JSONObject toJson() {
        JSONObject receptor = new JSONObject();
        try {
            receptor.put(Messages.JSON_TYPE_NAME, name);
            receptor.put(Messages.JSON_TYPE_IMAGE, imgPath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return receptor;
    }
}