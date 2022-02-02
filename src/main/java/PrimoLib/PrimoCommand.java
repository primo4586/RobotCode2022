package PrimoLib;

import edu.wpi.first.wpilibj2.command.Command;

public interface PrimoCommand extends Command {

    PrimoCommandBase whenFinished(Runnable whenFinished);

    PrimoCommandBase beforeStarting(Runnable beforeStarting);
}
