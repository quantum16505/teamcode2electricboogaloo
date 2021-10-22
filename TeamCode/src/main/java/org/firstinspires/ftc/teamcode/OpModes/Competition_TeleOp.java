package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware.HardwareProfile;

@TeleOp(name = "Competition_TeleOp", group = "")
public class Competition_TeleOp extends LinearOpMode {

    HardwareProfile robot = new HardwareProfile();   // Use a Pushbots hardware

    double left;
    double right;
    double drive;
    double turn;
    double max;
    double Spin;
    double maxSpin = 0.1;
    boolean Rbumber = false;
    boolean Lbumber = false;
    double SpeedMod = 0.5;
    double SpeedMod_ClipMin = 0.25;
    double SpeedMod_ClipMax =1;

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
                Spin = gamepad2.left_stick_x;


                if (gamepad1.right_bumper = !Rbumber)
                {
                    SpeedMod = SpeedMod + 0.1;
                    telemetry.addData("", "DUP");
                    telemetry.update();
                }
                if (gamepad1.left_bumper = !Lbumber)
                {
                  SpeedMod = SpeedMod - 0.1;
                    telemetry.addData("", "DD");
                    telemetry.update();
                }
                Rbumber = gamepad1.right_bumper;
                Lbumber = gamepad1.left_bumper;
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


                // Output the safe vales to the motor drives
                robot.RearLeftDrive.setPower(SpeedMod * left);
                robot.RearRightDrive.setPower(SpeedMod * right);
                robot.FrontLeftDrive.setPower(SpeedMod * left);
                robot.FrontRightDrive.setPower(SpeedMod * right);
                robot.Carousel.setPower(Spin * maxSpin);
                telemetry.addData("Power", SpeedMod);
                telemetry.update();

            }

        }
    }
}