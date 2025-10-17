package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.EndEffectorConstants;


public class EndEffectorSubsystem extends SubsystemBase {

    private SparkMax LeftOuttakeMotor = new SparkMax(EndEffectorConstants.kLeftOutTakeMotorCanId, MotorType.kBrushless);
    private SparkMax RightOuttakeMotor = new SparkMax(EndEffectorConstants.kRightOutTakeMotorCanId, MotorType.kBrushless);
    

    

    public EndEffectorSubsystem() {

    

    }

    public void Shoot(double shootingSpeed) {
        RightOuttakeMotor.set(-shootingSpeed);
       LeftOuttakeMotor.set(shootingSpeed); // Uncomment if needed.
    }

    public void intake(double intake_speed) {
        RightOuttakeMotor.set(intake_speed);
        LeftOuttakeMotor.set(-intake_speed );
    }

   

    @Override
    public void periodic() {
        // // Access the already instantiated driver controller via the singleton RobotContainer.
        // double axisValue = RobotContainer.getInstance().getDriverController().getRawAxis(5);
        // // Map axis range [-1, 1] to servo angle [180, 0] (i.e. -1 -> 180, 1 -> 0)
        // LLServo.setAngle(((axisValue + 1)/2) * 180);
    }
}
