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

    private final ElevatorTargetCommand elevatorL1;
    private final ElevatorTargetCommand elevatorL2;
    private final ElevatorTargetCommand elevatorL3;
    private final ElevatorTargetCommand elevatorL4;
    private final ElevatorTargetCommand elevatorBall1;
    private final ElevatorTargetCommand elevatorBall2;

    public ButtonBindings(RobotContainer container) {
        this.m_robotDrive = container.m_robotDrive;
        this.m_robotEndEffector = container.m_robotEndEffector;
        this.m_robotElevator = container.m_robotElevator;
        this.m_robotIntake = container.m_robotIntake;
        this.m_ButtonController = container.m_ButtonController;
        this.m_driverController = container.m_driverController;

        elevatorL1 = new ElevatorTargetCommand(m_robotElevator, 44, 0.3, 1.5, 0.05);
        elevatorL2 = new ElevatorTargetCommand(m_robotElevator, 14, 0.3, 1.5, 0.05);
        elevatorL3 = new ElevatorTargetCommand(m_robotElevator, 44, 0.3, 1.5, 0.05);
        elevatorL4 = new ElevatorTargetCommand(m_robotElevator, 94, 0.3, 1.5, 0.05);
        elevatorBall1 = new ElevatorTargetCommand(m_robotElevator, 30, 0.3, 3, 0.05);
        elevatorBall2 = new ElevatorTargetCommand(m_robotElevator, 44, 0.3, 3, 0.05);
    }

    public void configureButtonBindings() {
        ElevatorBindings();

        // ------------------------
        // DRIVE CONTROL SHORT MOVES
        // ------------------------
        new Trigger(() -> m_driverController.getRawButton(5))
            .onTrue(new RunCommand(() -> m_robotDrive.drive(0, .25, 0, true), m_robotDrive).withTimeout(1.5));

        new Trigger(() -> m_driverController.getRawButton(6))
            .onTrue(new RunCommand(() -> m_robotDrive.drive(0, -.25, 0, true), m_robotDrive).withTimeout(1.5));

        // ------------------------
        // MANUAL ELEVATOR ADJUST
        // ------------------------
        new Trigger(() -> m_ButtonController.getRawButton(16))
            .whileTrue(new RunCommand(() -> m_robotElevator.lift(-.1), m_robotElevator))
            .whileFalse(new InstantCommand(() -> m_robotElevator.lift(0), m_robotElevator));

        // ------------------------
        // SHOOTER CONTROLS
        // ------------------------
        new Trigger(() -> m_ButtonController.getRawButton(3))
            .whileTrue(new RunCommand(() -> m_robotEndEffector.Shoot(-.15), m_robotEndEffector))
            .whileFalse(new InstantCommand(() -> m_robotEndEffector.Shoot(0.0), m_robotEndEffector));

        new Trigger(() -> m_ButtonController.getRawButton(4))
            .whileTrue(new RunCommand(() -> m_robotEndEffector.Shoot(.1), m_robotEndEffector))
            .whileFalse(new InstantCommand(() -> m_robotEndEffector.Shoot(0.0), m_robotEndEffector));

        new Trigger(() -> m_ButtonController.getRawButton(5))
            .whileTrue(new RunCommand(() -> m_robotEndEffector.Shoot(.45), m_robotEndEffector))
            .whileFalse(new InstantCommand(() -> m_robotEndEffector.Shoot(0.0), m_robotEndEffector));

        // ------------------------
        // INTAKE SEQUENCES
        // ------------------------

        // A) Raise Intake Safely (Button 10)
        new Trigger(() -> m_ButtonController.getRawButton(10))
            .onTrue(
                new RunCommand(() -> m_robotElevator.lift(0.3), m_robotElevator).withTimeout(0.5)
                .andThen(new RunCommand(() -> m_robotIntake.RaiseIntake(0.15), m_robotIntake).withTimeout(1.0))
                .andThen(new InstantCommand(() -> {
                    m_robotElevator.lift(0);
                    m_robotIntake.RaiseIntake(0);
                }, m_robotElevator))
            );

        // B) Lower Intake Safely (Button 11)
        new Trigger(() -> m_ButtonController.getRawButton(11))
            .onTrue(
                new RunCommand(() -> m_robotElevator.lift(0.3), m_robotElevator).withTimeout(0.5)
                .andThen(new RunCommand(() -> m_robotIntake.RaiseIntake(-0.25), m_robotIntake).withTimeout(1.0))
                .andThen(new InstantCommand(() -> {
                    m_robotElevator.lift(0);
                    m_robotIntake.RaiseIntake(0);
                }, m_robotElevator))
            );

        // C) Intake (Button 12)
        new Trigger(() -> m_ButtonController.getRawButton(12))
            .whileTrue(new RunCommand(() -> m_robotIntake.shoot(-0.6), m_robotIntake))
            .whileFalse(new InstantCommand(() -> m_robotIntake.shoot(0), m_robotIntake));

        // D) Outtake + End Effector Slow Spin (Button 13)
        new Trigger(() -> m_ButtonController.getRawButton(13))
            .whileTrue(new RunCommand(() -> {
                m_robotIntake.shoot(0.6);
                m_robotEndEffector.Shoot(0.1);
            }, m_robotIntake))
            .whileFalse(new InstantCommand(() -> {
                m_robotIntake.shoot(0);
                m_robotEndEffector.Shoot(0);
            }, m_robotIntake));

        // E) End Effector High Spin (Button 14)
        new Trigger(() -> m_ButtonController.getRawButton(14))
            .whileTrue(new RunCommand(() -> m_robotEndEffector.Shoot(0.4), m_robotEndEffector))
            .whileFalse(new InstantCommand(() -> m_robotEndEffector.Shoot(0.0), m_robotEndEffector));

        // Button 15 intentionally left unbound
    }

    public void ElevatorBindings() {
        new Trigger(() -> m_ButtonController.getRawButton(1)).onTrue(elevatorBall1);
        new Trigger(() -> m_ButtonController.getRawButton(2)).onTrue(elevatorBall2);
        new Trigger(() -> m_ButtonController.getRawButton(6)).onTrue(elevatorL2);
        new Trigger(() -> m_ButtonController.getRawButton(7)).onTrue(elevatorL3);
        new Trigger(() -> m_ButtonController.getRawButton(8)).onTrue(elevatorL4);

        new Trigger(() -> elevatorL3.isTargetReached() || elevatorL2.isTargetReached())
            .onTrue(new RunCommand(() -> m_robotEndEffector.Shoot(0.25), m_robotEndEffector)
                .withTimeout(1.25)
                .andThen(new InstantCommand(() -> m_robotEndEffector.Shoot(0), m_robotEndEffector)));

        new Trigger(() -> elevatorL4.isTargetReached())
            .onTrue(new RunCommand(() -> m_robotEndEffector.Shoot(0.05), m_robotEndEffector)
                .withTimeout(1.25)
                .andThen(new InstantCommand(() -> m_robotEndEffector.Shoot(0), m_robotEndEffector)));
    }
}
