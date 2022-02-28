package frc.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import PrimoLib.PrimoShuffleboard;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cscore.VideoSource.ConnectionStrategy;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CameraHandler {

    private List<VideoSource> cameras;
    private VideoSink sink;
    private VideoSink limelightSink;
    private int index;
    private boolean isUsingLimelight;
    private HttpCamera limelightStream;

    public CameraHandler(VideoSource... cams) {
        sink = CameraServer.addSwitchedCamera("POV: You are Itzik");
        limelightSink = CameraServer.addServer("Limelight");
        cameras = new ArrayList<>();
        index = 0;
        isUsingLimelight = false;

        for (var cam : cams) {
            cameras.add(cam);
            cam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        }

        setCamera(0);
        enableLimelightStream();

        PrimoShuffleboard.getInstance().getCompetitonBoard().getTab().add("POV: You are Itzik", sink.getSource());
        PrimoShuffleboard.getInstance().getCompetitonBoard().getTab().add("POV: You are Limelight",
                limelightSink.getSource());

    }

    public void addCameras(VideoSource... cameras) {
        for (var cam : cameras) {
            this.cameras.add(cam);
            cam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        }
    }

    public VideoSource getCurrentSource() {
        return cameras.get(index);
    }

    public void setCamera(int index) {
        this.index = index;
        var currCam = sink.getSource();
        var putCam = cameras.get(index);
        // Make sure only one camera has "good" quality
        currCam.setVideoMode(PixelFormat.kGray, 4, 3, 1);
        putCam.setVideoMode(PixelFormat.kYUYV, 160, 120, 30);
        sink.setSource(putCam);
    }

    public void switchCamera() {
        setCamera((index + 1) % cameras.size());
    }

    public VideoSource getCamera(int index) {
        return cameras.get(index);
    }

    public void enableLimelightStream() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
        // NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
        limelightStream = new HttpCamera("Limelight", "http://10.45.86.11:5800");
        // limelightStream.setVideoMode(PixelFormat.kYUYV, 160, 120, 30);
        limelightSink.setSource(limelightStream);
    }

}
