package autonomous;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;

public class CommandSelector {
    SendableChooser<Command> chooser;
    SelectCommand selector;
    Map<String,Command> commands;

    public CommandSelector(Map<String,Command> commands,String name){
        this(commands,Shuffleboard.getTab(name));
    }
    public CommandSelector(Map<String,Command> commands,ShuffleboardTab tab) {
        List<Entry<String, Command>> commandList = new ArrayList<>();
        this.commands = commands;
        chooser = new SendableChooser<>();
        
        for(Entry<String,Command> e : commands.entrySet()){
            commandList.add(e);
        }
        chooser.setDefaultOption(commandList.get(0).getKey(), commandList.get(0).getValue());
        for(int i = 1; i < commandList.size();i++){
            chooser.addOption(commandList.get(i).getKey(), commandList.get(i).getValue());
        }
        tab.add(chooser);
    }

    public Command getCommand() {
        return chooser.getSelected();
    }
}
