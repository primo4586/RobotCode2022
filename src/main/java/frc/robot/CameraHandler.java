package frc.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

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
    private int index;
    private boolean isUsingLimelight;
    private HttpCamera limelightStream;

    public CameraHandler(VideoSource... cams) {
        sink = CameraServer.addSwitchedCamera("POV: You are Itzik");
        cameras = new ArrayList<>();
        index = 0;
        isUsingLimelight = false;

        for (var cam : cams) {
            cameras.add(cam);
            cam.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
        }

        
        setCamera(0);
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

    public void switchCameraToLimelight() {
        if (!isUsingLimelight) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
            limelightStream = new HttpCamera("Limelight", "10.45.86.11:5800");
            sink.setSource(limelightStream);
        } else {
            limelightStream.close();
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
            setCamera(0);
        }
    }
}
