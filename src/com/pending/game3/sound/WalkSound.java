package com.pending.game3.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class WalkSound {
    private Clip audioClip;
    public WalkSound() {

    }

    public void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL audioPath = this.getClass().getResource("/resources/footsteps.wav");
        // File audioFile = new File("resources/blip-sfx-36568.wav");
        assert audioPath != null;
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        audioClip.start();
    }

}