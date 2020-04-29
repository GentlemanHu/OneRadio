package pers.hu.oneradio;

import org.junit.Test;

import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
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
    public void testResponse(){
        SingleDetailDownloader downloader = new SingleDetailDownloader();
        SingleSongParser parser = new SingleSongParser();
        try {
            //parser.parseDetail(downloader.getData(SmartUrlGetter.getSongUrlDataById(2066494718)));
            parser.parserSong(downloader.getData("http://www.crushing.xyz:3000/dj/hot"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}