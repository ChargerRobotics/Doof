// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.swerve.SwerveDriveTrain;
import frc.robot.swerve.SwerveModule;

public class Robot extends TimedRobot {
  private DriveTrainSubsystem driveTrainSubsystem;

  @Override
  public void robotInit() {
    // TODO: actual values for drivetrain
    SwerveDriveTrain driveTrain = new SwerveDriveTrain(new SwerveModule[] {
      new SwerveModule(
        new TalonFXEncoderController(new TalonFX(0)),
        new CANSparkMaxEncoderController(new CANSparkMax(1, MotorType.kBrushless)),
        new Translation2d(0, 0)
      )
    }, new PIDController(0, 0, 0), new PIDController(0, 0, 0));
    driveTrainSubsystem = new DriveTrainSubsystem(driveTrain, () -> {
      // TODO: actual speeds from controller
      return new ChassisSpeeds(0, 0, 0);
    });
  }
}
