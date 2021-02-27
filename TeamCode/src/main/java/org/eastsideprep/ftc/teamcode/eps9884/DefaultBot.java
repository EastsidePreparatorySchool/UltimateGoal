package org.eastsideprep.ftc.teamcode.eps9884;




import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultBot implements Robot {

    private HardwareMap hardwareMap;
    private IMU imu;
    private DriveTrain driveTrain;
    OpMode opMode;
    Telemetry telemetry;

    /**
     * @param hardwareMap
     * @param telemetry
     */
    DefaultBot(HardwareMap hardwareMap, Telemetry telemetry, OpMode opMode) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void init() {

        imu = new IMU(hardwareMap, telemetry);
        imu.init();

        driveTrain = new DriveTrain(hardwareMap, telemetry, true);
        driveTrain.init();

    }

    public void runMotors(double fL, double bL, double fR, double bR) {
        driveTrain.runMotors(fL, bL, fR, bR);

    }

    public void testTelemetry() {
        imu.testTelemetry();
        driveTrain.testTelemetry();
    }

    /**
     * Turns right by default, left if speed negative. Turns towards angle
     *
     * @param angle  target angle
     * @param speed  motor speed
     * @param margin margin for error
     */
    public void turnToAngle(Double angle, Double speed, Double margin) {
        if (!driveTrain.getRunningWithEncoders()) {
            driveTrain.runWithEncoders();
        }
        runMotors(speed, speed, -speed, -speed);

       while (Math.abs(angle - imu.getAngle()) > margin){
           testTelemetry();
            telemetry.update();
        }
    }

    /**
     *
     * @param millis
     * @param speed
     */
    public void driveTime(long millis, Double speed){
        long start = System.currentTimeMillis();
        runMotors(speed, speed, speed, speed);

        while(Math.abs(start-System.currentTimeMillis())<millis){
            testTelemetry();
            telemetry.update();
        }
    }



    /**
     *
     * @param millis
     * @param speed
     * @param check this is a function (supplier), no input
     *  one boolean output. It produces a condition to be
     *  constantly checked while running the while loop
     *  if true, close the loop, stop driving.
     *  so that you can do stuff like check if opMode was shut off or
     *  check if something is in view or whatever else.
     */
    //@android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    void driveTime(long millis, Double speed, Function<DefaultBot, Boolean> check){
        long start = System.currentTimeMillis();
        Boolean condition = false;
        runMotors(speed, speed, speed, speed);

        while(Math.abs(start-System.currentTimeMillis())<millis && !condition){
            testTelemetry();
            telemetry.update();
            //condition = check.apply(this);
        }



    }






}
