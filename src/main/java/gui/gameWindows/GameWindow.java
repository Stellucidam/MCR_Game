package gui.gameWindows;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public abstract class GameWindow {
  protected HBox navigation;
  protected BorderPane racine;
  protected boolean isGaming;
  protected Stage stage;

  public GameWindow(BorderPane racine, HBox navigation, boolean isGaming, Stage stage) {
    racine.setRight(null);
    racine.setLeft(null);
    racine.setTop(null);
    racine.setBottom(null);
    this.racine = racine;
    this.navigation = navigation;
    this.isGaming = isGaming;
    this.stage = stage;
    racine.setTop(this.navigation);
  }
}
