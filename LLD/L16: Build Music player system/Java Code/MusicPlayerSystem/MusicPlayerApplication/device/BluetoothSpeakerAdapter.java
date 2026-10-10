package MusicPlayerApplication.device;

import MusicPlayerApplication.models.Song;
import MusicPlayerApplication.external.BluetoothSpeakerAPI;

public class BluetoothSpeakerAdapter implements IAudioOutputDevice {
    private BluetoothSpeakerAPI bluetoothApi;

    public BluetoothSpeakerAdapter(BluetoothSpeakerAPI api) {
        this.bluetoothApi = api;
    }

    @Override
    public void playAudio(Song song) {
        String payload = song.getTitle() + " by " + song.getArtist();
        bluetoothApi.playSoundViaBluetooth(payload);
    }
}
