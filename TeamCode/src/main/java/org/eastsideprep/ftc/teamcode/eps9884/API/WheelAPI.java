package org.eastsideprep.ftc.teamcode.eps9884.API;

import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

public class WheelAPI {
    DcMotor fr = hardwareMap.get(DcMotor.class, "fr");
    DcMotor fl = hardwareMap.get(DcMotor.class, "fl");
    DcMotor br = hardwareMap.get(DcMotor.class, "br");
    DcMotor bl = hardwareMap.get(DcMotor.class, "bl");
    public void movedistance(int direction, int distance){
    //direction 1=forward 2=right 3=backward 4=left
    //combine two movedistances to go diagonally
    //distance in [to be chosen]
        int posfr = this.fr.getCurrentPosition();
        int posfl = this.fl.getCurrentPosition();
        int posbr = this.br.getCurrentPosition();
        int posbl = this.bl.getCurrentPosition();
        if (direction == 1) {
            fr.setTargetPosition(distance);
        }
    }
}
