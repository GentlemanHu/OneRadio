package pers.hu.oneradio;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.tool.music.MusicHelper;
import pers.hu.oneradio.utils.parser.SingleSongParser;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testResponse() {
        SingleDetailDownloader downloader = new SingleDetailDownloader();
        SingleSongParser parser = new SingleSongParser();
       List<Song> songs= parser.toSongData(downloader.getDjDetailWithProgramsByRid(793094397));
        for (Song song:songs
             ) {
            System.out.println(song.getUrl());
        }
    }

    @Test
    public void testRandom(){
        System.out.println(MusicHelper.getRandomUrl());
    }
}