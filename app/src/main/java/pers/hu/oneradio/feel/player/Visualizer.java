package pers.hu.oneradio.feel.player;

import android.app.Activity;

import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.lzx.starrysky.StarrySky;

import pers.hu.oneradio.R;

public class Visualizer {
    private BlastVisualizer mVisualizer;
    private static Visualizer visualizer = null;

    private Visualizer(BlastVisualizer blastVisualizer) {        //get reference to visualizer
        mVisualizer = blastVisualizer;
        //TODO: init MediaPlayer and play the audio

        //get the AudioSessionId from your MediaPlayer and pass it to the visualizer
//        int audioSessionId = mAudioPlayer.getAudioSessionId();
//        if (audioSessionId != -1)
//            mVisualizer.setAudioSessionId(audioSessionId);
    }

    public static Visualizer getVisulizer(BlastVisualizer blastVisualizer) {
        synchronized (Visualizer.class) {
            if (visualizer == null)
                visualizer = new Visualizer(blastVisualizer);
        }
        return visualizer;
    }

}
