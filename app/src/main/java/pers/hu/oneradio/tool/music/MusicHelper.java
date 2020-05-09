package pers.hu.oneradio.tool.music;

import java.util.List;
import java.util.Random;

import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.utils.parser.SingleSongParser;

public class MusicHelper {
    private  Random random = new Random();
    private  SingleSongParser parser = new SingleSongParser();
    private  SingleDetailDownloader downloader = new SingleDetailDownloader();
    public  String getRandomUrl(){
        List<Song> songs =parser.toSongData(downloader.getDjDetailWithProgramsByRid(793094397));
        return songs.get(random.nextInt(songs.size())).getUrl();
    }
}
