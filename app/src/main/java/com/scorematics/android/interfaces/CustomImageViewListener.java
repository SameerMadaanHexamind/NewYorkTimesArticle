package com.scorematics.android.interfaces;

import android.graphics.Bitmap;

/**
 * Created by sameer.madaan on 7/18/2016.
 */

public interface CustomImageViewListener {

    void getImageByteArray(byte[] bitmapByteArray);
    void getImageBitmap(Bitmap imageBitmap);
}
