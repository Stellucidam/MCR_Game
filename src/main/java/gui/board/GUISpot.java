package gui.board;

import gui.maths.Vector2f;
import gui.receptors.GUIReceptor;
import gui.receptors.GUITrap;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.util.Pair;
import network.states.ClientSharedState;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** Cette classe représente une case qui constitue une ligne de combat */
public class GUISpot {

  // un case est représentée par un numéro
  private final int number;
  private final int lineNumber;

  // Compteur de case
  private static int spotCounter = 0;

  private ClientSharedState clientSharedState;

  // l'éventuelle créature présente sur la case
  private GUIReceptor occupant;

  // permet de savoir si une île est piégée
  private boolean isTrapped;

  // l'image représentant le spot
  private FileInputStream imagePath =
      new FileInputStream("src/main/resources/design/images/field/island.png");
  Image image;
  ImageView imageView;
  Button button;

  // la position du spot dans l'espace
  protected Vector2f pos;

  private static float STARTING_COORDINATE_X =
      (float) Screen.getPrimary().getVisualBounds().getWidth() / 4;
  private static float STARTING_COORDINATE_Y =
      (float) Screen.getPrimary().getVisualBounds().getHeight() / 6;
  private static float MIN_WIDTH_RATIO = 0.6f;

  /**
   * Permet de construire un îlot
   *
   * @throws IOException
   */
  public GUISpot(int line, ClientSharedState clientSharedState) throws IOException {
    this(
        spotCounter++, line,
        new Vector2f(STARTING_COORDINATE_X, STARTING_COORDINATE_Y),
        clientSharedState);
  }

  /**
   * Permet de construire un ilôt avec une position et un nombre le définissant
   *
   * @param number : allant de 0 à 9
   * @param pos : sa position dans la fenêtre du jeu
   * @throws FileNotFoundException
   */
  private GUISpot(int number, int line, Vector2f pos, ClientSharedState clientSharedState)
      throws FileNotFoundException {
    image = new Image(imagePath);
    imageView = new ImageView(image);
    this.clientSharedState = clientSharedState;
    isTrapped = false;
    button = new Button();
    button.getStyleClass().add("button-island");
    button.setOnAction(
        actionEvent -> {
          if (!clientSharedState.isMyTurn()) {
            // TODO: Afficher une alerte sur le GUI
            // on créé une alerte WARNING qui indique à l'utilisateur
            // que ce n'est pas à lui de jouer.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Ce n'est pas à votre tour de jouer !");
            Image image = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/4/42/Emojione_1F62D.svg/64px-Emojione_1F62D.svg.png");
            ImageView imageView = new ImageView(image);
            alert.setGraphic(imageView);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane
                    .getStylesheets()
                    .add(getClass().getResource("/design/css/styleSheet.css").toExternalForm());
            alert.show();

            System.out.println("Please wait for your turn");
            return;
          } else {
            // If there is a selected card and it's not an empty card
            if (clientSharedState.getSelectedCard() != null) {

              clientSharedState.setChosenPosition(
                  new Pair<>(line, number % GUILine.NB_SPOTS));

              try {
                JSONObject json = clientSharedState.getSelectedCard().getJson();
                clientSharedState.pushJsonToSend(json);
                clientSharedState.setIntendToSendJson(true);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          }
        });
    button.setGraphic(imageView);
    this.number = number % (GUILine.NB_SPOTS);
    this.lineNumber = line;
    this.pos = pos;
    initDisplaySpot();
  }

  /** permet d'initialiser correctement la place d'un îlot */
  private void initDisplaySpot() {
    imageView.setFitWidth(image.getWidth() * MIN_WIDTH_RATIO);
    imageView.setFitHeight(image.getHeight() * MIN_WIDTH_RATIO);
  }

  /**
   * Permet de savoir si une case est occupée par une créature.
   *
   * @return true si occupée, false sinon.
   */
  public boolean isEmpty() {
    return occupant == null || occupant.getName().equals("empty") || occupant.getClass() == GUITrap.class;
  }

  /**
   * Permet de set l'éventuelle créature présente sur la case.
   *
   * @param occupant : la créature
   */
  public void setOccupant(GUIReceptor occupant) {
    this.occupant = occupant;
  }

  /** Modélise le départ d'une créature de la case. */
  public void leave() {
    this.occupant = null;
  }

  /**
   * Permet de récupérer l'éventuelle créature présente sur la case.
   *
   * @return la créature, si elle occupe la case. Sinon null.
   */
  public GUIReceptor getOccupant() {
    return occupant;
  }

  /** @return l'image d'un spot (ici une petite île) */
  public FileInputStream getImagePath() {
    return imagePath;
  }

  public ImageView getImageView() {
    return imageView;
  }

  public Button getButton() {
    return button;
  }

  public boolean isTrapped() {
    return isTrapped;
  }

  public void trap() {
    isTrapped = true;
  }

  public void unTrap() {
    isTrapped = false;
  }

  public JSONObject toJson() {
    // TODO
    return null;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getSpotNumber() {
    return (number % GUILine.NB_SPOTS);
  }
}
