package com.liuty.maven.design;

/**
 * @描述: 策略模式
 * 结构：
 * 1、抽象统一接口
 * 2、各接口实现类实现自己的策略逻辑
 * 3、执行类根据需要实例化不同的策略实例，达到不同的策略效果
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/20
 */
public class StratetyDesignDemo {

    public static void main(String ...args) {

    }

    /**
     * 策略接口，通过将不同的接口实现类注入到BraveKnightDemo，以实现不同的执行效果
     */
    public interface Quest {
        void embark();
    }

    public interface Knight {
        void embarkOnQuest();
    }

    public static class BraveKnightDemo implements Knight {
        private Quest quest;
        public BraveKnightDemo(Quest quest) {
            this.quest = quest;
        }
        @Override
        public void embarkOnQuest() {
            quest.embark();
        }
    }
}
