package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Processors.Base_Processor;
import org.firstinspires.ftc.teamcode.Processors.Crater_Movement_Auto_Processor;
import org.firstinspires.ftc.teamcode.Processors.RobotInterface;
import org.firstinspires.ftc.teamcode.Processors.Sampling_to_Depo_Auto_Processor;
import org.firstinspires.ftc.teamcode.Processors.Latching_Auto_Processor;
import org.firstinspires.ftc.teamcode.Processors.Processor;
import org.firstinspires.ftc.teamcode.Processors.Robot;
import org.firstinspires.ftc.teamcode.Processors.Sampling_Auto_Processor;
import org.firstinspires.ftc.teamcode.Processors.Team_Marker_Auto_Processor;

import java.util.Arrays;
import java.util.List;

//import org.firstinspires.ftc.teamcode.Processors.Latching_Auto_Processor;
//import org.firstinspires.ftc.teamcode.Processors.Latching_Processor;

public class Base_Autonomous extends LinearOpMode implements RobotInterface {
    public static Hardware_Pushbot hardwarePushbot = new Hardware_Pushbot();
    public boolean latchmode;
    public boolean depo;
    List<Base_Processor> Autonomous_processors;


    Robot robot;

    public Base_Autonomous(boolean Depo) {
        this.depo = Depo;
    }

    public Base_Autonomous() {

    }


    public Robot getRobot() {
        return robot;
    }

    public Hardware_Pushbot getHardware_Pushbot(){ return hardwarePushbot;}

    @Override

    public void runOpMode() {
        hardwarePushbot.init(this.hardwareMap);
        robot  = new Robot(this);
        robot.init();
        Autonomous_processors = Arrays.asList(
                //new Latching_Auto_Processor(this),
                //new Latching_Movement_Auto_Processor(this),
                new Sampling_Auto_Processor(this),
                new Sampling_to_Depo_Auto_Processor(this, depo),
                //new Retreat_Auto_Processor(this, depo),
                new Team_Marker_Auto_Processor(this)
                //new Crater_Movement_Auto_Processor(this,depo)
                /*new GlyphClawAutoProcessor(this),

                new PictographProcessor(this),

                new JewelSensorAutoArmProcessor(this, baseColor),

                new GlyphEncoderAutoProcessors(this, baseColor, relicSide)
*/

        );


        for (Processor processor : Autonomous_processors) {

            processor.init();

        }

        // Send telemetry message to signify robot waiting;

        telemetry.addData("Say", "Hello Driver");    //

        telemetry.update();


        // Wait for the game to start (driver presses PLAY)

        waitForStart();


        // run until the end of the match (driver presses STOP)

        for (Processor processor : Autonomous_processors) {

            if (opModeIsActive()) {
                processor.process();
            }

            // Pace this loop so jaw action is reasonable speed.

            sleep(50);

            telemetry.update();


        }

    }


    public Processor getProcessors(Class clazz) {

        for (Processor processor : Autonomous_processors)
            if (clazz.isInstance(processor)) {

                return processor;

            }

        return null;

    }




}