package frc.robot.swerve;

import java.util.Arrays;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.EncoderMotorController;

public class SwerveDriveTrain {
  private final SwerveDriveKinematics kinematics;
  private final SwerveModule[] modules;
  private final PIDController speedController;
  private final PIDController rotateController;

  private SwerveModuleState[] states;

  public SwerveDriveTrain(SwerveModule[] modules, PIDController speedController, PIDController rotateController) {
    this.kinematics = new SwerveDriveKinematics(Arrays.stream(modules).map(module -> module.position()).toArray(Translation2d[]::new));
    this.modules = modules;
    this.speedController = speedController;
    this.rotateController = rotateController;
  }

  public void setSpeeds(ChassisSpeeds speeds) {
    this.states = kinematics.toSwerveModuleStates(speeds);
  }

  public void update() {
    if (states == null) return;

    for (int i = 0; i < states.length; i++) {
      // TODO: gyro
      SwerveModuleState optimized = SwerveModuleState.optimize(states[i], Rotation2d.fromDegrees(0));

      EncoderMotorController drive = modules[i].drive();
      drive.getMotorController().set(speedController.calculate(drive.getVelocity(), optimized.speedMetersPerSecond));

      EncoderMotorController rotate = modules[i].rotate();
      rotate.getMotorController().set(rotateController.calculate(rotate.getPosition(), optimized.angle.getDegrees()));
    }
  }
}
