package frc.robot.swerve;

import java.util.Arrays;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.control.VelocityControlSystem;
import frc.robot.motor.EncoderMotorController;

public class SwerveDriveTrain {
  private final SwerveDriveKinematics kinematics;
  private final SwerveModule[] modules;
  private final VelocityControlSystem speedController;
  private final PIDController rotateController;
  private final Supplier<Rotation2d> headingSupplier;

  private SwerveModuleState[] states;

  public SwerveDriveTrain(SwerveModule[] modules, VelocityControlSystem speedController, PIDController rotateController, Supplier<Rotation2d> headingSupplier) {
    this.kinematics = new SwerveDriveKinematics(Arrays.stream(modules).map(SwerveModule::position).toArray(Translation2d[]::new));
    this.modules = modules;
    this.speedController = speedController;
    this.rotateController = rotateController;
    this.headingSupplier = headingSupplier;
  }

  public void setSpeeds(ChassisSpeeds speeds) {
    this.states = kinematics.toSwerveModuleStates(speeds);
  }

  public void update() {
    if (states == null) return;

    for (int i = 0; i < states.length; i++) {
      SwerveModuleState optimized = SwerveModuleState.optimize(states[i], headingSupplier.get());

      EncoderMotorController drive = modules[i].drive();
      drive.getMotorController().setVoltage(speedController.calculateVoltage(drive.getVelocity(), optimized.speedMetersPerSecond));

      EncoderMotorController rotate = modules[i].rotate();
      // TODO: change to setVoltage to account for voltage sag
      rotate.getMotorController().set(rotateController.calculate(rotate.getPosition(), optimized.angle.getDegrees()));
    }
  }
}
