package org.eastsideprep.ftc.teamcode.null8103;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class YellowJacket3_7 implements Motor {

    private DcMotor m_motor;
    private double resetVal;

    public static final double TICKS_PER_REV = 103.6;

    public YellowJacket3_7(HardwareMap hMap, String name) {
        m_motor = hMap.get(DcMotor.class, name);
    }

    @Override
    public void set(double speed) {
        m_motor.setPower(speed);
    }

    @Override
    public double get() {
        return m_motor.getPower();
    }

    @Override
    public void setInverted(boolean isInverted) {
        m_motor.setDirection(!isInverted ? DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE);
    }

    @Override
    public boolean getInverted() {
        return m_motor.getDirection() == DcMotor.Direction.REVERSE;
    }

    @Override
    public void disable() {
        m_motor.close();
    }

    @Override
    public String getDeviceType() {
        return this.getClass().toString();
    }

    @Override
    public void pidWrite(double output) {
        set(output);
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    public double getEncoderCount() {
        return m_motor.getCurrentPosition() - resetVal;
    }

    public void resetEncoder() {
        resetVal += getEncoderCount();
    }

    public double getNumRevolutions() {
        return getEncoderCount() / TICKS_PER_REV;
    }

}
