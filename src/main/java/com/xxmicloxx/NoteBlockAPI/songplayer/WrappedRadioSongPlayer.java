package com.xxmicloxx.NoteBlockAPI.songplayer;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.consts.TickListener;
import com.xxmicloxx.NoteBlockAPI.model.*;
import com.xxmicloxx.NoteBlockAPI.model.playmode.ChannelMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import org.bukkit.entity.Player;

public class WrappedRadioSongPlayer extends SongPlayer {

    //protected boolean stereo = true;

    public WrappedRadioSongPlayer(Song song) {
        super(song);
        makeNewClone(com.xxmicloxx.NoteBlockAPI.RadioSongPlayer.class);
    }

    public WrappedRadioSongPlayer(Song song, SoundCategory soundCategory) {
        super(song, soundCategory);
        makeNewClone(com.xxmicloxx.NoteBlockAPI.RadioSongPlayer.class);
    }

    private WrappedRadioSongPlayer(com.xxmicloxx.NoteBlockAPI.SongPlayer songPlayer) {
        super(songPlayer);
    }

    public WrappedRadioSongPlayer(Playlist playlist, SoundCategory soundCategory) {
        super(playlist, soundCategory);
        makeNewClone(com.xxmicloxx.NoteBlockAPI.RadioSongPlayer.class);
    }

    public WrappedRadioSongPlayer(Playlist playlist) {
        super(playlist);
        makeNewClone(com.xxmicloxx.NoteBlockAPI.RadioSongPlayer.class);
    }

    @Override
    public void playTick(Player player, int tick) {
        for (TickListener tickListener : NoteBlockAPI.getTickListeners()) {
            tickListener.playTick(player, this, tick);
        }

        byte playerVolume = NoteBlockAPI.getPlayerVolume(player);

        for (Layer layer : song.getLayerHashMap().values()) {
            Note note = layer.getNote(tick);
            if (note == null) {
                continue;
            }

            float volume = (layer.getVolume() * (int) this.volume * (int) playerVolume * note.getVelocity()) / 100_00_00_00F;

            channelMode.play(player, player.getEyeLocation(), song, layer, note, soundCategory, volume, !enable10Octave);
        }
    }

    /**
     * Returns if the SongPlayer will play Notes from two sources as stereo
     * @return if is played stereo
     * @deprecated
     */
    @Deprecated
    public boolean isStereo(){
        return !(channelMode instanceof MonoMode);
    }

    /**
     * Sets if the SongPlayer will play Notes from two sources as stereo
     * @param stereo
     * @deprecated
     */
    @Deprecated
    public void setStereo(boolean stereo){
        channelMode = stereo ? new MonoMode() : new MonoStereoMode();
    }

    /**
     * Sets how will be {@link Note} played to {@link Player} (eg. mono or stereo). Default is {@link MonoMode}.
     * @param mode
     */
    public void setChannelMode(ChannelMode mode){
        channelMode = mode;
    }
}

