package org.eastsideprep.ftc.teamcode.eps9884.API;

import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
/*To Do:
*/


public class ShooterAPI {
    //Public class members
    DcMotor ShooterMotor1;
    DcMotor ShooterMotor2;
    //Constructor
    public ShooterAPI(){

    }


    public void init() {
        ShooterMotor1 = hardwareMap.get(DcMotor.class, "ShooterMotor1" /*Whats the motors actual name?*/);
        ShooterMotor2 = hardwareMap.get(DcMotor.class, "ShooterMotor2" /*Whats the motors actual name?*/);
    }
    public void InitiateShooterMotor(int a){

        this.ShooterMotor1.setPower(a);
        this.ShooterMotor2.setPower(a);
    }

    public void ShootForTime(int time){
        while(time != 0){
            InitiateShooterMotor(10);
            getring();
        }
        InitiateShooterMotor(0);
    }
    public static void getring(){
        //Not ready for production, to-do below:
        //How does the ring get to the shooter mechanism in the first place?
        //Is it another motor? = Yes
        //Can we put a sensor to detect the number of rings loaded
    }
}

