package edu.wpi.first.wpilibj.experimental.command;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.experimental.command.SendableSubsystemBase;

public abstract class PIDSubsystem extends SendableSubsystemBase {
    private final PIDController m_controller;
    private final PIDOutput m_output = this::usePIDOutput;
    private final PIDSource m_source = new PIDSource() {
        @Override
        public void setPIDSourceType(PIDSourceType pidSource) {
        }

        @Override
        public PIDSourceType getPIDSourceType() {
            return PIDSourceType.kDisplacement;
        }

        @Override
        public double pidGet() {
            return returnPIDInput();
        }
    };

    public PIDSubsystem(String name, double p, double i, double d) {
        super();
        m_controller = new PIDController(p, i, d, m_source, m_output);
    }


    public PIDSubsystem(String name, double p, double i, double d, double f) {
        super();
        m_controller = new PIDController(p, i, d, f, m_source, m_output);
    }

    protected abstract double returnPIDInput();

    protected abstract void usePIDOutput(double output);

    public PIDController getPIDController() {
        return m_controller;
    }

    public void setSetpoint(double setpoint) {
        m_controller.setSetpoint(setpoint);
    }

    public double getSetpoint() {
        return m_controller.getSetpoint();
    }

    public void setInputRange(double minimumInput, double maximumInput) {
        m_controller.setInputRange(minimumInput, maximumInput);
    }

    public void setOutputRange(double minimumOutput, double maximumOutput) {
        m_controller.setOutputRange(minimumOutput, maximumOutput);
    }

    public void setAbsoluteTolerance(double t) {
        m_controller.setAbsoluteTolerance(t);
    }

    public void setPercentTolerance(double p) {
        m_controller.setPercentTolerance(p);
    }

    public boolean onTarget() {
        return m_controller.onTarget();
    }

    public void enable() {
        m_controller.enable();
    }

    public void disable() {
        m_controller.disable();
    }
}
