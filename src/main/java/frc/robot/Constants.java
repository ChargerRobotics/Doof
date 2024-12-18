package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public final class Constants {
  public static final double SPEED_PERCENT = 0.5;
  public static final double ROTATION_RADIANS =  Math.PI;
  public static final PIDController FRONT_LEFT_ROTATE_PID = rotatePid();
  public static final PIDController FRONT_RIGHT_ROTATE_PID = rotatePid();
  public static final PIDController BACK_LEFT_ROTATE_PID = rotatePid();
  public static final PIDController BACK_RIGHT_ROTATE_PID = rotatePid();

  private static final PIDController rotatePid() {
    PIDController controller = new PIDController(0.0075, 0, 0);
    controller.enableContinuousInput(-180, 180);
    return controller;
  }

  private Constants() {
    throw new AssertionError();
  }
}
