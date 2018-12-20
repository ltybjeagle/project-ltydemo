package com.liuty.maven.design;

/**
 * 策略模式
 */
public class BraveKnight implements Knight {

    private Quest quest;

    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }
}

interface Knight {
    void embarkOnQuest();
}

interface Quest {
    void embark();
}