package frc.robot.utils;

//  New import here (JoyStickButton)
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//  You may also have to import your command file:
import frc.robot.commands.ShootingCommand;

/*  OI Should not be a SubsystemBase - This causes problems!
    Just don't extend anything - OI can be an independent class.
*/
public class OI extends SubsystemBase {
    public Joystick joy;

    // Get rid of all these booleans and doubles, you never use it anywhere in the class.
    public boolean square,cross,circle,triangle,lBumper,rBumper,lTrigger,rTrigger,
    select,start,lsButton,rsButton,touchpad;
    public double lStickV,rStickV;

    // Need a constructor to initialize all the Objects to default values.
    public OI() {
        // Moved line from controlsInit()
        joy = new Joystick(0);
        
        /*  Setup one-shot commands here (commands that run on a button press)
            For instance, we want to bind square to shoot:

            First part: JoystickButton constructor.
            pass in the Joystick, then the button number.

            Second part: .whenPressed() function.
            pass in a lambda (the '() ->' part), which itself
            passes in the COMMAND AS A NEW OBJECT.
            NOT a function in your command class,
            THE CLASS ITSELF (with the keyword 'new' in front of it)

            (Note: You can pass in a CommandGroup instead of a command if you want)
        */
        new JoystickButton(this.joy, 1).whenPressed(() -> new ShootingCommand());
        // Add more JoystickButtons *as necessary*, not all of them just cause.
    }

    // Remove this entire function
    public void controlsInit() {
        joy = new Joystick(0); // Already moved this line to constructor
    }

    // Everything below this line is fine, no changes needed.

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
    
}