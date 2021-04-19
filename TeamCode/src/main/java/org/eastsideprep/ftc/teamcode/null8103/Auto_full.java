///* Copyright (c) 2017 FIRST. All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without modification,
// * are permitted (subject to the limitations in the disclaimer below) provided that
// * the following conditions are met:
// *
// * Redistributions of source code must retain the above copyright notice, this list
// * of conditions and the following disclaimer.
// *
// * Redistributions in binary form must reproduce the above copyright notice, this
// * list of conditions and the following disclaimer in the documentation and/or
// * other materials provided with the distribution.
// *
// * Neither the name of FIRST nor the names of its contributors may be used to endorse or
// * promote products derived from this software without specific prior written permission.
// *
// * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
// * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
// * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
// * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.eastsideprep.ftc.teamcode.null8103;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvInternalCamera;
//import org.openftc.easyopencv.OpenCvPipeline;
//
////TODO: THIS FILE IS VERY BROKEN SO DONT USE YET
//
////simple skystone detection teleop, code from https://github.com/OpenFTC/EasyOpenCV/blob/master/examples/src/main/java/org/openftc/easyopencv/examples/SkystoneDeterminationExample.java
//@Autonomous(name = "full auto")
//
//public class Auto_full extends LinearOpMode {
//
//    @Override
//    public void runOpMode() {
//
//        //these scary lines open the camera streaming
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//
//        OpenCvInternalCamera phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
//
//        //set the pipeline written below to the camera
//        RingDetectorPipeline RingDetectorPipeline = new RingDetectorPipeline();
//        phoneCam.setPipeline(RingDetectorPipeline);
//
//        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
//
//        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
//            }
//        });
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            telemetry.addData("The skystone is ", RingDetectorPipeline.getResult());
//            telemetry.update();
//
//            // Don't burn CPU cycles busy-looping in this sample
//            sleep(1000);
//        }
//
//    }
//
//    private static class RingDetectorPipeline extends OpenCvPipeline {
//
//        //to find the skystone:
//        //look at three rectangles
//        //convert from RGB to YCrCb
//        //extract Cb channel (stones are yellow so lots of contrast in the blue channel against skystones)
//
//        //colors (for drawing rectangles)
//
//        static final Scalar BLUE = new Scalar(0, 0, 255);
//        static final Scalar GREEN = new Scalar(0, 255, 0);
//
//        /*
//         * The core values which define the location and size of the sample regions
//         */
//        static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(70, 98);
//        static final Point REGION2_TOPLEFT_ANCHOR_POINT = new Point(181, 98);
//        static final Point REGION3_TOPLEFT_ANCHOR_POINT = new Point(290, 98);
//        static final int REGION_WIDTH = 20;
//        static final int REGION_HEIGHT = 20;//small sample space ensures that only we only check the stone
//
//        //the points needed to define the three rectangles
//        Point region1_pointA = new Point(
//                REGION1_TOPLEFT_ANCHOR_POINT.x,
//                REGION1_TOPLEFT_ANCHOR_POINT.y);
//        Point region1_pointB = new Point(
//                REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
//                REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
//        Point region2_pointA = new Point(
//                REGION2_TOPLEFT_ANCHOR_POINT.x,
//                REGION2_TOPLEFT_ANCHOR_POINT.y);
//        Point region2_pointB = new Point(
//                REGION2_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
//                REGION2_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
//        Point region3_pointA = new Point(
//                REGION3_TOPLEFT_ANCHOR_POINT.x,
//                REGION3_TOPLEFT_ANCHOR_POINT.y);
//        Point region3_pointB = new Point(
//                REGION3_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,
//                REGION3_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);
//
//
//        /*
//         * Working variables
//         */
//        Mat region1_Cb, region2_Cb, region3_Cb;
//        Mat YCrCb = new Mat();
//        Mat Cb = new Mat();
//        int avg1, avg2, avg3;
//
//
//        //this is separate from the main processing method because we want to set Cb once from the first frame
//        //to ensure that the three regions don't change if/when Cb is re-written to
//        @Override
//        public void init(Mat firstFrame) {
//
//            //openCV likes 2 params: input and output variable to set to
//            Imgproc.cvtColor(firstFrame, YCrCb, Imgproc.COLOR_RGB2YCrCb);
//            Core.extractChannel(YCrCb, Cb, 2);
//
//            region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));
//            region2_Cb = Cb.submat(new Rect(region2_pointA, region2_pointB));
//            region3_Cb = Cb.submat(new Rect(region3_pointA, region3_pointB));
//        }
//
//        String outputMessage;
//
//        @Override
//        public Mat processFrame(Mat input) {
//
//            Mat matYCrCb = new Mat();
//            Mat topRect;
//            Mat bottomRect = new Mat();
//
//            Imgproc.cvtColor(input, matYCrCb, Imgproc.COLOR_RGB2YCrCb);
//
//            Rect topRect = new Rect(
//                    (int) (matYCrCb.width() * topRectWidthPercentage),
//                    (int) (matYCrCb.height() * topRectHeightPercentage),
//                    rectangleWidth,
//                    rectangleHeight
//            );
//
//            Rect bottomRect = new Rect(
//                    (int) (matYCrCb.width() * bottomRectWidthPercentage),
//                    (int) (matYCrCb.height() * bottomRectHeightPercentage),
//                    rectangleWidth,
//                    rectangleHeight
//            );
//
//            //The rectangle is drawn into the mat
//            drawRectOnToMat(input, topRect, new Scalar(255, 0, 0));
//            drawRectOnToMat(input, bottomRect, new Scalar(0, 255, 0));
//
//
//            topBlock = matYCrCb.submat(topRect);
//            bottomBlock = matYCrCb.submat(bottomRect);
//
//            Core.extractChannel(bottomBlock, matCbBottom, 2);
//            Core.extractChannel(topBlock, matCbTop, 2);
//
//            Scalar bottomMean = Core.mean(matCbBottom);
//            Scalar topMean = Core.mean(matCbTop);
//
//            bottomAverage = bottomMean.val[0];
//            topAverage = topMean.val[0];
//
//
//        }
//
//        public String getResult() {
//            return outputMessage;
//        }
//    }
//}
//
