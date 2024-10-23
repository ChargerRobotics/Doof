package frc.robot.control;

public interface VelocityControlSystem {
  double calculateVoltage(double setPointVelocity, double currentVelocity);

  static VelocityControlSystem added(VelocityControlSystem left, VelocityControlSystem right) {
    return (setPointVelocity, currentVelocity) -> {
      return left.calculateVoltage(setPointVelocity, currentVelocity) + right.calculateVoltage(setPointVelocity, currentVelocity);
    };
  }
}
