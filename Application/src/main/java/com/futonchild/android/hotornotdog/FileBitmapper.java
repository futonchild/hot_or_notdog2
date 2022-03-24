package com.futonchild.android.hotornotdog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

public class FileBitmapper {
    private Bitmap imageBitmap;
    private File mFile;
    private int targetW, targetH ;
    private final int defaultScaleFactor = 1;

    public FileBitmapper(File file, int viewW, int viewH) {
        Log.d("Bitmap", "Bitmapping...");
        targetW = viewW;
        targetH = viewH;
        mFile = file ;
        final String mCurrentPhotoPath = mFile.getAbsolutePath();
        // Get the dimensions of the bitmap

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        if ( targetW < 0 || targetH < 0 ) {
            Log.d("Bitmap","Negative dim parameter; setting scale factor to " + String.valueOf(defaultScaleFactor));
            targetW = photoW;
            targetH = photoH;
        }
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH); //1;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    }

    public FileBitmapper(File file) {
        this(file, -1, -1);
    }

    public Bitmap getImageBitmap() {
        return imageBitmap ;
    }
}
