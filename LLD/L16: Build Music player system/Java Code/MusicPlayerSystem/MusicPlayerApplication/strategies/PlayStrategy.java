package MusicPlayerApplication.strategies;

import MusicPlayerApplication.models.Playlist;
import MusicPlayerApplication.models.Song;

public interface PlayStrategy {
    void setPlaylist(Playlist playlist);
    Song next();
    boolean hasNext();
    Song previous();
    boolean hasPrevious();
    default void addToNext(Song song) {}
}
