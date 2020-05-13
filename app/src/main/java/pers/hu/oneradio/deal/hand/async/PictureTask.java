package pers.hu.oneradio.deal.hand.async;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Deprecated
@SuppressWarnings("暂时没有用途，用作记录")
public class PictureTask extends AsyncTask<String, Boolean, Bitmap> {
    private Handler handler;
    private Bitmap bitmap;

    public PictureTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Message message = handler.obtainMessage(1, bitmap);
        message.sendToTarget();
    }
}
