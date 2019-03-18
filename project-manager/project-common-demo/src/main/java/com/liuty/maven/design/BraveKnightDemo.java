package com.liuty.maven.design;

/**
 * 策略模式
 * 结构：
 *      1、抽象统一接口
 *      2、各接口实现类实现自己的策略逻辑
 *      3、执行类根据需要实例化不同的策略实例，达到不同的策略效果
 */
public class BraveKnightDemo implements Knight {

    public interface Quest {
        void embark();
    }

    private Quest quest;

    public BraveKnightDemo(Quest quest) {
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