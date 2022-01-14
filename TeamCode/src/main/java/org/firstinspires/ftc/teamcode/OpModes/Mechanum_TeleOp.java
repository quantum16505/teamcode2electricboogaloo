package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.HardwareProfile;

@TeleOp(name = "Mechanum_TeleOp", group = "")
public class Mechanum_TeleOp extends LinearOpMode {

    HardwareProfile robot = new HardwareProfile();   // Use a Pushbots hardware
    double rx;
    double y;
    double x;
    double max;
    double maxSpin = 0.7;
    double SpeedMod = 0.75;
    double SpeedMod_ClipMin = 0.5;
    double SpeedMod_ClipMax =1;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                DriveControls();

                CarouselControls();

                ArmControls();

                IntakeControls();

                telemetry.update();
            }

        }
    }
    private void IntakeControls() {
        if (gamepad2.left_bumper) {
            robot.IntakeLeft.setPower(1);
            robot.IntakeRight.setPower(-1);
        }
        else if (gamepad2.right_bumper) {
            robot.IntakeLeft.setPower(-1);
            robot.IntakeRight.setPower(1);
        }
        else {
            robot.IntakeLeft.setPower(0);
            robot.IntakeRight.setPower(0);
        }
    }
    private void ArmControls() {
        telemetry.addData("ArmStick" , gamepad1.left_stick_y );
        if(gamepad2.left_stick_y > 0.05 || gamepad2.left_stick_y < 0.05)   {
            robot.ArmServo.setPower(gamepad2.left_stick_y);
        } else {
            robot.ArmServo.setPower(0);
        }
    }

    private void CarouselControls() {
        if (gamepad2.dpad_left) {
            robot.Carousel.setPower(-maxSpin);
        }
        else if (gamepad2.dpad_right) {
            robot.Carousel.setPower(maxSpin);
        }
        else {
            robot.Carousel.setPower(0);
        }
    }

    private void DriveControls() {
        y = -gamepad1.left_stick_y;
        x =  gamepad1.right_stick_x * 1.1;
        rx = gamepad1.left_stick_x;
        if (gamepad1.dpad_up)
        {
            SpeedMod = SpeedMod + 0.0001;

        }

        if (gamepad1.dpad_down)
        {
            SpeedMod = SpeedMod - 0.0001;

        }

        if (SpeedMod > SpeedMod_ClipMax)
        {
            SpeedMod = SpeedMod_ClipMax;
        }
        if (SpeedMod < SpeedMod_ClipMin)
        {
            SpeedMod = SpeedMod_ClipMin;
        }
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx),1);
        robot.FrontLeftDrive.setPower((y + x + rx) / denominator);
        robot.RearLeftDrive.setPower((y - rx + x) / denominator);
        robot.FrontRightDrive.setPower((y - rx - x) / denominator);
        robot.RearRightDrive.setPower( (y + rx - x) / denominator);

        telemetry.addData("Power", SpeedMod);
    }
}
//No Jimothy
//Lee