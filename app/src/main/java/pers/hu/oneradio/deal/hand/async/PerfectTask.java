package pers.hu.oneradio.deal.hand.async;

import android.os.AsyncTask;

import pers.hu.oneradio.deal.listener.OnTaskCompleted;

public class PerfectTask extends AsyncTask<Void,Void,Void> {
    private OnTaskCompleted dealer;

    public PerfectTask() {
        super();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //当异步完成后处理
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
