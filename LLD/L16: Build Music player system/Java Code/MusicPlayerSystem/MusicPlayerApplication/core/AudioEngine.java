package MusicPlayerApplication.core;

import MusicPlayerApplication.models.Song;
import MusicPlayerApplication.device.IAudioOutputDevice;

public class AudioEngine {
    private Song currentSong;
    private boolean songIsPaused;

    public AudioEngine() {
        currentSong = null;
        songIsPaused = false;
    }

    public String getCurrentSongTitle() {
        if (currentSong != null) {
            return currentSong.getTitle();
        }
        return "";
    }

    public boolean isPaused() {
        return songIsPaused;
    }

    public void play(IAudioOutputDevice aod, Song song) {
        if (song == null) {
            throw new RuntimeException("Cannot play a null song.");
        }
        // Resume if same song was paused
        if (songIsPaused && song == currentSong) {
            songIsPaused = false;
            System.out.println("Resuming song: " + song.getTitle());
            aod.playAudio(song);
            return;
        }

        currentSong = song;
        songIsPaused = false;
        System.out.println("Playing song: " + song.getTitle());
        aod.playAudio(song);
    }

    public void pause() {
        if (currentSong == null) {
            throw new RuntimeException("No song is currently playing to pause.");
        }
        if (songIsPaused) {
            throw new RuntimeException("Song is already paused.");
        }
        songIsPaused = true;
        System.out.println("Pausing song: " + currentSong.getTitle());
    }
}
