package frc.robot.control;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class FeedforwardControl implements VelocityControlSystem {
  private final SimpleMotorFeedforward feedforward;

  public FeedforwardControl(SimpleMotorFeedforward feedforward) {
    this.feedforward = feedforward;
  }

  @Override
  public double calculateVoltage(double setPointVelocity, double currentVelocity) {
    return feedforward.calculate(setPointVelocity);
  }
}
