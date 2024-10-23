package frc.robot.motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public interface EncoderMotorController {
  MotorController getMotorController();

  double getPosition();

  double getVelocity();
}
