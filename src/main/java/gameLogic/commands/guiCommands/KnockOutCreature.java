package gameLogic.commands.guiCommands;

import gameLogic.commands.CommandName;
import gui.GameBoard;
import gui.board.GUISpot;
import gui.receptors.GUICreature;
import network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

public class KnockOutCreature extends GuiCommand {
  private GUISpot position;

  public KnockOutCreature() {
    super(CommandName.KNOCK_OUT_CREATURE);
  }

  public void setPosition(GUISpot position) {
    this.position = position;
  }

  @Override
  public JSONObject toJson() {
    JSONObject knockout = super.toJson();

    try {
      knockout.put(Messages.JSON_TYPE_POSITION, position.toJson());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return knockout;
  }

  @Override
  public void execute(GameBoard gameBoard) {
    ((GUICreature)
            gameBoard
                .getGuiBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant())
        .knockOut();
  }

  @Override
  public void undo(GameBoard gameBoard) {
    ((GUICreature)
            gameBoard
                .getGuiBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant())
        .wakeUp();
  }
}
