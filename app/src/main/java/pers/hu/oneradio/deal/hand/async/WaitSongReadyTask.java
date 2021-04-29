package pers.hu.oneradio.deal.hand.async;

import android.os.AsyncTask;

import pers.hu.oneradio.deal.listener.OnTaskCompleted;
import pers.hu.oneradio.deal.music.SongListManager;

public class WaitSongReadyTask extends AsyncTask<Void, Boolean, Void> {
    private OnTaskCompleted dealer;
    private int position;

    public WaitSongReadyTask(OnTaskCompleted dealer, int position) {
        this.dealer = dealer;
        this.position = position;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (; ; ) {
            if (SongListManager.getDjDetails().size() - 1 >= position)
                break;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dealer.onTaskCompleted(position);
    }
}
