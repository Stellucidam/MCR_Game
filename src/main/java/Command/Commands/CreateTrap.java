package Command.Commands;

import Command.Command;
import GameBoard.Spot;
import Receptors.Trap;

public class CreateTrap implements Command {
    private Spot position;
    private Trap trap;

    public CreateTrap(Trap trap, Spot position){
        this.trap = trap;
        this.position = position;
    }

    @Override
    public void execute() {
        trap.setPosition(position);
    }

    @Override
    public void undo() {
        trap.setPosition(null);
    }
}
