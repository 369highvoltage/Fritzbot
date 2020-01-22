package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootingCommand extends SequentialCommandGroup {
    private ShooterSubsystem shootSys;
    private JoystickSubsystem joySys; // Delete this, JoystickSubsystem is now gone.
    
    public ShootingCommand() {
        /*  Not sure, but may have to initialize shootSys here
            Also means you may have to modify the constructor to accept a ShooterSubsystem as a command.
        */

        addCommands(
            // If you have multiple commands, put them on separate lines for clarity.
            new SpoolMotors(shootSys, joySys),
            new FireDisc(shootSys)
        );
    }
    
}