package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.EndEffectorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class ButtonBindings {

    private final DriveSubsystem m_robotDrive;
    private final EndEffectorSubsystem m_robotEndEffector;
    private final ElevatorSubsystem m_robotElevator;
    private final IntakeSubsystem m_robotIntake;
    private final Joystick m_ButtonController;
    private final Joystick m_driverController;

    private final ElevatorTargetCommand elevatorL2;
    private final ElevatorTargetCommand elevatorL3;
    private final ElevatorTargetCommand elevatorL4;

    public ButtonBindings(RobotContainer container) {
        this.m_robotDrive = container.m_robotDrive;
        this.m_robotEndEffector = container.m_robotEndEffector;
        this.m_robotElevator = container.m_robotElevator;
        this.m_robotIntake = container.m_robotIntake;
        this.m_ButtonController = container.m_ButtonController;
        this.m_driverController = container.m_driverController;

        // Elevator presets
        elevatorL2 = new ElevatorTargetCommand(m_robotElevator, 14, 0.3, 1.5, 0.05);
        elevatorL3 = new ElevatorTargetCommand(m_robotElevator, 44, 0.3, 1.5, 0.05);
        elevatorL4 = new ElevatorTargetCommand(m_robotElevator, 94, 0.3, 1.5, 0.05);
    }

    public void configureButtonBindings() {

        // ------------------------
        // DEFAULT DRIVE COMMAND (swerve)
        // ------------------------
        m_robotDrive.setDefaultCommand(
            new RunCommand(
                () -> m_robotDrive.drive(
                    -m_driverController.getRawAxis(1),   // forward/backward
                    -m_driverController.getRawAxis(0),   // strafe
                    -m_driverController.getRawAxis(4),   // rotation
                    true                                 // field-relative
                ),
                m_robotDrive
            )
        );

        // ------------------------
        // END EFFECTOR SPIN
        // ------------------------
        new Trigger(() -> m_ButtonController.getRawButton(1))
            .whileTrue(new RunCommand(() -> m_robotEndEffector.Shoot(0.45), m_robotEndEffector))
            .whileFalse(new InstantCommand(() -> m_robotEndEffector.Shoot(0.0), m_robotEndEffector));

        new Trigger(() -> m_ButtonController.getRawButton(2))
            .whileTrue(new RunCommand(() -> m_robotEndEffector.Shoot(-0.45), m_robotEndEffector))
            .whileFalse(new InstantCommand(() -> m_robotEndEffector.Shoot(0.0), m_robotEndEffector));

        // ------------------------
        // INTAKE WHEELS
        // ------------------------
        new Trigger(() -> m_ButtonController.getRawButton(3))
            .whileTrue(new RunCommand(() -> m_robotIntake.shoot(0.6), m_robotIntake))
            .whileFalse(new InstantCommand(() -> m_robotIntake.shoot(0.0), m_robotIntake));

        new Trigger(() -> m_ButtonController.getRawButton(4))
            .whileTrue(new RunCommand(() -> m_robotIntake.shoot(-0.6), m_robotIntake))
            .whileFalse(new InstantCommand(() -> m_robotIntake.shoot(0.0), m_robotIntake));

        // ------------------------
        // INTAKE ARM MOVEMENT
        // ------------------------
        new Trigger(() -> m_ButtonController.getRawButton(16))
            .whileTrue(new RunCommand(() -> m_robotIntake.RaiseIntake(0.25), m_robotIntake))
            .whileFalse(new InstantCommand(() -> m_robotIntake.RaiseIntake(0.0), m_robotIntake));

        new Trigger(() -> m_ButtonController.getRawButton(17))
            .whileTrue(new RunCommand(() -> m_robotIntake.RaiseIntake(-0.25), m_robotIntake))
            .whileFalse(new InstantCommand(() -> m_robotIntake.RaiseIntake(0.0), m_robotIntake));

        // ------------------------
        // ELEVATOR LEVELS
        // ------------------------
        new Trigger(() -> m_ButtonController.getRawButton(6)).onTrue(elevatorL2);
        new Trigger(() -> m_ButtonController.getRawButton(7)).onTrue(elevatorL3);
        new Trigger(() -> m_ButtonController.getRawButton(8)).onTrue(elevatorL4);
    }
}
