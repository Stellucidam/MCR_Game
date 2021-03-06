package gui.board;

import gui.receptors.GUIPlayer;
import gui.receptors.GUIReceptor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Cette classe représente le "plateau" où se déplacent les différentes créatures lors d'une partie.
 * Une plateau comporte 4 lignes.
 */
public class GUIBoard {

  // TODO : rentre paramétrique.
  // le nombre de lignes
  public static final int NB_LINES = 4;

  // compteur de ligne
  private static int lineCounter;

  // les lignes du board
  private LinkedList<GUILine> guiLines;

  /** Constructeur de la classe GUIBoard */
  public GUIBoard(GridPane gridPane, VBox vbox, GUIPlayer player1, GUIPlayer player2)
      throws IOException {
    lineCounter = 0;
    guiLines = new LinkedList<>();
    for (int line = 0; line < NB_LINES; ++line) {
      guiLines.add(
          new GUILine(
              lineCounter++, gridPane, vbox, player1, player2, player1.getClientSharedState()));
    }
  }

  public void place(GUIReceptor receptor, int lineCounter, int spot) {
    guiLines.get(lineCounter).setReceptor(receptor, spot);
    guiLines.get(lineCounter).getSpot(spot).setOccupant(receptor);
  }

  public GUILine getLine(int index) {
    if (index < guiLines.size()) {
      return guiLines.get(index);
    }
    return null;
  }

  /**
   * Permet de récupérer la liste des lignes du board.
   *
   * @return la liste des lignes du board.
   */
  public LinkedList<GUILine> getGuiLines() {
    return guiLines;
  }

  /**
   * Permet de savoir le nombre de lignes constituant un board.
   *
   * @return le nombre de lignes constituant un board.
   */
  public int getNB_LINES() {
    return NB_LINES;
  }

  public void placeTrap(int line, int position) {
    guiLines.get(line).getSpot(position).trap();
  }

  public void removeTrap(int line, int position) {
    guiLines.get(line).getSpot(position).unTrap();
  }

  public GUISpot getSpot(int line, int spot) {
    return guiLines.get(line).getSpot(spot);
  }

  public GUILine getLineAt(int number) {
    return guiLines.get(number);
  }

  public GridPane getGridIslandPanel(int line) {
    return guiLines.get(line).getGridIslandPanel();
  }

  public ArrayList<GridPane> getGridIslandPanels() {
    ArrayList<GridPane> gridPanes = new ArrayList<>();
    for (GUILine line : guiLines) {
        gridPanes.add(line.getGridIslandPanel());
    }
    return gridPanes;
  }
}
