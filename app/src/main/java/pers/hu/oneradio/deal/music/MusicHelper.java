package pers.hu.oneradio.deal.music;

import java.util.List;
import java.util.Random;

import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.utils.parser.SingleSongParser;

/**
 * @author Gentleman.Hu
 * @since 1.0
 */
@Deprecated
public class MusicHelper {
    private  Random random = new Random();
    private  SingleSongParser parser = new SingleSongParser();
    private  SingleDetailDownloader downloader = new SingleDetailDownloader();
    public  String getRandomUrl(){
        List<Song> songs =parser.toSongData(downloader.getDjDetailWithProgramsByRid(792734470,1));
        return songs.get(random.nextInt(songs.size())).getUrl();
    }
}
