package pers.hu.oneradio;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.SongData;
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
            List list=parser.parserSong(downloader.getData(SmartUrlGetter.getHotDjRaw()));
            Iterator it = list.iterator();
            while (it.hasNext()){
                SongData songData = (SongData)it.next();
                System.out.println(
                songData.getId()+"，"+songData.getPicUrl()
                );
            }
            //parser.parserSong(downloader.getData(SmartUrlGetter.getHotDjRaw()));
            //System.out.println(downloader.getDjByCateId(3366));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}