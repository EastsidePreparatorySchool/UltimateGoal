package org.eastsideprep.ftc.teamcode.eps9884;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TestMode", group="Iterative Opmode")

public class TestMode extends OpMode{

    DefaultBot defaultBot;

    @Override
    public void init() {
        defaultBot = new DefaultBot(hardwareMap, telemetry);
        defaultBot.init();

    }

    @Override
    public void loop() {
        defaultBot.testTelemetry();
    }
}
