package network.jsonUtils;

import gameLogic.board.Board;
import gameLogic.board.Spot;
import gameLogic.commands.playersAction.PlayCard;
import gameLogic.commands.playersAction.PlayersAction;
import gameLogic.invocator.card.Card;
import gameLogic.receptors.Player;
import network.Messages;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUtil {

  public String getJsonContent(String file) {
    StringBuilder sb = new StringBuilder();
    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));

      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append('\n');
      }
      fileInputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public PlayersAction getPlayerAction(Player player, String receivedMessage, Board board) {
    Card cardPlayed = null;
    Spot position = null;
    try {
      JSONObject jsonAction = new JSONObject(receivedMessage);
      String type = jsonAction.getString(Messages.JSON_TYPE);

      if (type.equals(Messages.JSON_TYPE_PLAY)) {
        int cardID = jsonAction.getInt(Messages.JSON_TYPE_CARD_ID);

        for (Card card : player.getHand()) {
          if (cardID == card.getID()) {
            cardPlayed = card;
            break;
          }
        }

        JSONObject pos = jsonAction.getJSONObject(Messages.JSON_TYPE_POSITION);
        position =
            board.getPosition(
                pos.getInt(Messages.JSON_TYPE_LINE), pos.getInt(Messages.JSON_TYPE_SPOT));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return new PlayCard(cardPlayed, position);
  }
}
