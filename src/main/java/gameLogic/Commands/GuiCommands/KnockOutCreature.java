package gameLogic.commands.guiCommands;

import gui.GameBoard;
import gameLogic.board.Spot;
import gameLogic.commands.CommandName;
import gameLogic.receptors.LiveReceptor;
import network.Messages;

import org.json.JSONException;
import org.json.JSONObject;

public class KnockOutCreature extends GuiCommand {
    private Spot position;

    public KnockOutCreature() {
        super(CommandName.KNOCK_OUT_CREATURE);
    }

    public void setPosition(Spot position) {
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
        // Todo : execution on the gui
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();
    }

    @Override
    public void undo(GameBoard gameBoard) {
        // Todo : undo on the gui
        LiveReceptor receptor = (LiveReceptor)gameBoard
                .getGUIBoard()
                .getLine(position.getLineNumber())
                .getSpot(position.getSpotNumber())
                .getOccupant();
    }
}