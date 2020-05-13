package pers.hu.oneradio;

import org.junit.Test;

import java.util.List;

import pers.hu.oneradio.deal.hand.async.DjSingleTask;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.utils.parser.DjParser;
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
    }

    @Test
    public void testRandom(){

    }

    @Test
    public void testDj(){
        DjParser parser = new DjParser();
        SingleDetailDownloader detailDownloader = new SingleDetailDownloader();

       parser.parseDjs(detailDownloader.getDjHot());
        List<DjDetail> details = parser.getDjs();
        for (DjDetail dj:details
             ) {
            System.out.println(dj.getPicUrl());
        }
    }
}