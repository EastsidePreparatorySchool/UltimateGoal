/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eastsideprep.ftc.teamcode.whitmer;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

public class cwRobot
{
    public DcMotor backRight = null;
    public DcMotor backLeft = null;
    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public Servo phoneServo = null;

    public static final double PHONE_VERTICAL =  0.4 ;
    public static final double PHONE_AT_45 =  0.2 ;

    // The IMU sensor object
//    BNO055IMU imu;
//    BNO055IMU.Parameters imuParameters;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    DcMotor[] allMotors;
    static final int FRONTLEFT = 0;
    static final int FRONTRIGHT = 1;
    static final int BACKLEFT = 2;
    static final int BACKRIGHT = 3;
    double[] turnFactors;
    double[] driveFactors;
    double[] strafeFactors;
    ElapsedTime driveTimer = new ElapsedTime();

    /* Constructor */
    public cwRobot(){
    }
    // Mecanum wheels - Neverest Orbital 20
    // 1.0 inch is this many full power milliseconds.
    // Was 17.2 msec/inch before calibration with ultrasound and laser.
    // It was observed to be a factor of 1.0286 too large.
    static public final double msPerInch = 16.72;
    static public final double msPerCm = 6.583;
    static public final double msPerDegree = 3.474;

    // Return run time in msec for driving amounts.
    public static int inches(double len) {return (int) Math.round(len * msPerInch);}
    public static int cms(double len)
    {
        return (int) Math.round(len * msPerCm);
    }
    public static int degrees(double d)
    {
        return  (int) Math.round(d * msPerDegree);
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        backRight = hwMap.dcMotor.get("backRight");
        backLeft = hwMap.dcMotor.get("backLeft");
        frontRight = hwMap.dcMotor.get("frontRight");
        frontLeft = hwMap.dcMotor.get("frontLeft");

//        I2cDeviceSynch i2c = hwMap.get(I2cDeviceSynch.class, "TwoMeter");
//        rev2M = new MyVL53L0X(i2c);

        allMotors = new DcMotor[] {frontLeft, frontRight, backLeft, backRight};
        turnFactors = new double[]{1.0, -1.0, 1.0, -1.0};
        driveFactors = new double[] {1.0, 1.0, 1.0, 1.0};
        // The back end of the robot is heavy. This makes the back motors more effective.
        strafeFactors = new double[] {1.0, -1.0, -0.85, 0.85};

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        for (DcMotor m : allMotors)
        {
            m.setPower(0.0);
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

//        imuParameters = new BNO055IMU.Parameters();
//        imuParameters.accelRange = BNO055IMU.AccelRange.G16;
//        imuParameters.gyroRange = BNO055IMU.GyroRange.DPS500;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
//        imu = hwMap.get(BNO055IMU.class, "IMU");
//        imu.initialize(imuParameters);
    }

    public double[] getDrivePowersFromAngle(double angle) {
        double[] unscaledPowers = new double[4];
        unscaledPowers[0] = Math.sin(angle + Math.PI / 4);
        unscaledPowers[1] = Math.cos(angle + Math.PI / 4);
        unscaledPowers[2] = unscaledPowers[1];
        unscaledPowers[3] = unscaledPowers[0];
        return unscaledPowers;
    }

    double runPower = 0.5;

    void Drive(int fullPowerMs, LinearOpMode caller)
    {
        double time = fullPowerMs/1000.0;
        String msg = String.format("Drive: %d",fullPowerMs);
        Log.i("foo", msg);
        AllRun(Math.abs(time/runPower),runPower*Math.signum(time),driveFactors,caller);
    }

    // A turn is clockwise for the given full-power time.
    void Turn(int fullPowerMs, LinearOpMode caller)
    {
        double time = fullPowerMs/1000.0;
        AllRun(Math.abs(time/runPower),runPower*Math.signum(time),turnFactors,caller);
    }

    // A strafe is to the right.
    void Strafe(int fullPowerMs, LinearOpMode caller)
    {
        double time = fullPowerMs/1000.0;
        AllRun(Math.abs(time/runPower),runPower*Math.signum(time),strafeFactors,caller);
    }

//    void TurnToHeading(int heading, LinearOpMode caller)
//    {
//        double diff = normalizeAngle(heading - getHeadingWithLog());
//        String msg = String.format("TurnToHeading: %d diff: %.2f", heading, diff);
//        Log.i("foo",msg);
//        Turn(degrees(diff), caller);
//
//        diff = normalizeAngle(heading - getHeadingWithLog());
//        if (Math.abs(diff) > 2.0) {
//            msg = String.format("TurnToHeading2: %d diff: %.2f", heading, diff);
//            Log.i("foo", msg);
//            Turn(degrees(diff), caller);
//        }
//    }

    // AllRun - Handles all programed movement, including driving, strafing, and turning.

    void AllRun(double runTime, double power, double[] motorFactors, LinearOpMode caller)
    {
        long timeStepMsec = 50;  // 50 msec cycles
        double deltaT = timeStepMsec / 1000.0;
        double remainingIntegral = Math.abs(runTime * power);

        // If there is so little movement that there is no time for
        // ramps, then adjust the power down and time up.
        if (runTime/deltaT < 3.0)
        {
            runTime = 3.000001 * deltaT;
            power = remainingIntegral / runTime * Math.signum(power);
        }
        int powerTicks = (int)Math.floor(runTime/deltaT-3.0);
        resetTickPeriod();
        double lastTime = driveTimer.time();
        double startTime = lastTime;

        // Ramp up
        for (int p=1; caller.opModeIsActive() && p<4; p++) {
            double rampPower = (p * power)/4.0;
            for (int i = 0; i < allMotors.length; i++)
                allMotors[i].setPower(rampPower*motorFactors[i]);
            waitForTick(timeStepMsec);
            double t0 = driveTimer.time();
            remainingIntegral -= (t0 - lastTime) * Math.abs(rampPower);
            lastTime = t0;
        }

        if (powerTicks > 0)
        {
            double absPower = Math.abs(power);
            for (int i=0; i<allMotors.length; i++)
                allMotors[i].setPower(power*motorFactors[i]);
            for (int p=0; caller.opModeIsActive() && p<powerTicks; p++)
            {
                waitForTick(timeStepMsec);
                double newTime = driveTimer.time();
                remainingIntegral -= (newTime - lastTime)*absPower;
                lastTime = newTime;
                if (remainingIntegral <= 0.0) break;
            }
        }
        // Ramp down
        for (int p=3; caller.opModeIsActive() && p>0; p--) {
            if (remainingIntegral <= 0.0) break;
            double rampPower = (p * power)/4.0;
            double absPower = Math.abs(rampPower);
            for (int i = 0; i < allMotors.length; i++)
                allMotors[i].setPower(rampPower*motorFactors[i]);
            long step = (long)((2.0/(p+1))*remainingIntegral/absPower*1000.0);
            waitForTick(step);
            double t0 = driveTimer.time();
            remainingIntegral -= (t0 - lastTime) * absPower;
            lastTime = t0;
        }

        for (int i=0; i<allMotors.length; i++)
            allMotors[i].setPower(0.0);
        waitForTick(400);
    }

//    private double headingOffset = 0.0;
//
//    // The heading is defined to be a clockwise angle measure from 'north'.
//    // This is the negative of the intrinsic quaternion system.
//    double getHeading()
//    {
//        Quaternion q = imu.getQuaternionOrientation();
//        double c = q.w;
//        double s = q.z;
//        double norm = Math.sqrt(c*c+s*s);
//        if (norm < 0.5)
//        {
//            //telemetry.addData("Say", "IMU died!");
//            //telemetry.update();
//            return 0.0;
//        }
//        // The atan2 returns the half-angle, so double it and convert to degrees.
//        double angle = -Math.atan2(s,c) * 360.0 / Math.PI; // The atan gives us the half-angle.
//        // Put the angle in the range from -180 to 180.
//        return normalizeAngle(angle + headingOffset);
//    }
//
//    double getHeadingWithLog()
//    {
//        Quaternion q = imu.getQuaternionOrientation();
//        String msg = String.format("q: %.3f %.3f %.3f %.3f",q.w,q.x,q.y,q.z);
//        Log.i("foo",msg);
//        double c = q.w;
//        double s = q.z;
//        double norm = Math.sqrt(c*c+s*s);
//        if (norm < 0.5)
//        {
//            Log.i("foo","IMU died");
//            //telemetry.addData("Say", "IMU died!");
//            //telemetry.update();
//            return 0.0;
//        }
//        // The atan2 returns the half-angle, so double it and convert to degrees.
//        double angle = -Math.atan2(s,c) * 360.0 / Math.PI; // The atan gives us the half-angle.
//        // Put the angle in the range from -180 to 180.
//        return normalizeAngle(angle + headingOffset);
//    }
//
//    // Return an equivalent angle from -180 to 180.
//    double normalizeAngle(double d)
//    {
//        return d-Math.floor((d + 180.0)/360.0)*360.0;
//    }

    private long lastPeriodTime = 0;
    public void resetTickPeriod()
    {
        period.reset();
        lastPeriodTime = 0;
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs)
    {
        lastPeriodTime += periodMs; // Our estimate of when we return from this call.
        long  remaining = lastPeriodTime - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}

