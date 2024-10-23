package frc.robot;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.control.FeedforwardControl;
import frc.robot.motor.CANSparkMaxEncoderController;
import frc.robot.motor.TalonFXEncoderController;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.swerve.SwerveDriveTrain;
import frc.robot.swerve.SwerveModule;

public class RobotContainer {
  private final DriveTrainSubsystem driveTrainSubsystem;

  private final CommandXboxController controller = new CommandXboxController(0);
  private final Pigeon2 pigeon = new Pigeon2(1);

  public RobotContainer() {
    // TODO: actual values for drivetrain
    SwerveDriveTrain driveTrain = new SwerveDriveTrain(new SwerveModule[]{
            new SwerveModule(
                    new TalonFXEncoderController(new TalonFX(0)),
                    new CANSparkMaxEncoderController(new CANSparkMax(1, CANSparkLowLevel.MotorType.kBrushless)),
                    new Translation2d(0, 0)
            )
    }, new FeedforwardControl(new SimpleMotorFeedforward(0, 0)), new PIDController(0, 0, 0), pigeon::getRotation2d);
    driveTrainSubsystem = new DriveTrainSubsystem(driveTrain, () -> {
      ChassisSpeeds speeds = new ChassisSpeeds(
              -controller.getLeftY() * Constants.SPEED_METERS_PER_SECOND,
              -controller.getLeftX() * Constants.SPEED_METERS_PER_SECOND,
              -controller.getRightX() * Constants.ROTATION_RADIANS_PER_SECOND
      );
      return ChassisSpeeds.fromFieldRelativeSpeeds(speeds, pigeon.getRotation2d());
    });
  }
}
