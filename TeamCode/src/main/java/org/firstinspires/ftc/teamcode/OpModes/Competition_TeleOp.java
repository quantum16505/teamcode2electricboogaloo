package org.firstinspires.ftc.teamcode.OpModes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware.HardwareProfile;

@TeleOp(name = "Competition_TeleOp", group = "")
public class Competition_TeleOp extends LinearOpMode {

    HardwareProfile robot = new HardwareProfile();   // Use a Pushbots hardware
    double arm;
    double left;
    double right;
    double drive;
    double turn;
    double max;
    double carouselSpin;
    double maxSpin = 0.1;
    int Counter = 0;
    double SpeedMod = 0.75;
    double SpeedMod_ClipMin = 0.5;
    double SpeedMod_ClipMax =1;
    double clawSpeed = 0.004;
    double Claw = 0;
    boolean carouselOn = false;
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
//        robot.RearLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.RearRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.RearLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.RearRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {

                drive = -gamepad1.left_stick_y;
                turn  =  gamepad1.right_stick_x;

               arm = gamepad2.left_stick_y;

                if (gamepad1.dpad_up)
                {
                    SpeedMod = SpeedMod + 0.0001;

                }

                if (gamepad1.dpad_down)
                {
                  SpeedMod = SpeedMod - 0.0001;

                }

                // Combine drive and turn for blended motion.
                left  = drive + turn;
                right = drive - turn;

                // Normalize the values so neither exceed +/- 1.0
                max = Math.max(Math.abs(left), Math.abs(right) ) ;
                if (max > 1.0)
                {
                    left /= max;
                    right /= max;
                }

                if (SpeedMod > SpeedMod_ClipMax)
                {
                    SpeedMod = SpeedMod_ClipMax;
                }
                if (SpeedMod < SpeedMod_ClipMin)
                {
                    SpeedMod = SpeedMod_ClipMin;
                }
                robot.RearLeftDrive.setPower(SpeedMod * left);
                robot.RearRightDrive.setPower(SpeedMod * right);
                robot.FrontLeftDrive.setPower(SpeedMod * left);
                robot.FrontRightDrive.setPower(SpeedMod * right);
                robot.ArmMotor.setPower(arm);
                if (gamepad2.dpad_left) {
                    robot.Carousel.setPower(maxSpin);
                    carouselOn = true;
                }
                else if (gamepad2.dpad_left == false) {
                    robot.Carousel.setPower(0);
                    carouselOn = false;
                }
                telemetry.addData("Power", SpeedMod);

                if (gamepad2.left_bumper) {
                    Claw -= clawSpeed;
                }
                else if (gamepad2.right_bumper) {
                    Claw += clawSpeed;
                }
                Claw = Range.clip(Claw, -0.5, 0.5);

                robot.ClawServo.setPosition(0.5 + Claw);

                // Output the safe vales to the motor drives
                telemetry.update();
                Log.d("CurrentPos", String.valueOf(robot.Carousel.getCurrentPosition()));

            }

        }
    }
}