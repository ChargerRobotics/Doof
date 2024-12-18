package frc.robot.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.motor.EncoderMotorController;

public record SwerveModule(MotorController drive, EncoderMotorController rotate, PIDController rotatePid, Translation2d position) { }
