package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.CommandName;

public class Retreat extends MoveCreature {
    public Retreat() {
        super(CommandName.RETREAT_CREATURE);
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).retreat(((Creature)receptors[0]).getSteps());
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).advance();
    }
}