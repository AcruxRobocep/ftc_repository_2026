package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends Command {

    private final Drivetrain drivetrain;
    private final Joystick joystick;

    private boolean robotLigado = false;
    private double limiteVelocidade = 0.40;

    public DriveCommand(Drivetrain drivetrain, Joystick joystick) {
        this.drivetrain = drivetrain;
        this.joystick = joystick;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {

        // Logitech F310/F710 padrão:
        // 8 = Start
        // 7 = Back
        // 1 = A
        // 2 = B
        // 3 = X
        // 4 = Y

        if (joystick.getRawButtonPressed(8)) {
            robotLigado = true;
            limiteVelocidade = 0.40;
        }

        if (joystick.getRawButtonPressed(7)) {
            robotLigado = false;
            drivetrain.stop();
            return;
        }

        if (!robotLigado) {
            drivetrain.stop();
            return;
        }

        if (joystick.getRawButtonPressed(1)) {
            limiteVelocidade = 0.60;
        }

        if (joystick.getRawButtonPressed(3)) {
            limiteVelocidade = 0.80;
        }

        if (joystick.getRawButtonPressed(4)) {
            limiteVelocidade = 1.00;
        }

        if (joystick.getRawButtonPressed(2)) {
            limiteVelocidade = 0.40;
        }

        // Logitech em modo XInput:
        // eixo 1 = analógico esquerdo Y
        // eixo 5 = analógico direito Y
        double LY = joystick.getRawAxis(1) * limiteVelocidade;
        double RY = joystick.getRawAxis(5) * limiteVelocidade;

        double LX = joystick.getRawAxis(0) * limiteVelocidade;
        double RX = joystick.getRawAxis(4) * limiteVelocidade;

        double ME = LY - RX;
        double MD = LY + RX;

        drivetrain.tankDrive(ME,MD);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}