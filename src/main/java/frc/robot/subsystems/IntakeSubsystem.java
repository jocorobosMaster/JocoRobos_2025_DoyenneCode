package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
double FinalRaiseSpeed = 0;
boolean IntakeUp = false;
double speed = .5;
double intake_up_speed = .2;

    WPI_VictorSPX IntakeMotor = new WPI_VictorSPX(IntakeConstants.kIntakeRotationMotorCanId);
    WPI_VictorSPX IntakeShoot = new WPI_VictorSPX(IntakeConstants.kIntakeShoototorCanId);

    


    public void RaiseIntake(double Direction) { // Positive int = raise, negative = lower, 0 = stop
        
        IntakeMotor.set(-intake_up_speed);
     
    }

    public void shoot(double Direction) { // Positive int = raise, negative = lower, 0 = stop
        
        IntakeShoot.set(-speed);
      
    }


   


   
}