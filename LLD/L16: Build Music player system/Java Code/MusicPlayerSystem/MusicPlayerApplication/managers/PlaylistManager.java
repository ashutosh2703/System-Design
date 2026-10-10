package MusicPlayerApplication.managers;

import MusicPlayerApplication.models.Playlist;
import MusicPlayerApplication.models.Song;

import java.util.HashMap;
import java.util.Map;

public class PlaylistManager {
    private static PlaylistManager instance = null;
    private Map<String, Playlist> playlists;

    private PlaylistManager() {
        playlists = new HashMap<>();
    }

    public static synchronized PlaylistManager getInstance() {
        if (instance == null) {
            instance = new PlaylistManager();
        }
        return instance;
    }

    public void createPlaylist(String name) {
        if (playlists.containsKey(name)) {
            throw new RuntimeException("Playlist \"" + name + "\" already exists.");
        }
        playlists.put(name, new Playlist(name));
    }

    public void addSongToPlaylist(String playlistName, Song song) {
        if (!playlists.containsKey(playlistName)) {
            throw new RuntimeException("Playlist \"" + playlistName + "\" not found.");
        }
        playlists.get(playlistName).addSongToPlaylist(song);
    }

    public Playlist getPlaylist(String name) {
        if (!playlists.containsKey(name)) {
            throw new RuntimeException("Playlist \"" + name + "\" not found.");
        }
        return playlists.get(name);
    }
}
