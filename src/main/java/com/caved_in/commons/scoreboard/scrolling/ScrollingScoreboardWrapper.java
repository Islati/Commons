package com.caved_in.commons.scoreboard.scrolling;

import com.caved_in.commons.scoreboard.AbstractScoreboardWrapper;
import com.caved_in.commons.scoreboard.BoardManager;
import com.caved_in.commons.scoreboard.ScoreboardInformation;
import com.caved_in.commons.scoreboard.threads.UpdatePlayerScrollingScoreboardThread;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.UUID;

public class ScrollingScoreboardWrapper extends AbstractScoreboardWrapper {
    private Scoreboard scoreboard;

    private DisplaySlot slot;

    private List<BukkitRunnable> threads = Lists.newArrayListWithExpectedSize(2);

    public ScrollingScoreboardWrapper(@NonNull BoardManager manager, @NonNull Scoreboard scoreboard, @NonNull ScoreboardInformation info) {
        super(manager, scoreboard, info);
    }

    @Override
    public ScrollingScoreboardWrapper assign(Player p) {
        UUID id = p.getUniqueId();

        Objective obj = scoreboard.getObjective(slot);

        obj.setDisplayName(getInfo().getTitle().toString());

//		ScrollingTitleCycler title = new ScrollingTitleCycler(getManager(),id,getInfo());
        //Create the thread that manages the title cycler. (If required)
//		threads.add(title);

        //Create the thread that updates this players scoreboard.
        threads.add(new UpdatePlayerScrollingScoreboardThread(this));

        //Assign the player their scoreboard.
        p.setScoreboard(scoreboard);
        return this;
    }

    public List<BukkitRunnable> getThreads() {
        return threads;
    }
}
