package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class TalonFXEncoderController implements EncoderMotorController {
  private final TalonFX talonFX;

  public TalonFXEncoderController(TalonFX talonFX) {
    this.talonFX = talonFX;
  }

  @Override
  public MotorController getMotorController() {
    return talonFX;
  }

  @Override
  public double getPosition() {
    return talonFX.getPosition().getValueAsDouble();
  }

  @Override
  public double getVelocity() {
    return talonFX.getVelocity().getValueAsDouble();
  }
}
