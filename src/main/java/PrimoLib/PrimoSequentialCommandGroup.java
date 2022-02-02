package PrimoLib;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PrimoSequentialCommandGroup extends PrimoCommandGroupBase  {

    public PrimoSequentialCommandGroup(Command... commands) {
        super(new SequentialCommandGroup(), commands);
    }
}
