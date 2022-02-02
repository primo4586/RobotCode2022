package PrimoLib;

import edu.wpi.first.wpilibj2.command.CommandBase;


public abstract class PrimoCommandBase extends CommandBase implements PrimoCommand {
    public PrimoCommandBase whenFinished(Runnable whenFinished) {
        PrimoCommandBase command = this;
        return new PrimoCommandBase() {
            @Override
            public void initialize() {
                command.initialize();
            }

            @Override
            public void execute() {
                command.execute();
            }

            @Override
            public boolean isFinished() {
                return command.isFinished();
            }

            @Override
            public void end(boolean interrupted) {
                command.end(interrupted);
                whenFinished.run();
            }

        };
    }

    public PrimoCommandBase beforeStarting(Runnable beforeStarting) {
        PrimoCommandBase command = this;
        return new PrimoCommandBase() {
            @Override
            public void initialize() {
                beforeStarting.run();
                command.initialize();
            }

            @Override
            public void execute() {
                command.execute();
            }

            @Override
            public boolean isFinished() {
                return command.isFinished();
            }

            @Override
            public void end(boolean interrupted) {
                command.end(interrupted);
            }
        };
    }
}
