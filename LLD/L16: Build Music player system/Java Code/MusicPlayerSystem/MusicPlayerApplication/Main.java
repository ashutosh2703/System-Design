package MusicPlayerApplication;

import MusicPlayerApplication.enums.DeviceType;
import MusicPlayerApplication.enums.PlayStrategyType;

public class Main {
    public static void main(String[] args) {
        try {
            MusicPlayerApplication application = MusicPlayerApplication.getInstance();

            // Populate library
            application.createSongInLibrary("Kesariya", "Arijit Singh", "/music/kesariya.mp3");
            application.createSongInLibrary("Chaiyya Chaiyya", "Sukhwinder Singh", "/music/chaiyya_chaiyya.mp3");
            application.createSongInLibrary("Tum Hi Ho", "Arijit Singh", "/music/tum_hi_ho.mp3");
            application.createSongInLibrary("Jai Ho", "A. R. Rahman", "/music/jai_ho.mp3");
            application.createSongInLibrary("Zinda", "Siddharth Mahadevan", "/music/zinda.mp3");

            // Create playlist and add songs
            application.createPlaylist("Bollywood Vibes");
            application.addSongToPlaylist("Bollywood Vibes", "Kesariya");
            application.addSongToPlaylist("Bollywood Vibes", "Chaiyya Chaiyya");
            application.addSongToPlaylist("Bollywood Vibes", "Tum Hi Ho");
            application.addSongToPlaylist("Bollywood Vibes", "Jai Ho");

            // Connect device
            application.connectAudioDevice(DeviceType.BLUETOOTH);

            //Play/pause a single song
            application.playSingleSong("Zinda");
            application.pauseCurrentSong("Zinda");
            application.playSingleSong("Zinda");  // resume

            System.out.println("\n-- Sequential Playback --\n");
            application.selectPlayStrategy(PlayStrategyType.SEQUENTIAL);
            application.loadPlaylist("Bollywood Vibes");
            application.playAllTracksInPlaylist();

            System.out.println("\n-- Random Playback --\n");
            application.selectPlayStrategy(PlayStrategyType.RANDOM);
            application.loadPlaylist("Bollywood Vibes");
            application.playAllTracksInPlaylist();

            System.out.println("\n-- Custom Queue Playback --\n");
            application.selectPlayStrategy(PlayStrategyType.CUSTOM_QUEUE);
            application.loadPlaylist("Bollywood Vibes");
            application.queueSongNext("Kesariya");
            application.queueSongNext("Tum Hi Ho");
            application.playAllTracksInPlaylist();

            System.out.println("\n-- Play Previous in Sequential --\n");
            application.selectPlayStrategy(PlayStrategyType.SEQUENTIAL);
            application.loadPlaylist("Bollywood Vibes");
            application.playAllTracksInPlaylist();

            application.playPreviousTrackInPlaylist();
            application.playPreviousTrackInPlaylist();

        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
        }
    }
}
