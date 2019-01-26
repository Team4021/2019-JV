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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

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
    Double leftStick;
    Double rightStick;
    Joystick joy1 = new Joystick(0);
    Joystick joy2 = new Joystick(1);
    Talon frontRight = new Talon(0);
    Talon frontLeft = new Talon(1);
    Talon backRight = new Talon(2);
    Talon backLeft = new Talon(3);
    Talon claw = new Talon(4);
    Talon forback = new Talon(5);
    Talon lift1 = new Talon(6);
    Talon lift2 = new Talon(7);
    //Names motor controllers
    SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, backLeft);
    SpeedControllerGroup right = new SpeedControllerGroup(frontRight, backRight);
    SpeedControllerGroup lift = new SpeedControllerGroup(lift1, lift2);
    //Combines two motor controllers
    DifferentialDrive drive = new DifferentialDrive(left, right);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
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
    leftStick = joy1.getRawAxis(1);
    rightStick = joy2.getRawAxis(1);
    drive.tankDrive(leftStick, rightStick);
    if (joy2.getRawButton(1)) {
      claw.set(0.2);
      //Claw closes
    }
    else if (joy1.getRawButton(1)) {
      claw.set(-0.2);
      //Claw opens
    }
    else {
      claw.set(0);
      //Claw doesn't move naturally
    }
    if (joy2.getRawButton(3)) {
      forback.set(0.2);
      //moves claw forward
    }
    else if (joy1.getRawButton(4)) {
      forback.set(-0.2);
      //moves claw backward
    }
    else {
      forback.set(0);
      //claw doesn't move naturally
    }
    if (joy2.getRawButton(5)) {
      lift.set(0.2);
    //lift moves up
    }
    else if (joy1.getRawButton(6)) {
      lift.set(-0.2);
      //lift moves down
    }
    else {
      lift.set(0);
      //lift doesn't move naturally
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
