package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MagSubsystem extends SubsystemBase {
  private final MotorController intakeMotor;
  private final double intakeSpeed;

  private final MotorController shooterMotor;
  private final double shooterSpeed;

  public MagSubsystem(MotorController intakeMotor, double intakeSpeed, MotorController shooterMotor, double shooterSpeed) {
    this.intakeMotor = intakeMotor;
    this.intakeSpeed = intakeSpeed;

    this.shooterMotor = shooterMotor;
    this.shooterSpeed = shooterSpeed;
  }

  public Command intakeCommand() {
    return startEnd(() -> intakeMotor.set(intakeSpeed), () -> intakeMotor.stopMotor());
  }

  public Command shootCommand() {
    return startEnd(() -> shooterMotor.set(shooterSpeed), () -> shooterMotor.set(0));
  }
}
