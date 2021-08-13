package com.example.barcoderecognizer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;

public class BarcodeProcessor {

    private boolean ResultsAvailable = false;
    private String BarcodeValue = null;
    private boolean IsBusy = false;
    private static final String TAG = "BarcodeProcessor";

    private final BarcodeScanner barcodeScanner;

    public BarcodeProcessor() {
        barcodeScanner = BarcodeScanning.getClient();
    }

    public void Done() {
        barcodeScanner.close();
    }

    public void DetectInImageFromBytes(byte[] image, int width, int height) {
        if (IsBusy) {
            return;
        }
        Log.d(TAG, "Barcode detection started");
        ResultsAvailable = false;
        IsBusy = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);
        InputImage img = InputImage.fromBitmap(bmp, 0);
        DetectInImage(img);
    }

    protected void DetectInImage(InputImage image) {
        if (image == null){
            Log.d(TAG, "Failed to create an image");
            IsBusy = false;
            return;
        }
        barcodeScanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        IsBusy = false;
                        Log.d(TAG, "Image process done");
                        if (barcodes.isEmpty()) {
                            return;
                        }
                        Barcode barcode = barcodes.get(0);
                        ResultsAvailable = true;
                        BarcodeValue = barcode.getDisplayValue();
                        Log.d(TAG, "Found barcode");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        IsBusy = false;
                        ResultsAvailable = false;
                        Log.d(TAG, "Image process failed");
                    }
                });
    }

    public boolean IsResultAvailable() {
        return ResultsAvailable;
    }

    public char[] ReadResults() {
        if (barcodeScanner == null){
            return null;
        }
        Log.d(TAG, "Result: " + BarcodeValue);
        return BarcodeValue.toCharArray();
    }
}
