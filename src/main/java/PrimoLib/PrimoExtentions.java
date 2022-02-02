package PrimoLib;

import edu.wpi.first.wpilibj2.command.Command;

public class PrimoExtentions {

    public static PrimoCommandBase whenFinished(Command command, Runnable... whenFinished) {
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
                for (Runnable r : whenFinished)
                    r.run();
            }

        };
    }

    public static PrimoCommandBase beforeStarting(Command command, Runnable... beforeStarting) {

        return new PrimoCommandBase() {
            @Override
            public void initialize() {
                for (Runnable r : beforeStarting)
                    r.run();
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
