package frc.robot.utils;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncoderSystem {
    private Encoder encoder;

    public EncoderSystem(int sourceA, int sourceB) {
        encoder = new Encoder(sourceA, sourceB);
    }

    public void init() {
        encoder.setDistancePerPulse(1./256.);
    }

    public void putToDashboard() {
        SmartDashboard.putString("Turret Encoder Distance", Double.toString(getDistance()));
    }

    public double getDistance() {
        return encoder.getDistance();
    }
}