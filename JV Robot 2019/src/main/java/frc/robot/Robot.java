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
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

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
  Double leftstick;
  Double rightstick;
  double righttrigger;
  double lefttrigger;
  boolean open;
  boolean close;
  Joystick xbox = new Joystick(0);
  WPI_TalonSRX frontright = new WPI_TalonSRX(3);
  WPI_TalonSRX frontleft = new WPI_TalonSRX(2);
  WPI_TalonSRX backright = new WPI_TalonSRX(4);
  WPI_TalonSRX backleft = new WPI_TalonSRX(7);
  SpeedControllerGroup left = new SpeedControllerGroup(frontleft, backleft);
  SpeedControllerGroup right = new SpeedControllerGroup(frontright, backright);
  DifferentialDrive drive = new DifferentialDrive(left, right);
  WPI_TalonSRX openClose = new WPI_TalonSRX(1);
  WPI_TalonSRX upDown = new WPI_TalonSRX(8);
  UsbCamera camera;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    camera = CameraServer.getInstance().startAutomaticCapture(0);

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
    leftstick = xbox.getRawAxis(1);
    rightstick = xbox.getRawAxis(5);
    righttrigger = xbox.getRawAxis(3);
    lefttrigger = xbox.getRawAxis(2);
    open = xbox.getRawButton(3); // Button x on the xbox controller
    close = xbox.getRawButton(4); // Button y on the xbox controller
    drive.tankDrive(-leftstick, -rightstick);
    if (righttrigger > 0) {
      upDown.set(-righttrigger);
    } else if (lefttrigger > 0) {
      upDown.set(lefttrigger);
    } else {
      upDown.set(0);
    }
    if (open == true) {
      openClose.set(0.5);
    } else if (close == true) {
      openClose.set(-0.5);
    } else {
      openClose.set(0);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
