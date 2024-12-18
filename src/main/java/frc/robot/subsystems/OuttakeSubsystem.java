package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OuttakeSubsystem extends SubsystemBase {
  private final MotorController outtakeController;
  private final double outtakeSpeed;

  public OuttakeSubsystem(MotorController outtakeController, double outtakeSpeed) {
    this.outtakeController = outtakeController;
    this.outtakeSpeed = outtakeSpeed;
  }

  public Command outtakeCommand() {
    return startEnd(() -> outtakeController.set(outtakeSpeed), () -> outtakeController.set(0));
  }
}
