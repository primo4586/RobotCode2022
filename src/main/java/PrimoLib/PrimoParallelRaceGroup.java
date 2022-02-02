package PrimoLib;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

public class PrimoParallelRaceGroup extends PrimoCommandGroupBase {

    public PrimoParallelRaceGroup(Command... commands) {
        super(new ParallelRaceGroup(), commands);
    }
}

