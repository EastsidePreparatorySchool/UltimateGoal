package org.eastsideprep.ftc.teamcode.eps9884.API;

import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

public class ShooterAPI {
    //Public class members
    DcMotor ShooterMotor;
    //Constructor
    public ShooterAPI(){

    }


    public void init() {
        ShooterMotor = hardwareMap.get(DcMotor.class, "ShooterMotor" /*Whats the motors actual name?*/);
    }
    public void InitiateShooterMotor(boolean a){

        if (a == true){
            this.ShooterMotor.setPower(10);
        }
//        if(a==false) {
//            this.ShooterMotor.setPower(0);
//        }
    }

    public void ShootForTime(int time){
        while(time != 0){
            InitiateShooterMotor(true);
            getring();
        }
        InitiateShooterMotor(false);
    }
    public static void getring(){
        //Not ready for production, to-do below:
        //How does the ring get to the shooter mechanism in the first place?
        //Is it another motor? = Yes
        //Can we put a sensor to detect the number of rings loaded
    }
}

