package Common.GameBoard;

import Common.Receptors.Chest;
import Common.Receptors.Creature;
import Common.Receptors.Player;
import Server.Game.ModelClasses.Receptor;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.LinkedList;

/** Cette classe permet d'instancier les lignes composant le "plateau" de jeu. */
public class Line {

  // Le nombre de cases dans une ligne
  private final int NB_SPOTS = 12;

  // Le numéro de la ligne
  private int noLine;

  //Les cases composant la ligne
  private LinkedList<Spot> spots;

  //La liste de creatures
  private LinkedList<Receptor> receptors;

  //La liste de trésors
  private LinkedList<Chest> chests;

  // le groupe d'îlots qu'on affichera par la suite
  Group root;

  private static VBox vBox;
  private static GridPane gridPane;

  public Line(int noLine) {
    this.noLine = noLine;
  }

  /**
   * Constructeur de la classe Line
   *
   * @param noLine : le numéro de la ligne
   */
  public Line(int noLine, GridPane gridPane, VBox vbox, Player player1, Player player2) throws IOException {
    this.noLine = noLine;
    receptors = new LinkedList<>(); // on initialise la liste de créatures.
    spots = new LinkedList<>(); // on initialise la liste de spots.
    chests = new LinkedList<>(); //on initialise la liste de chess.

    this.vBox = vbox;
    this.gridPane = gridPane;

    int indexCreature = 0;
    for (int spot = 0; spot < NB_SPOTS; ++spot) {
      vbox = new VBox(); // On créé une box verticale pour aligne la créature à l'île.
      spots.add(new Spot());
      if(spot == 0)
      {
        chests.add(new Chest("",player1));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren()
                .addAll((chests.get(0).getImageView()), (spots.get(spot).getImageView()));
        gridPane.add(vbox, spot, noLine);
      }
      else if(spot == 11) {
        chests.add(new Chest("",player2));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren()
                .addAll((chests.get(1).getImageView()), (spots.get(spot).getImageView()));
        gridPane.add(vbox, spot, noLine);
      }
      else {
        receptors.add(new Creature("unknown", 0, 0, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren()
                .addAll((receptors.get(indexCreature++).getImageView()), (spots.get(spot).getImageView()));
        gridPane.add(vbox, spot, noLine);
      }

    }
  }

  public void setReceptor(Receptor receptor, int spot) {
    // TODO demander à Elodie de remplacer la créature courante par la creature donnée
    System.out.println(receptors.get(spot).getImageView().imageProperty().toString());
    System.out.println(receptor.getImageView().imageProperty().toString());
    receptors.get(spot).setTo(receptor);
    System.out.println(receptor.getImageView().imageProperty().toString());

    //vBox.getChildren().set(spot, receptor.getImageView());
    //gridPane.getChildren().set(noLine, vBox);

    ObservableList<Node> childrens = gridPane.getChildren();

    for (Node node : childrens) {
      if(gridPane.getRowIndex(node) == noLine && gridPane.getColumnIndex(node) == spot) {
        System.out.println("Avant");
        System.out.println((((VBox)node).getChildren()));

        ((VBox)node).getChildren().set(0, receptor.getImageView());

        System.out.println("Apres");
        System.out.println(((VBox)node).getChildren());
        break;
      }
    }
    //gridPane.getChildren().clear();
  }

  /**
   * Permet de récupérer le nombre de cases.
   *
   * @return le nombre de cases de la ligne.
   */
  public int getNB_SPOTS() {
    return NB_SPOTS;
  }

  public Spot getSpot(int index) {
    if (spots == null)
      return null;
    return spots.get(index);
  }

  /**
   * Permet de récupérer la liste des cases de la ligne.
   *
   * @return la liste des cases de la ligne.
   */
  public LinkedList<Spot> getSpots() {
    return spots;
  }

  /**
   * Permet de récupérer le numéro de la ligne.
   *
   * @return le numéro de la ligne.
   */
  public int getNoLine() {
    return noLine;
  }
}
