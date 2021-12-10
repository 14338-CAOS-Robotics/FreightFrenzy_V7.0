package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Holonomic OpMode", group="Iterative Opmode")
//@Disabled
public class HolonomicOpMode extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor  FrontRightMotor, FrontLeftMotor, BackRightMotor, BackLeftMotor;
    private DcMotorEx LiftMotor;
    private CRServo CMotor1, CMotor2, intakeServo;
    private int[] liftPos = {0, 400, 1150, 2050, 6000, 8000, 10000};
    private int currentLiftPosition = 0;
    private Servo LeftArm, RightArm;
    HolonomicDrive holonomicDrive;
    boolean YIsPressed = false;
    boolean XIsPressed = false;
    boolean BIsPressed = false;
    boolean AIsPressed = false;
    boolean RBIsPressed = false;
    boolean LBIsPressed = false;
    boolean DPADLeftIsPressed = false;
    boolean DPADRightIsPressed = false;
    boolean LTIsPressed = false;
    boolean RTIsPressed = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontRightMotor  = hardwareMap.get(DcMotor.class, "front_right_drive");
        FrontLeftMotor = hardwareMap.get(DcMotor.class, "front_left_drive");
        BackRightMotor  = hardwareMap.get(DcMotor.class, "back_right_drive");
        BackLeftMotor = hardwareMap.get(DcMotor.class, "back_left_drive");
        LiftMotor = hardwareMap.get(DcMotorEx.class, "lift_motor");
        LeftArm = hardwareMap.get(Servo.class, "left_arm");
        RightArm = hardwareMap.get(Servo.class, "right_arm");
        CMotor1 = hardwareMap.get(CRServo.class, "carousel_motor_1");
        CMotor2 = hardwareMap.get(CRServo.class, "carousel_motor_2");
        intakeServo = hardwareMap.get(CRServo.class, "intake_servo");


        holonomicDrive = new HolonomicDrive(FrontRightMotor, FrontLeftMotor, BackRightMotor, BackLeftMotor);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        LiftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        LiftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        LiftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //LiftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        //LiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

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
        double x = -gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double z = -gamepad1.right_stick_x;
        double RightJoyY = -gamepad1.right_stick_y;

        boolean B_1Button = gamepad1.b;
        boolean A_1Button = gamepad1.a;
        boolean RB_1Button = gamepad1.right_bumper;
        boolean X_1Button = gamepad1.x;
        boolean Y_1Button = gamepad1.y;
        boolean LB_1Button = gamepad1.left_bumper;
        boolean DPL_1Button = gamepad1.dpad_left;
        boolean DPR_1Button = gamepad1.dpad_right;
        float LT_1Button = gamepad1.left_trigger;
        float RT_1Button = gamepad1.right_trigger;





        // Raise Arms or Lower Arms
//        if(Y_1Button == true && YIsPressed == false){
//            YIsPressed = true;
//            holonomicDrive.raiseArms();
//
//        } else if(Y_1Button == true) {
//            YIsPressed == false;
//            holonomicDrive.resetArms();
//        }
//        if(A_1Button == true && AIsPressed == false){
//            AIsPressed = true;
//            holonomicDrive.lowerArms();
//
//        } else if(Y_1Button == true) {
//            AIsPressed == false;
//            holonomicDrive.resetArms();
//        }

        // Control arms holding the element

        // Open wheels
        if(X_1Button == true && XIsPressed == false){
            XIsPressed = true;
            LeftArm.setPosition(0);
            RightArm.setPosition(-0.5);
        }
        else if(X_1Button == false){
            XIsPressed = true;
        }

        //Close
        if(Y_1Button == true && YIsPressed == false) {
            YIsPressed = true;
            LeftArm.setPosition(0.5);
            RightArm.setPosition(0);
        }
        else if(Y_1Button == false) {
            YIsPressed = true;
        }



        if(LB_1Button == true && LBIsPressed == false){
            LBIsPressed = true;
            CMotor1.setPower(0.5);
            CMotor2.setPower(0.5);

        }
        else if(LB_1Button == false){
            LBIsPressed = false;
        }
        if(RB_1Button == true && RBIsPressed == false) {
            RBIsPressed = true;
            CMotor2.setPower(0);
            CMotor1.setPower(0);
        }
        else if(RB_1Button == false) {
            RBIsPressed = false;
        }

        if(LT_1Button != 0 && !LTIsPressed) {
            LTIsPressed = true;
            intakeServo.setPower(0.5);
        }
        else if(LT_1Button == 0) {
            LTIsPressed = false;
            intakeServo.setPower(0);
        }

        if(RT_1Button != 0 && !RTIsPressed) {
            RTIsPressed = true;
            intakeServo.setPower(-0.5);
        }
        else if(RT_1Button == 0) {
            RTIsPressed = false;
            intakeServo.setPower(0);
        }

        // Make the lift go UP
        if(Y_1Button && !YIsPressed && currentLiftPosition < (liftPos.length - 1)){
            YIsPressed = true;
            currentLiftPosition += 1;
            LiftMotor.setTargetPosition(liftPos[currentLiftPosition]);
            LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LiftMotor.setPower(0.5);

        } else if(!Y_1Button) {
            YIsPressed = false;

        }

        //Make the lift go down
        if(X_1Button && !XIsPressed && currentLiftPosition > 0){
            XIsPressed = true;
            currentLiftPosition -= 1;
            LiftMotor.setTargetPosition(liftPos[currentLiftPosition]);
            LiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LiftMotor.setPower(0.5);

        } else if(!X_1Button) {
            XIsPressed = false;
        }




        LiftMotor.setPower(RightJoyY);

        holonomicDrive.teleopDrive(x,y,z);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        //telemetry.addLine("Current Lift position: " + currentLiftPosition + "\nCurrent Lift Ticks: " + liftPos[currentLiftPosition]);
        telemetry.addLine("Current Lift Motor Ticks: "  + LiftMotor.getCurrentPosition());
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}