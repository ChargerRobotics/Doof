package frc.robot;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.motor.CANSparkMaxEncoderController;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.MagSubsystem;
import frc.robot.swerve.SwerveDriveTrain;
import frc.robot.swerve.SwerveModule;

public class RobotContainer {
  private final DriveTrainSubsystem driveTrainSubsystem;

  private final CommandXboxController controller = new CommandXboxController(0);
  private final Pigeon2 pigeon = new Pigeon2(20);

  private final GenericEntry autoEnabledEntry;

  public RobotContainer() {
    ShuffleboardTab rotateTab = Shuffleboard.getTab("rotate");

    rotateTab.addDouble("heading", () -> pigeon.getRotation2d().getDegrees());

    autoEnabledEntry = Shuffleboard.getTab("auto").add("Auto enabled", true)
      .withWidget(BuiltInWidgets.kToggleButton)
      .getEntry();
 
    SwerveDriveTrain driveTrain = new SwerveDriveTrain(new SwerveModule[]{
            new SwerveModule(talonFX(Ports.SWERVE_FRONT_LEFT_DRIVE_ID, false), sparkMaxRotation(Ports.SWERVE_FRONT_LEFT_ROTATE_ID), Constants.FRONT_LEFT_ROTATE_PID, new Translation2d(0.178, 0.173)),
            new SwerveModule(talonFX(Ports.SWERVE_FRONT_RIGHT_DRIVE_ID, false), sparkMaxRotation(Ports.SWERVE_FRONT_RIGHT_ROTATE_ID), Constants.FRONT_RIGHT_ROTATE_PID, new Translation2d(0.178, -0.173)),
            new SwerveModule(talonFX(Ports.SWERVE_BACK_LEFT_DRIVE_ID, false), sparkMaxRotation(Ports.SWERVE_BACK_LEFT_ROTATE_ID), Constants.BACK_LEFT_ROTATE_PID, new Translation2d(-0.178, 0.173)),
            new SwerveModule(talonFX(Ports.SWERVE_BACK_RIGHT_DRIVE_ID, false), sparkMaxRotation(Ports.SWERVE_BACK_RIGHT_ROTATE_ID), Constants.BACK_RIGHT_ROTATE_PID, new Translation2d(-0.178, -0.173)),
    });

    driveTrainSubsystem = new DriveTrainSubsystem(driveTrain, () -> {
      double leftY = -controller.getLeftY();
      if (Math.abs(leftY) < 0.1) leftY = 0;

      double leftX = -controller.getLeftX();
      if (Math.abs(leftX) < 0.1) leftX = 0;

      double rightX = -controller.getRightX();
      if (Math.abs(rightX) < 0.1) rightX = 0;

      ChassisSpeeds speeds = new ChassisSpeeds(
              leftY * Constants.SPEED_PERCENT,
              leftX * Constants.SPEED_PERCENT,
              rightX * Constants.ROTATION_RADIANS
      );
      return ChassisSpeeds.fromFieldRelativeSpeeds(speeds, pigeon.getRotation2d());
      // return speeds;
    });

    PWMSparkMax leftShooterController = new PWMSparkMax(Ports.LEFT_SHOOTER_PORT);
    PWMSparkMax rightShooterController = new PWMSparkMax(Ports.RIGHT_SHOOTER_PORT);
    rightShooterController.setInverted(true);
    leftShooterController.addFollower(rightShooterController);
    MagSubsystem magSubsystem = new MagSubsystem(new PWMSparkMax(Ports.INTAKE_PORT), 0.7, leftShooterController, -1);

    controller.leftBumper()
      .whileTrue(magSubsystem.intakeCommand());

    controller.rightBumper()
      .whileTrue(magSubsystem.shootCommand());

    reset();
  }

  // public Command getAutoCommand() {
  //   if (!autoEnabledEntry.getBoolean(false)) return Commands.none();

  //   return Commands.sequence(
  //     driveTrainSubsystem.runOnce(() -> {
  //       driveTrainSubsystem.getDriveTrain().setSpeeds(new ChassisSpeeds(0.8, 0, 0));
  //     }),
  //     Commands.waitSeconds(2),
  //     driveTrainSubsystem.runOnce(() -> {
  //       driveTrainSubsystem.getDriveTrain().setSpeeds(new ChassisSpeeds());
  //     })
  //   );
  // }

  public DriveTrainSubsystem getDriveTrainSubsystem() {
    return driveTrainSubsystem;
  }

  public void reset() {
    pigeon.reset();
    driveTrainSubsystem.getDriveTrain().reset();
  }

  private TalonFX talonFX(int id, boolean inverted) {
    TalonFX talonFX = new TalonFX(id);
    talonFX.setInverted(inverted);
    return talonFX;
  }

  private CANSparkMaxEncoderController sparkMaxRotation(int id) {
    CANSparkMax controller = new CANSparkMax(id, MotorType.kBrushless);
    controller.getEncoder().setPositionConversionFactor(360 / 21.428);
    controller.setInverted(true);
    return new CANSparkMaxEncoderController(controller);
  }
}
