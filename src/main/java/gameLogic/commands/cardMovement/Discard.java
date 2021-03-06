package gameLogic.commands.cardMovement;

import gameLogic.commands.CommandName;
import gameLogic.receptors.Player;
import network.states.ServerSharedState;

public class Discard extends CardMovement {
  public Discard() {
    super(CommandName.DISCARD);
  }

  @Override
  public void execute(Player player, ServerSharedState serverSharedState) {
    player.discardCard(card);
  }

  @Override
  public void undo(Player player, ServerSharedState serverSharedState) {
    player.giveCard(card);
  }
}
