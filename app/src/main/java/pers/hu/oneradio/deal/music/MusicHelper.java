package pers.hu.oneradio.deal.music;

import android.content.Context;

import java.util.List;
import java.util.Random;

import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.utils.parser.SingleSongParser;

@Deprecated
public class MusicHelper {
    private  Random random = new Random();
    private  SingleSongParser parser ;
    private  SingleDetailDownloader downloader;
    public MusicHelper(Context context){
        downloader = new SingleDetailDownloader(context);
        parser = new SingleSongParser(context);
    }
    public  String getRandomUrl(){
        List<Song> songs =parser.toSongData(downloader.getDjDetailWithProgramsByRid(792734470,1));
        return songs.get(random.nextInt(songs.size())).getUrl();
    }
}
