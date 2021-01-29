package org.eastsideprep.ftc.teamcode.eps9884.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.eastsideprep.ftc.teamcode.eps9884.API.ShooterAPI;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
/*
    This class is made to simplify the shooter testing process
*/

public class ShooterTester {

    public void ShootForTime(int time){
        ShooterAPI shooterAPI = new ShooterAPI();
        while(time != 0){
            shooterAPI.InitiateShooterMotor(true);
            shooterAPI.getring();
        }
        shooterAPI.InitiateShooterMotor(false);
    }
}
