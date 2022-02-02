package PrimoLib;

import java.util.Collection;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public abstract class PrimoCommandGroupBase extends PrimoCommandBase {

    private CommandGroupBase com;

    public PrimoCommandGroupBase(CommandGroupBase com, Command... commands) {
        this.com = com;
        addCommands(commands);
    }

    public void addCommands(Command... commands) {
        com.addCommands(commands);
    }

    @Override
    public void initialize() {
        com.initialize();
    }

    @Override
    public void execute() {
        com.execute();
    }

    @Override
    public void end(boolean interrupted) {
        com.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return com.isFinished();
    }

    @Override
    public boolean runsWhenDisabled() {
        return com.runsWhenDisabled();
    }
    
    public PrimoCommandGroupBase withTimeOut(double time){
        return new PrimoParallelRaceGroup(new WaitCommand(time), com);
        
    }

    public static void clearGroupedCommands() {
        CommandGroupBase.clearGroupedCommands();
    }

    public static void clearGroupedCommand(Command command) {
        CommandGroupBase.clearGroupedCommand(command);
    }

    public static void requireUngrouped(Command... commands) {
        CommandGroupBase.requireUngrouped(commands);
    }

    public static void requireUngrouped(Collection<Command> commands) {
        CommandGroupBase.requireUngrouped(commands);
    }

    public static PrimoCommandGroupBase sequence(Command... commands) {
        return new PrimoSequentialCommandGroup(commands);
    }

    public static PrimoCommandGroupBase parallel(Command... commands) {
        return new PrimoParallelCommandGroup(commands);
    }

    public static PrimoCommandGroupBase race(Command... commands) {
        return new PrimoParallelRaceGroup(commands);
    }

    public static PrimoCommandGroupBase deadline(Command deadline, Command... commands) {
        return new PrimoParallelDeadlineGroup(deadline, commands);
    }


}
