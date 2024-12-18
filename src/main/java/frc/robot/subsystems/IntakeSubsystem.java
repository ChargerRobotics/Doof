package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  private final MotorController intakeController;
  private final double intakeSpeed;

  public IntakeSubsystem(MotorController intakeController, double intakeSpeed) {
    this.intakeController = intakeController;
    this.intakeSpeed = intakeSpeed;
  }

  public Command intakeCommand() {
    return startEnd(() -> intakeController.set(intakeSpeed), intakeController::stopMotor);
  }
}
