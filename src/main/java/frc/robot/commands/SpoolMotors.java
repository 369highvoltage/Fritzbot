package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.JoystickSubsystem;

public class SpoolMotors extends CommandBase { 
    // Remove the 'final' keyword.
    private final ShooterSubsystem shootSys;

    // Remove joySys, JoystickSubsystem is now gone.
    private final JoystickSubsystem joySys;
    private Timer timer;

    // Remove joySys parameter from constructor.
    public SpoolMotors(ShooterSubsystem shootSys, JoystickSubsystem joySys) {
        // In fact, just remove every reference to joySys & JoystickSubsystem.
        this.joySys = joySys;
        this.shootSys = shootSys;

        // Call addRequirements() & pass in your Shooting Subsystem.
    }

 // Called when the command is initially scheduled.
 @Override
 public void initialize() {
    timer = new Timer();
    shootSys.spoolMotors(1, 1);
    timer.start();
 }

 // Called every time the scheduler runs while the command is scheduled.
 @Override
 public void execute() {
    
 }

 // Called once the command ends or is interrupted.
 @Override
 public void end(boolean interrupted) {
    
 }

 // Returns true when the command should end.
 @Override
 public boolean isFinished() {
   return timer.get() >= 2;
 }

}