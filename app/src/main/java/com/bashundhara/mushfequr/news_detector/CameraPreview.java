package com.bashundhara.mushfequr.news_detector;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder mHolder;
    private   Camera mCamera;
    private static  final String TAG = CameraPreview.class.getSimpleName();

    public CameraPreview(Context context, Camera camera) {

        super(context);
        mCamera= camera;
        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //TAKE CARE OF FIXING CAMERA Orientataion


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        //TAKE CARE OF GETTING RID of CAMERA



    }
}
