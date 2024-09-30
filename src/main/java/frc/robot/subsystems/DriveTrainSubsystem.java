package frc.robot.subsystems;

import java.util.function.Supplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.swerve.SwerveDriveTrain;

public class DriveTrainSubsystem extends SubsystemBase {
  private final SwerveDriveTrain driveTrain;

  public DriveTrainSubsystem(SwerveDriveTrain driveTrain, Supplier<ChassisSpeeds> speedsSupplier) {
    this.driveTrain = driveTrain;

    setDefaultCommand(run(() -> {
      ChassisSpeeds speeds = speedsSupplier.get();

      driveTrain.setSpeeds(speeds);
    }));
  }

  @Override
  public void periodic() {
    driveTrain.update();
  }

  public SwerveDriveTrain getDriveTrain() {
    return driveTrain;
  }
}
