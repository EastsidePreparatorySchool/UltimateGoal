package org.eastsideprep.ftc.teamcode.eps9884;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Drive forward, turn around drive back", group="Autonomous Opmode")
public class BasicAutonomous extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DefaultBot bot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        bot = new DefaultBot(hardwareMap, telemetry, this);
        bot.init();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        long timerMS = 1000;
        bot.driveTime(timerMS, 0.5);
        bot.turnToAngle(Math.toRadians(180), 0.25, 1.0);
        bot.driveTime(timerMS, 0.5);


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Show the elapsed game time and wheel power.
            bot.testTelemetry();
            telemetry.update();
        }
    }
}
