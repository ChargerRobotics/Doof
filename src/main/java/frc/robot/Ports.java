package frc.robot;

public class Ports {
  public static final int SWERVE_FRONT_LEFT_DRIVE_ID = 1;
  public static final int SWERVE_FRONT_RIGHT_DRIVE_ID = 2;
  public static final int SWERVE_BACK_LEFT_DRIVE_ID = 3;
  public static final int SWERVE_BACK_RIGHT_DRIVE_ID = 4;

  public static final int SWERVE_FRONT_LEFT_ROTATE_ID = 5;
  public static final int SWERVE_FRONT_RIGHT_ROTATE_ID = 6;
  public static final int SWERVE_BACK_LEFT_ROTATE_ID = 7;
  public static final int SWERVE_BACK_RIGHT_ROTATE_ID = 8;
  
  public static final int INTAKE_PORT = 0;
  public static final int RIGHT_SHOOTER_PORT = 5;
  public static final int LEFT_SHOOTER_PORT = 4;

  private Ports() {
    throw new AssertionError();
  }
}
