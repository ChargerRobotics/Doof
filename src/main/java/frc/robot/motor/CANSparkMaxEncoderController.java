package frc.robot.motor;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class CANSparkMaxEncoderController implements EncoderMotorController {
  private final CANSparkMax controller;

  public CANSparkMaxEncoderController(CANSparkMax controller) {
    this.controller = controller;
  }

  @Override
  public MotorController getMotorController() {
    return controller;
  }

  @Override
  public double getPosition() {
    return controller.getEncoder().getPosition();
  }

  @Override
  public void setPosition(double position) {
    controller.getEncoder().setPosition(position);
  }

  @Override
  public double getVelocity() {
    return controller.getEncoder().getVelocity();
  }
}
