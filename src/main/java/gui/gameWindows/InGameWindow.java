package gui.gameWindows;

import gui.board.GUIBoard;
import gui.receptors.GUICard;
import gui.receptors.GUIPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import network.states.ClientSharedState;

import java.io.IOException;
import java.util.ArrayList;

public class InGameWindow extends GameWindow {
  private GUIBoard guiBoard;
  private GUIPlayer player1, player2;
  private ArrayList<GUICard> handPlayer;
  private ClientSharedState clientSharedState;
  private ToggleGroup groupButtons;

  public InGameWindow(
      BorderPane racine,
      HBox navigation,
      GUIBoard guiBoard,
      GUIPlayer player1,
      GUIPlayer player2,
      boolean isGaming,
      ClientSharedState clientSharedState,
      Stage stage,
      ArrayList<GUICard> handPlayer)
      throws IOException {
    super(racine, navigation, isGaming, stage);
    this.guiBoard = guiBoard;
    this.player1 = player1;
    this.player2 = player2;
    this.handPlayer = handPlayer;
    this.clientSharedState = clientSharedState;
    groupButtons = new ToggleGroup();

    generateBody();
  }

  public void generateBody() throws IOException {

    if (clientSharedState.isPlayerTurn(player1.getName())) {
      // On créé l'espace d'infos du joueur 1
      racine.setLeft(getPlayerInformations(player1.getName()));

      // On créé l'espace d'infos du joueur 2
      racine.setRight(getPlayerInformations(player2.getName()));
    } else {
      // On créé l'espace d'infos du joueur 1
      racine.setLeft(getPlayerInformations(player2.getName()));

      // On créé l'espace d'infos du joueur 2
      racine.setRight(getPlayerInformations(player1.getName()));
    }
    // On met en place le corps du texte
    racine.setCenter(displayInGameField());

    // On crée un footer dans le BorderPane
    racine.setBottom(footerBar());
  }

  /**
   * Affiche le terrain où se déplace les créatures.
   *
   * @return le gridPane sur lequel se déplace les créatures.
   * @throws IOException
   */
  private Group displayInGameField() throws IOException {
    VBox corpsInstruction = new VBox(); // contient les lignes du board.
    corpsInstruction.getStyleClass().add("instructions-body");

    ArrayList<GridPane> gridIslandsPanel = guiBoard.getGridIslandPanels();

    for (GridPane gridPane : gridIslandsPanel ) {
      gridPane.getStyleClass().add("corps-gridPane");
      gridPane.setAlignment(Pos.CENTER);
    }

    Group group = new Group();
    for (int i = 0; i < gridIslandsPanel.size(); i++) {
      if (!group.getChildren().contains(gridIslandsPanel.get(i))) {
        group.getChildren().add(gridIslandsPanel.get(i));
      }
    }

    return group;
  }

  /** @return les informations du Player */
  private VBox getPlayerInformations(String labelTitle) {
    VBox informationPannelUser = new VBox();

    Label isPlaying = new Label("- Playing now -");
    isPlaying.getStyleClass().add("isPlaying-label");
    informationPannelUser.getChildren().add(isPlaying);
    if (!(clientSharedState.isPlayerTurn(labelTitle))) {
      isPlaying.setText("");
    }

    // on set l'image de player 1
    if (labelTitle.equals(player1.getName())) {
      ImageView imageView = new ImageView(player1.getImage());
      imageView.setFitWidth(player1.getImage().getWidth() / 2.5);
      imageView.setFitHeight(player1.getImage().getHeight() / 2.5);
      informationPannelUser.getChildren().add(imageView);
    }

    // on set l'image de player 2 en checkant si elle est pas égale à celle de base.
    // TODO remplace image 2 en reprenant info serveur
    else if (player2.getImage() != null) {
      ImageView imageView = new ImageView(player2.getImage());
      imageView.setFitWidth(player2.getImage().getWidth() / 2.5);
      imageView.setFitHeight(player2.getImage().getHeight() / 2.5);
      informationPannelUser.getChildren().add(imageView);
    }

    // On l'affiche
    informationPannelUser.getStyleClass().add("menuLabelsGauche-vbox");
    informationPannelUser.setAlignment(Pos.CENTER);

    Label informationPanelUserTitle = new Label(labelTitle);
    informationPanelUserTitle.getStyleClass().add("titre-label");
    informationPannelUser.getChildren().add(informationPanelUserTitle);

    //TODO afficher les points d'action
    Label pointActionTitle = new Label("Points d'action : 15");

    informationPannelUser.getChildren().add(pointActionTitle);

    return informationPannelUser;
  }
  /**
   * Footer Il s'agit de la fonction initialisant tout ce qui est inhérent au footer. Dans ce
   * footer, nous affichons les cartes du joueur.
   *
   * @return le footer
   */
  private HBox footerBar() {

    // On définit une boxe horizontale qui définira l'espace "footer" -> cartes du joueur
    HBox footerCardsPlayer = new HBox();
    footerCardsPlayer.setPadding(new Insets(15, 15, 15, 15));
    footerCardsPlayer.getStyleClass().add("footer-header-hbox");

    for (GUICard card : player1.getHand()) {
      footerCardsPlayer.getChildren().add(card.getButton());
      card.getButton()
          .selectedProperty()
          .addListener(
              (observable, oldValue, newValue) -> {

                // If selected, color the background cyan
                if (newValue) {
                  card.getButton().getStyleClass().add("toggle-selected");
                  clientSharedState.setSelectedCard(card);
                } else {
                  card.getButton().getStyleClass().add("toggle-unselected");
                  clientSharedState.setSelectedCard(null);
                }
              });
      groupButtons.getToggles().clear();
      groupButtons.getToggles().add(card.getButton());

    }

    footerCardsPlayer.setVisible(true);
    footerCardsPlayer.getStyleClass().add("corps-gridPane");

    return footerCardsPlayer;
  }
}
