/*  Aside from the last two functions,
    Delete this entire file,
    It's now completely useless & redundant.
*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ShootingCommand;
public class JoystickSubsystem extends SubsystemBase {
    public Joystick joy;
    public boolean square,cross,circle,triangle,lBumper,rBumper,lTrigger,rTrigger,
    select,start,lsButton,rsButton,touchpad;
    public double lStickV,rStickV;
    private ShooterSubsystem shooterSys;
    

    public void controlsInit() {
        joy = new Joystick(0);
    }

    public boolean getSquare() {
        return joy.getRawButton(1);
    }

    public boolean getCross() {
        return joy.getRawButton(2);
    }

    public boolean getCircle() {
        return joy.getRawButton(3);
    }

    public boolean getTriangle() {
        return joy.getRawButton(4);
    }

    public boolean getlBumper() {
        return joy.getRawButton(5);
    }

    public boolean getrBumper() {
        return joy.getRawButton(6);
    }

    public boolean getlTrigger() {
        return joy.getRawButton(7);
    }

    public boolean getrTrigger() {
        return joy.getRawButton(8);
    }

    public boolean getSelect() {
        return joy.getRawButton(9);
    }

    public boolean getStart() {
        return joy.getRawButton(10);
    }

    public boolean getLsButton() {
        return joy.getRawButton(11);
    }

    public boolean getRsButton() {
        return joy.getRawButton(12);
    }

    public boolean getTouchpad() {
        return joy.getRawButton(13);
    }

    public double getlStickV() {
        return joy.getRawAxis(1);
    }

    public double getrStickV() {
        return joy.getRawAxis(5);
    }

    public void ControlMapping(DriveSubsystem driveSys,  ShootingCommand scomm) {
        new JoystickButton(joy, 1)
        .whenPressed(() -> driveSys.highGear());
      new JoystickButton(joy, 2)
        .whenPressed(() -> toggleShooter());
      new JoystickButton(joy, 3)
        .whenPressed(() -> toggleRelay());
      new JoystickButton(joy, 4)
        .whenPressed(() -> driveSys.lowGear());
      new JoystickButton(joy, 5)
        .whenPressed(() -> scomm.execute());
      new JoystickButton(joy, 6)
        .whenPressed(() -> driveSys.resetGyro());
      //new JoystickButton(dualShock, 7)
      //  .whenPressed(() -> limelight.setLedMode(LightMode.eOff));

    }


    /*  I don't know what you're using this for,
        but if you need it for whatever reason,
        move it into a dedicated command.
    */
    public void toggleShooter() {
        //isLifted = !isLifted;
        if(!shooterSys.isUp()) {
          shooterSys.liftShooter();
        }else {
          shooterSys.dropShooter();
        }
      }
    
      public void toggleRelay() {
        if(!shooterSys.isRelayOn()){
          shooterSys.startRelay();
        }else {
          shooterSys.stopRelay();
        }
      }
    
}