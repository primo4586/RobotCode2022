package PrimoLib;

import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class PrimoShuffleboard {
    
    private static Map<String,PrimoTab> tabs;

    // Adds or gets a new PrimoTab to avoid adding a tab that already exists and crashing the robot
    public static PrimoTab getPrimoTab(String tabName) {
        if(tabs.containsKey(tabName))
            return tabs.get(tabName);
        
        PrimoTab tab = new PrimoTab(tabName);
        tabs.put(tabName, new PrimoTab(tabName)); 
        return tab;
    }

    // Switches the dashboard to view a different tab, if it exists
    public static void selectTab(String tabName) {
        if(tabs.containsKey(tabName))
            Shuffleboard.selectTab(tabName);
    }



}
