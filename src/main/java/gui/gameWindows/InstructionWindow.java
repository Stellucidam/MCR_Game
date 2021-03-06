package gui.gameWindows;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InstructionWindow extends GameWindow {
  private VBox body;

  public InstructionWindow(BorderPane racine, HBox navigation, boolean isGaming, Stage stage) {
    super(racine, navigation, isGaming, stage);
    body = new VBox();
    body.getStyleClass().add("instructions-body");
    generateBody();
  }

  private void generateBody() {
    Label instructionsTitle = new Label("Instructions");
    instructionsTitle.getStyleClass().add("instructions-title");

    String line, fullText = "";
    try (BufferedReader reader =
        new BufferedReader(new FileReader(new File("src/main/resources/utils/instructions.txt")))) {

      while ((line = reader.readLine()) != null) fullText += line + "\n";

    } catch (IOException e) {
      e.printStackTrace();
    }

    Text gameInstructions = new Text(fullText);
    gameInstructions.getStyleClass().add("instructions-text");
    gameInstructions.setWrappingWidth(900);

    VBox textBox = new VBox();
    textBox.setAlignment(Pos.CENTER);
    textBox.getChildren().add(gameInstructions);

    body.getChildren().addAll(instructionsTitle, textBox);
    body.setAlignment(Pos.CENTER);
    body.setSpacing(50);

    // ------------------------------------------------------------------
    // REGLAGES RACINE
    // ------------------------------------------------------------------

    racine.setCenter(body);
  }
}
