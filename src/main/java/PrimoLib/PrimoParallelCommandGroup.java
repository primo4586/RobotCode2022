package PrimoLib;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class PrimoParallelCommandGroup extends PrimoCommandGroupBase {

    public PrimoParallelCommandGroup(Command... commands) {
        super(new ParallelCommandGroup(), commands);
    }
}
