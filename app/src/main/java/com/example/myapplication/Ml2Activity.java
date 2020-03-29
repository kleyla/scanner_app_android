package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AlertDialog;

//import android.content.Context;
//import android.graphics.Camera;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Helper.GraphicOverlay;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import dmax.dialog.SpotsDialog;

public class Ml2Activity extends AppCompatActivity {

    CameraView cameraView;
    //public AlertDialog waitingDialog;
    GraphicOverlay graphicOverlay;
    Button btnCapture;
    //Context context;
    //AlertDialog waitingDialog;
    android.app.AlertDialog waitingDialog;

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml2);

        //Iniciar Dialog
        waitingDialog = new SpotsDialog.Builder().setContext(Ml2Activity.this).build();
        //waitingDialog = new SpotsDialog(Ml2Activity.this, "write_your_string_here. for ex: Loading..");
        //waitingDialog = new SpotsDialog(Ml2Activity.this, "Write");
       // waitingDialog.dismiss();
        //AlertDialog alertDialog = new SpotsDialog.Builder().setContext(Ml2Activity.this).build();

        //final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(Ml2Activity.this).build();

        //waitingDialog.show();

//        waitingDialog = SpotsDialog.Builder()
//                .setContext(this)
//                .setMessage("Hi")
//                .setCancelable(false)
//                .setTheme(R.style.AppTheme)
//                .setCancelListener(false)
//                .build();

//        waitingDialog.show();
//        waitingDialog.dismiss();

        cameraView = (CameraView) findViewById(R.id.camera_view);

        graphicOverlay = (GraphicOverlay) findViewById(R.id.graphic_overlay);
        btnCapture = (Button) findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.start();
                cameraView.captureImage();
                graphicOverlay.clear();
            }
        });

        //Evnt camer
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }


            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                waitingDialog.show();
                Bitmap bitmap cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(),false);
                cameraView.stop();

                recognizeText(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }
}
