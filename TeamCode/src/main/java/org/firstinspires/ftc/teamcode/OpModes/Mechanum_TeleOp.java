package org.firstinspires.ftc.teamcode.OpModes;

import android.util.Log;
import org.firstinspires.ftc.teamcode.OpModes.Competition_TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Hardware.HardwareProfile;

@TeleOp(name = "Mechanum_TeleOp", group = "")
public class Mechanum_TeleOp extends LinearOpMode {

    HardwareProfile robot = new HardwareProfile();   // Use a Pushbots hardware
    double arm;
    double ArmSpeedMod = 0.4;
    double leftTurn;
    double rightTurn;
    double strafeLeft;
    double strafeRight;
    double drive;
    double turn;
    double max;
    double maxSpin = 0.35;
    double SpeedMod = 0.75;
    double SpeedMod_ClipMin = 0.5;
    double SpeedMod_ClipMax =1;
    double clawSpeed = 0.004;
    double Claw = 0;
    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.ArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        robot.FrontRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.FrontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.RearRightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.RearLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);



        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {

                drive = -gamepad1.left_stick_y;
                turn  =  gamepad1.right_stick_x;
                strafeLeft = -gamepad1.left_stick_x;
                strafeRight = gamepad1.left_stick_x;

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
                leftTurn  = drive + turn;
                rightTurn = drive - turn;

                // Normalize the values so neither exceed +/- 1.0
                max = Math.max(Math.abs(leftTurn), Math.abs(rightTurn) ) ;
                if (max > 1.0)
                {
                    leftTurn /= max;
                    rightTurn /= max;
                }

                if (SpeedMod > SpeedMod_ClipMax)
                {
                    SpeedMod = SpeedMod_ClipMax;
                }
                if (SpeedMod < SpeedMod_ClipMin)
                {
                    SpeedMod = SpeedMod_ClipMin;
                }
                robot.FrontLeftDrive.setPower(SpeedMod * (drive + turn + strafeLeft));
                robot.RearLeftDrive.setPower(SpeedMod * (drive + (turn + strafeRight)));
                robot.FrontRightDrive.setPower(SpeedMod * (-drive + (turn + strafeRight)));
                robot.RearRightDrive.setPower(SpeedMod * (-drive + (turn + strafeLeft)));




                robot.ArmMotor.setVelocity(arm * 500);



                if (gamepad2.dpad_left) {
                    robot.Carousel.setPower(-maxSpin);
                }
                else if (gamepad2.dpad_right) {
                    robot.Carousel.setPower(maxSpin);
                }
                else {
                    robot.Carousel.setPower(0);
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
                Log.d("Arm" , String.valueOf(robot.ArmMotor.getCurrentPosition()));
            }

        }
    }
}
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Jimothy
//Lee