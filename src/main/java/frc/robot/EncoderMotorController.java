package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public interface EncoderMotorController {
  MotorController getMotorController();

  double getPosition();

  double getVelocity();
}
