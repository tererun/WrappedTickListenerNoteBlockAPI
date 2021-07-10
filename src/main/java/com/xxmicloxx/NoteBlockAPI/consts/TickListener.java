package com.xxmicloxx.NoteBlockAPI.consts;

import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import org.bukkit.entity.Player;

public interface TickListener {
    void playTick(Player player, SongPlayer songPlayer, int tick);
}