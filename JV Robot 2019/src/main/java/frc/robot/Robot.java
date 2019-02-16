/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.cscore.UsbCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  double x;
  double y;
  // Doubles for driving axies
  UsbCamera Cam0;
  UsbCamera Cam1;
  Joystick joy = new Joystick(0);
  Talon frontRight = new Talon(0);
  Talon frontLeft = new Talon(3);
  Talon backRight = new Talon(1);
  Talon backLeft = new Talon(2);
  // Names driving motor controllers (Correct PDM ports)
  Relay claw = new Relay(0);
  // Names claw motor controller? hopefully

  /**
   * Talon forback = new Talon(5); // Names claw moving back and forth motor
   * controller (No idea if right PDM port) Talon lift1 = new Talon(6); Not using
   * lift code or forward and back Talon lift2 = new Talon(7); // Names lift motor
   * controller (No idea if right PDM port and if actually using lift)
   */
  SpeedControllerGroup Left = new SpeedControllerGroup(frontLeft, backLeft);
  SpeedControllerGroup Right = new SpeedControllerGroup(frontRight, backRight);
  // SpeedControllerGroup lift = new SpeedControllerGroup(lift1, lift2);
  // Combines two motor controllers
  DifferentialDrive fullSendDrive = new DifferentialDrive(Left, Right);
  // Combines left and right into one
  // NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  // NetworkTableEntry tx = table.getEntry("tx");
  // NetworkTableEntry ty = table.getEntry("ty");
  // NetworkTableEntry ta = table.getEntry("ta");
  // Bunch of limelight stuff that hopefully works

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    Cam0 = CameraServer.getInstance().startAutomaticCapture(0);
    Cam1 = CameraServer.getInstance().startAutomaticCapture(1);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    x = joy.getRawAxis(1);
    y = joy.getRawAxis(0);
    // Sets x and y to Axis values
    fullSendDrive.arcadeDrive(-x, y);
    // Motors controllers get x and y values so it can full send it my dude
    // double xlight = tx.getDouble(0.0);
    // double ylight = ty.getDouble(0.0);
    // double area = ta.getDouble(0.0);
    // read values periodically
    // SmartDashboard.putNumber("LimelightX", xlight);
    // SmartDashboard.putNumber("LimelightY", ylight);
    // SmartDashboard.putNumber("LimelightArea", area);
    // post to smart dashboard periodically
    // NetworkTableInstance.getDefault().getTable("limelight").getEntry("<variablename>").getDouble(0);
    if (joy.getRawButton(1) == true) {
      claw.set(Value.kForward);
      // Claw grabs hatch (Trigger)
    } else if (joy.getRawButton(2) == true) {
      claw.set(Value.kReverse);
      // Claw lets go of hatch
    } else {
      claw.set(Relay.Value.kOff);
    }

    /**
     * Not using forward and backward and lift code if (joy.getRawButton(5)) {
     * forback.set(0.2); //moves claw forward (Top left) } else if
     * (joy.getRawButton(3)) { forback.set(-0.2); //moves claw backward (Bottom
     * Left) } else { forback.set(0); //claw doesn't move naturally }
     * 
     * if (joy.getRawButton(6)) { lift.set(0.2); //lift moves up (Top Right) } else
     * if (joy.getRawButton(4)) { lift.set(-0.2); //lift moves down (Bottom Right) }
     * else { lift.set(0); //lift doesn't move naturally }
     */
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}