package com.liuty.maven.design;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class BraveKnightTest {

    @Test
    public void embarkOnQuestTest() {
        StratetyDesignDemo.Quest mockQuest = mock(StratetyDesignDemo.Quest.class);
        StratetyDesignDemo.BraveKnightDemo braveKnight = new StratetyDesignDemo.BraveKnightDemo(mockQuest);
        braveKnight.embarkOnQuest();
        verify(mockQuest, times(1)).embark();
    }
}
