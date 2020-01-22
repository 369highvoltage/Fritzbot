/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.JoystickCommand;
import frc.robot.commands.ShootingCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.JoystickSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.CameraSubsystem.CameraMode;
import frc.robot.subsystems.CameraSubsystem.LightMode;
import frc.robot.utils.Limelight;
import frc.robot.utils.OI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

// I got rid of all the comments so that it wouldn't look so cluttered.


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  public Joystick dualShock;
  public Joystick dualShock2;
  //public Talon FL,FR,RL,RR;
  //public DifferentialDrive drive;
  //public SpeedControllerGroup leftSide;
  //public SpeedControllerGroup rightSide;
  private RobotContainer m_robotContainer;
  private DriveSubsystem driveSys;
  private ShooterSubsystem shooterSys;
  private JoystickSubsystem joySys;
  private DriveCommand driveCommand;
  private CameraSubsystem cam;
  private Limelight limelight;
  private OI oi;
  private ShootingCommand scomm;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    dualShock = new Joystick(0);
    dualShock2 = new Joystick(1);
    driveSys = new DriveSubsystem(4, 1, 3, 2);
    shooterSys = new ShooterSubsystem(2, 3, 5, 6, 5, 0, 0);
    limelight = new Limelight();
    scomm = new ShootingCommand();
    cam = new CameraSubsystem();
    oi = new OI();

    /*  Change DriveCommand constructor to not use joySys.
        Also change DriveCommand constructor so that you can pass in OI.
    */
    driveCommand = new DriveCommand(driveSys, joySys);
    cam.Vision();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    //driveSys.autoCont();
    System.out.println("Passed init");

    cam.setCameraMode(CameraMode.eVision);
    cam.setLedMode(LightMode.eOn);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
   double leftAdjust = 0.0;
   double rightAdjust = 0.0;

   leftAdjust -= aimbot();
   rightAdjust += aimbot();

   driveSys.control(leftAdjust, rightAdjust, 1);

   // System.out.println("Created adjust varibles");
   //while (limelight.isTarget() != false) {
   // driveSys.control(leftAdjust,rightAdjust,1,1);
   // System.out.println(limelight.getTx() + "," + limelight.isTarget());
   //}
   //if (limelight.isTarget() == false){
   //driveSys.control(-.1, -.1, 1, 2);
   //}
   //System.out.println("Passed movement functions");
   //System.out.println(leftAdjust + " , " +  rightAdjust);



  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    } 
    driveCommand.initialize(); //delete
    shooterSys.dropShooter();
    limelight.setCameraMode("vision");
    limelight.setLed("off");
    cam.setLedMode(LightMode.eOff);
    cam.setCameraMode(CameraMode.eDriver);

    /*  Setup continuously repeating commands here (commands that run again & again)
        Ex. we always want to be able to drive around:

        Format:
        <subsystem>.setDefaultCommand(<command>);

        Alternatively you can use the lambda syntax:
        <subsystem>.setDefaultCommand(() -> new <command>);
    */
    driveSys.setDefaultCommand(driveCommand);
  }

  @Override
  public void teleopPeriodic() {
    driveCommand.execute(); //Remove
    limelight.debug();
    double inputL = dualShock.getRawAxis(1);
    double inputR = dualShock.getRawAxis(5); 
    double modifier = 1; 
    
    shooterSys.ColorSensor();
    shooterSys.Proximity();
    joySys.ControlMapping(driveSys, scomm); //delete
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

 
  


  public double aimbot() {
    float kp = -.1f;
    float minCommand = .005f;
    float steeringAdjust = 0.05f;
    //double txEntry = getValue("tx").getDouble(0.0);
    float tx = (float) 
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    //SmartDashboard.setDefaultNumber("TX", tx);
    float headingError = -tx;

    if(tx > 1) {
        steeringAdjust = kp*headingError -minCommand;
    }else if (tx < 1){
        steeringAdjust = kp*headingError + minCommand;
    }
    
    return steeringAdjust;


}


}
