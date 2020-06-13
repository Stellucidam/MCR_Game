package Server.Game.ModelClasses.Commands.OnLiveReceptors.OnCreature;

import Common.Receptors.Creature;
import Server.Game.ModelClasses.Commands.CommandName;

public class ChangeMovementsPoints extends OnCreature {
    private int newMP;
    private int oldMP;

    public ChangeMovementsPoints() {
        super(CommandName.CHANGE_MP);
    }

    public void setNewMP(int newMP) {
        this.newMP = newMP;
    }

    @Override
    public void execute() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).setMovementsPoints(newMP);
    }

    public void setMovementsPoints(int mp) {
        oldMP = mp;
    }

    @Override
    public void undo() {
        if (receptors == null)
            return;
        for (int i = 0; i < receptors.length; i++)
            ((Creature)receptors[i]).wakeUp();
    }
}