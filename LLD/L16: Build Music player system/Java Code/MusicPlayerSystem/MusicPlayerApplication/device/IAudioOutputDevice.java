package MusicPlayerApplication.device;

import MusicPlayerApplication.models.Song;

public interface IAudioOutputDevice {
    void playAudio(Song song);
}
