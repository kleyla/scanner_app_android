package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
//import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.util.List;

public class MlActivity extends AppCompatActivity {

    private Button mBtnSnap;
    private Button mBtnDetect;
    private ImageView mImageView;
    private TextView mTxtView;
    private Bitmap imageBitmap;
    //URI imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ml);

        mBtnSnap = (Button) findViewById(R.id.btnSnap);
        mBtnDetect = (Button) findViewById(R.id.btnDetect);
        mImageView = (ImageView) findViewById(R.id.imageViewMl);
        mTxtView = (TextView) findViewById(R.id.txtViewMl);

        mBtnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarImagenIntent();
            }
        });

        mBtnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectarTexto();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 100;

    private void  enviarImagenIntent(){
        Intent tomarImagenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (tomarImagenIntent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(tomarImagenIntent, REQUEST_IMAGE_CAPTURE);
        }
//        Intent tomarFotogaleria = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        if (tomarFotogaleria.resolveActivity(getPackageManager()) !=null){
//            startActivityForResult(tomarFotogaleria, PICK_IMAGE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //if ((requestCode == PICK_IMAGE || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == RESULT_OK){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Toast.makeText(MlActivity.this, "AQUI",Toast.LENGTH_LONG).show();
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
//        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
//            imageUri = data.getData();
//            foto_gallery.setImageURI(imageUri);
//        }
    }

    private void detectarTexto(){
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision .getInstance().getCloudTextRecognizer();
        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                procesarTexto2(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MlActivity.this, "Fallo",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void procesarTexto(FirebaseVisionText text){
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size()==0){
            Toast.makeText(MlActivity.this, "No hay texto", Toast.LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            Toast.makeText(MlActivity.this, "Texto",Toast.LENGTH_LONG).show();
            String txt = block.getText();
//            mTxtView.setTextSize(10);
//            mTxtView.setText(txt);
            mTxtView.append(txt);
        }
    }

    private void procesarTexto2 (FirebaseVisionText text){
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size()==0){
            Toast.makeText(MlActivity.this, "No hay texto", Toast.LENGTH_LONG).show();
            return;
        }

        for (FirebaseVisionText.TextBlock block: text.getTextBlocks()) {
            String blockText = block.getText();
            Float blockConfidence = block.getConfidence();
            block.getRecognizedLanguages();
            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (FirebaseVisionText.Line line: block.getLines()) {
                String lineText = line.getText();
                Float lineConfidence = line.getConfidence();
                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (FirebaseVisionText.Element element: line.getElements()) {
                    String elementText = element.getText();
                    Float elementConfidence = element.getConfidence();
                    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                    //mTxtView.append(elementText+"\n");
                    //Toast.makeText(MlActivity.this, "For", Toast.LENGTH_LONG).show();
                }
                mTxtView.append(lineText+"\n");
            }
        }
    }

    private void procesarTexto3(FirebaseVisionText text){
        int i;
        for (i=0; i<=5; i++){
            mTxtView.append("hola");
        }

    }
}
