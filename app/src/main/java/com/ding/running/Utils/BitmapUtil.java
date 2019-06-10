package com.ding.running.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName BitmapUtil
 * @Author Leoren
 * @Date 2019/5/22 17:39
 * Description :
 * @Version v1.0
 */
public class BitmapUtil {

    private static Bitmap bitmap = null;

    public static Bitmap netPicToBmp(final String src) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("FileUtil", src);
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);

                    //设置固定大小
                    //需要的大小
                    float newWidth = 200f;
                    float newHeigth = 200f;

                    //图片大小
                    int width = myBitmap.getWidth();
                    int height = myBitmap.getHeight();

                    //缩放比例
                    float scaleWidth = newWidth / width;
                    float scaleHeigth = newHeigth / height;
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeigth);

                    bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
                } catch (IOException e) {
                    // Log exception
                }
            }
        }).start();

        return bitmap;

    }

}
