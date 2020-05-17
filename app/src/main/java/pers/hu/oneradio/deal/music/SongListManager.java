package pers.hu.oneradio.deal.music;


import com.lzx.starrysky.provider.SongInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import pers.hu.oneradio.net.model.DjDetail;

public class SongListManager {
    private static Hashtable<Integer, ArrayList<SongInfo>> allSongPlaylist = new Hashtable<>();
    private static ArrayList<DjDetail> djDetails = new ArrayList<>();

    public SongListManager() {
    }

    public static ArrayList<DjDetail> getDjDetails() {
        return djDetails;
    }

    public static void addDjDetail(DjDetail dj) {
        djDetails.add(dj);
    }

    public static void addPlayList(Integer position, ArrayList<SongInfo> songlist) {
        allSongPlaylist.put(position, songlist);
    }

    public static void removePlayList(Integer position, ArrayList<SongInfo> songlist) {
        allSongPlaylist.remove(position, songlist);
    }

    public static ArrayList<SongInfo> getSongPlaylist(Integer position) {
        return allSongPlaylist.get(position);
    }
}
