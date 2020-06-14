package gui.gameWindows;

import gui.buttons.GameButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.InetAddress;

public class ParameterWindow extends GameWindow {
  private VBox body;
  private TextField playerNameField, playerIpField, playerPortField;

  public ParameterWindow(BorderPane racine, HBox navigation, Stage stage, boolean isGaming) {
    super(racine, navigation, isGaming, stage);
    body = new VBox(10);
    this.stage = stage;
    body.getStyleClass().add("parameters-body");
    generateBody();
  }

  private void generateBody() {
    body.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

    // Titre : Settings
    Label settingsTitle = new Label("Settings");
    settingsTitle.getStyleClass().add("instructions-title");

    // Nom du joueur (Bouffon n°1 si pas changé :D)
    Label playerName = new Label("Nom");
    playerName.getStyleClass().add("parameters-label");

    playerNameField = new TextField();
    playerNameField.setText("Bouffon n°1");
    playerNameField.getStyleClass().add("parameters-field");

    // Adresse IP du joueur
    Label playerIP = new Label("Adresse IP");
    playerIP.getStyleClass().add("parameters-label");

    playerIpField = new TextField();
    playerIpField.setText(
        String.valueOf(InetAddress.getLoopbackAddress())); // récupère l'adresse IP
    playerIpField.getStyleClass().add("parameters-field");

    // Port du joueur
    Label playerPort = new Label("Port");
    playerPort.getStyleClass().add("parameters-label");

    playerPortField = new TextField();
    playerPortField.setText("8080"); // récupère l'adresse IP
    playerPortField.getStyleClass().add("parameters-field");

    body.getChildren()
        .addAll(
            settingsTitle,
            playerName,
            playerNameField,
            playerIP,
            playerIpField,
            playerPort,
            playerPortField);
    body.setAlignment(Pos.CENTER);
    body.setSpacing(50); // espace entre les éléments
  }

  public void addGameButton(GameButton button) {
    body.getChildren().add(button.getButton());
  }

  public TextField getPlayerNameField() {
    return playerNameField;
  }

  public TextField getPlayerIpField() {
    return playerIpField;
  }

  public TextField getPlayerPortField() {
    return playerPortField;
  }

  public VBox getBody() {
    return body;
  }
}
