package AutoLib;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

public class PathHandler {
    public Trajectory shootBalls;
    public Trajectory collectBalls;
    public static PathHandler instance;
    private PathHandler(){
        shootBalls = loadPath("paths/output/shootballs.wpilib.json");
        collectBalls = loadPath("paths/output/collectBalls.wpilib.json");
    }

    public static PathHandler getInstance(){
        if(instance == null){
            instance = new PathHandler();
        }
        return instance;
    }

    private static Trajectory loadPath(String path){
        Trajectory trajectory = null;
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(path);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + path, ex.getStackTrace());
        }
        return trajectory;
    }
}
