package frc.robot.swerve;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.EncoderMotorController;

public record SwerveModule(EncoderMotorController drive, EncoderMotorController rotate, Translation2d position) { }
