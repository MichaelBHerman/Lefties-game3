package com.pending.game3.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ItemSound extends Sound {
    private Clip audioClip;
    public ItemSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

    }

    public void playSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        URL audioPath = this.getClass().getResource("/resources/blip-sfx-36568.wav");
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