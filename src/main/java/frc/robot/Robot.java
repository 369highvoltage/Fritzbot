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
import frc.robot.utils.EncoderSystem;
import frc.robot.utils.Limelight;
import frc.robot.utils.OI;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
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
  private DriveCommand driveCommand;
  private CameraSubsystem cam;
  private Limelight limelight;
    private OI oi;
  private EncoderSystem encoder;
 // private JoystickCommand jcomm;

  /**public static enum LightMode {
		eOn, eOff, eBlink
  }
  
	public static enum CameraMode {
		eVision, eDriver
	} */




  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    dualShock = new Joystick(0);
    dualShock2 = new Joystick(1);
    driveSys = new DriveSubsystem(4, 1, 3, 2);
    shooterSys = new ShooterSubsystem(2, 3, 5, 6, 5, 0, 0);
    limelight = new Limelight();
    cam = new CameraSubsystem();
    oi = new OI();
    driveCommand = new DriveCommand(driveSys, oi);
    encoder = new EncoderSystem(0,1);
   // jcomm = new JoystickCommand(driveSys, shooterSys, oi, scomm);
    encoder.init();
    cam.Vision();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    encoder.putToDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your {@link RobotContainer} class.
   */
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
   shooterSys.Proximity();
   double leftAdjust = -1.0;
   double rightAdjust = -1.0; // default speed values for chase
   leftAdjust -= aimbot();
   rightAdjust += aimbot();
   
    if(shooterSys.getProximity() >= 120)
      driveSys.control(0, 0, 1);

    else{
      if(cam.isTarget() == false){
        driveSys.highGear();
       driveSys.control(-.5, .5, 1);
      }else if((cam.isTarget() == true)){
           driveSys.control(leftAdjust, rightAdjust, 1);
         }
    }
      


  //driveSys.control(leftAdjust, rightAdjust, 1);

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
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    } 
    driveCommand.initialize();
    shooterSys.dropShooter();
    limelight.setCameraMode("vision");
    limelight.setLed("off");
    cam.setLedMode(LightMode.eOff);
    cam.setCameraMode(CameraMode.eDriver);
    driveSys.setDefaultCommand(driveCommand);
    //oi.setDefaultCommand(jcomm);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    limelight.debug();
    
    shooterSys.ColorSensor();
    shooterSys.Proximity();
    
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
    float kp = -.05f;
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
