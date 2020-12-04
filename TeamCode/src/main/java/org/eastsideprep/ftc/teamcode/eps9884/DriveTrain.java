package org.eastsideprep.ftc.teamcode.eps9884;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Boolean rightFlipped; // are the motors on the right side flipped?
    HardwareMap hardwareMap;
    // if false, the motors on the left side are flipped
    Telemetry telemetry;


    /**
     *
     * @param hardwareMap
     * @param telemetry
     * @param rightFlipped Are the motors on the right side flipped?
     */
    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetry, Boolean rightFlipped){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.rightFlipped = rightFlipped;

    }


    public void init(){
        frontLeftMotor = hardwareMap.dcMotor.get("fLM");
        backLeftMotor = hardwareMap.dcMotor.get("bLM");
        frontRightMotor = hardwareMap.dcMotor.get("fRM");
        backRightMotor = hardwareMap.dcMotor.get("bRM");
    }



    public void runMotors(double fL, double bL, double fR, double bR){
        DcMotor[] dt = asArray();
        dt[0].setPower(fL);
        dt[1].setPower(bL);
        dt[2].setPower(fR);
        dt[3].setPower(bR);
        if(rightFlipped){
            fR *= -1;
            bR *= -1;
        }else {
            fL *= -1;
            bL *= -1;
        }

    }

    public void stopAndResetEncoders(){
        DcMotor[] dt = asArray();
        dt[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void runWithEncoders(){
        //stopAndResetEncoders();

        DcMotor[] dt = asArray();
        dt[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dt[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dt[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dt[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runWithoutEncoders(){
        DcMotor[] dt = asArray();
        dt[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public DcMotor[] asArray(){
        DcMotor[] motors = new DcMotor[4];

        motors[0] = frontLeftMotor;
        motors[1] = backLeftMotor;
        motors[2] = frontRightMotor;
        motors[3] = backRightMotor;
        return motors;
    }

    public String encoders(){
        String enc = "";
        DcMotor[] motors = asArray();
        enc+=" fL EncPos: "+motors[0].getCurrentPosition();
        enc+=" bL EncPos: "+motors[1].getCurrentPosition();
        enc+=" fR EncPos: "+motors[2].getCurrentPosition();
        enc+=" bR EncPos: "+motors[3].getCurrentPosition();

        return enc;
    }

}
