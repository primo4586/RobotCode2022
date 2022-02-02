package PrimoLib;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class PrimoParallelDeadlineGroup extends PrimoCommandGroupBase {

    public PrimoParallelDeadlineGroup(Command deadline, Command... commands) {
        super(new ParallelDeadlineGroup(deadline), commands);
    }
}
