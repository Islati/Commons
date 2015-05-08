package com.caved_in.commons.scoreboard;

import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashSet;
import java.util.Set;

public class ScoreboardBuilder implements IScoreboardBuilder {
    private Set<ScoreboardEntry> entries = new HashSet<>();

    private ObjectiveRegisterData objective = new ObjectiveRegisterData();

    private ScoreboardInformation info = new BasicScoreboardInformation();

    private ScoreboardType type;

    private BoardManager manager;

    public ScoreboardBuilder(BoardManager manager, DisplaySlot sidebar) {
        this.manager = manager;
        objective.slot(sidebar);
    }

    @Override
    public ScoreboardBuilder type(ScoreboardType type) {
        this.type = type;
        return this;
    }

    @Override
    public ScoreboardBuilder title(String title) {
        info.title(title);
        return this;
    }

    @Override
    public ScoreboardBuilder entry(int position, String text) {
        entries.add(new IndexedScoreboardEntry().position(position).text(text));
        return this;
    }

    @Override
    public IScoreboardBuilder entry(ScoreboardEntry entry) {
        entries.add(entry);
        return this;
    }

    @Override
    public ScoreboardBuilder objective(String name, String action) {
        objective.action(action).name(name);
        return this;
    }

    @Override
    public ScoreboardWrapper build() {
        //Assign the entries to our scoreboard information.
        info.setEntries(entries);
        return manager.bake(type, info, objective);
    }
}
