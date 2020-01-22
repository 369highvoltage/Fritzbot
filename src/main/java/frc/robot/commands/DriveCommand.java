package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

// Remove
import frc.robot.subsystems.JoystickSubsystem;

public class DriveCommand extends CommandBase {
    /*  Add OI object to this DriveCommand

        Ex: private OI oi;
    */

    private DriveSubsystem driveSys;
    private JoystickSubsystem joySys;

    /*  Remove JoystickSubsystem from constructor
        Add OI class to constructor

        Ex: public DriveCommand(DriveSubsystem driveSys, JoystickSubsystem joySys, OI oi)
    */
    public DriveCommand(DriveSubsystem driveSys, JoystickSubsystem joySys) {
        this.driveSys = driveSys;
        this.joySys = joySys; // Remove
        addRequirements(joySys); // Remove
        addRequirements(driveSys);

        /*  Initialize OI here.
            DO NOT CALL addRequirements() ON OI.
        */
    }

    // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    joySys.controlsInit();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    driveSys.control(joySys.getlStickV(), joySys.getrStickV(), 1); // Use OI functions instead of joySys functions.
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}