package com.futonchild.android.hotornotdog

import android.graphics.Bitmap

interface Classifier {
    fun recognizeImage(bitmap: Bitmap): Result
}