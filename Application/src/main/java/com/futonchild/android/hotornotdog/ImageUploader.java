package com.futonchild.android.hotornotdog;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImageUploader implements Runnable {

    private String uploadURL;
    private Context mCtx;
    private Bitmap mBitmap ;
    private String mSaveType;


    public ImageUploader(String saveType, Bitmap bitmap, Context ctx) {
        mSaveType = saveType;
        mBitmap = bitmap;
        mCtx = ctx;
        uploadURL = mCtx.getString(R.string.url) ;
    }
    
    public void run() {

        Log.d("UL", "Uploading to " + uploadURL);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) ;
        final String imageFileName = mSaveType + "_" + timeStamp ;
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST, uploadURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String Response = jsonObject.getString("response") ;
                                    Log.d("Server resp", Response);
                                    Toast.makeText(mCtx, Response, Toast.LENGTH_LONG ).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void  onErrorResponse(VolleyError error) {
                                Log.d("UL", "Volley error:"+ error.getMessage());
                                Toast.makeText(mCtx, "Network error encountered while uploding " + mSaveType + " image", Toast.LENGTH_LONG ).show();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("name",  imageFileName.trim()) ;
                        params.put("image", imageToString(mBitmap)) ;
                        params.put("type",  mSaveType) ;

                        return params ;
                    }
                };
        UploadSingleton.getInstance(mCtx).addToRequestQueue(stringRequest);

        //UploadSingleton.getInstance(mCtx).mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,PendingIntent.getActivity().getIntentSender());
    }

    private String imageToString(@NonNull Bitmap bitmap) {
        String returnString = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imgBytes = baos.toByteArray();
            returnString = Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (NullPointerException ex) {
            Log.e("UL", ex.getMessage());
        }
        return returnString;

    }
}
