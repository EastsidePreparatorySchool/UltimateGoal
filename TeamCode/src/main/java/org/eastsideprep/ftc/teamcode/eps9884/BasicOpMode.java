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

package org.eastsideprep.ftc.teamcode.eps9884;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Basic OpMode", group="TeleOp")

public class BasicOpMode extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DefaultBot bot;
    double g1LeftAnalogX;
    double g1LeftAnalogY;
    double g1RightAnalogX;
    double g1RightAnalogY;
    double fLM = 0;
    double bLM = 0;
    double fRM = 0;
    double bRM = 0;
    double speed = 0.5;
    double turn;
    double threshold = 0.05;
    int mode = 3;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        bot = new DefaultBot(hardwareMap, telemetry, this);
        bot.init();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }


    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {


        getInput();
        setMotorValues();
        bot.runMotors(fLM, bLM, fRM, bRM);
        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "flm (%.2f),  frm (%.2f), blm (%.2f), brm (%.2f)", fLM, fRM, bLM, bRM);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public void getInput() {
        // checks threshold here as well
        g1RightAnalogX = thresholdCheck(this.gamepad1.right_stick_x);
        g1RightAnalogY = thresholdCheck(this.gamepad1.right_stick_y);
        g1LeftAnalogX = thresholdCheck(this.gamepad1.left_stick_x);
        g1LeftAnalogY = thresholdCheck(this.gamepad1.left_stick_y);
        speed += (this.gamepad1.right_trigger-this.gamepad1.left_trigger) / 2;

        turn = getTurn();
        if (this.gamepad1.dpad_up) {
            // turn while moving
            mode = 0;

        } else if (this.gamepad1.dpad_right) {
            // turn in place
            mode = 1;

        } else if (this.gamepad1.dpad_down) {
            //strafe
            mode = 2;

        } else if (this.gamepad1.dpad_left) {
            //  total control
            mode = 3;

        }
    }
    public double getTurn() {

        return g1RightAnalogX;

    }

    public void setMotorValues() {


        switch (mode) {

            case 0: {
                turnWhileMoving();
                break;
            }
            case 1: {
                turnInPlace();
                break;
            }
            case 2: {
                strafe();
                break;
            }
            case 3: {
                totalControl();
                break;
            }

        }


    }


    public double thresholdCheck(double motor) {
        if (-threshold < motor && motor < threshold) {

            motor = 0.0;

        }

        return motor;
    }


    public void turnInPlace() {
        turn = getTurn();
        fLM = turn * speed;
        bLM = turn * speed;
        fRM = -turn * speed;
        bRM = -turn * speed;


    }

    public void turnWhileMoving() {

        fLM = (-g1LeftAnalogY + turn) * speed;
        bLM = (-g1LeftAnalogY + turn) * speed;
        fRM = (-g1LeftAnalogY + -turn) * speed;
        bRM = (-g1LeftAnalogY + -turn) * speed;


    }

    public void strafe() {

        fLM = g1LeftAnalogX * speed;
        bLM = -g1LeftAnalogX * speed;
        fRM = -g1LeftAnalogX * speed;
        bRM = g1LeftAnalogX * speed;

    }

    public void totalControl() {

        fLM = (-g1LeftAnalogY + turn + g1LeftAnalogX) * speed;
        bLM = (-g1LeftAnalogY + turn + -g1LeftAnalogX) * speed;
        fRM = (-g1LeftAnalogY + -turn + -g1LeftAnalogX) * speed;
        bRM = (-g1LeftAnalogY + -turn + g1LeftAnalogX) * speed;

    }
}
