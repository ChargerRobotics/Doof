package frc.robot.swerve;

import java.util.Arrays;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.motor.EncoderMotorController;

public class SwerveDriveTrain {
  private final SwerveDriveKinematics kinematics;
  private final SwerveModule[] modules;

  private SwerveModuleState[] states;

  public SwerveDriveTrain(SwerveModule[] modules) {
    this.kinematics = new SwerveDriveKinematics(Arrays.stream(modules).map(SwerveModule::position).toArray(Translation2d[]::new));
    this.modules = modules;
  }

  public void setSpeeds(ChassisSpeeds speeds) {
    this.states = kinematics.toSwerveModuleStates(speeds);
  }

  public void update() {
    if (states == null) return;

    for (int i = 0; i < states.length; i++) {
      SwerveModuleState optimized = SwerveModuleState.optimize(states[i], Rotation2d.fromDegrees(modules[i].rotate().getPosition()));
      // SwerveModuleState optimized = states[i];

      modules[i].drive().set(MathUtil.clamp(optimized.speedMetersPerSecond, -1, 1));

      EncoderMotorController rotate = modules[i].rotate();
      rotate.getMotorController().set(MathUtil.clamp(modules[i].rotatePid().calculate(rotate.getPosition(), optimized.angle.getDegrees()), -0.8, 0.8));
    }
  }

  public void reset() {
    for (SwerveModule module : modules) {
      module.rotate().setPosition(0);
    }
  }

  public void addShuffleboardData(ShuffleboardTab tab) {
    for (int i = 0; i < modules.length; i++) {
      SwerveModule module = modules[i];
      tab.addNumber("Swerve Drive % " + i, () -> module.drive().get());
      tab.addNumber("Swerve Rotate % " + i, () -> module.rotate().getMotorController().get());
      tab.addNumber("Swerve Rotate Encoder " + i, () -> module.rotate().getPosition());

      int anotherI = i;
      tab.addNumber("Swerve Rotate deg " + i, () -> {
        if (states == null) return 0;
        return states[anotherI].angle.getDegrees();
      });
    }
  }
}
