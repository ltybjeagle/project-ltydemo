package com.liuty.maven.design;

import org.junit.Test;
import com.liuty.maven.design.BraveKnightDemo.Quest;
import static org.mockito.Mockito.*;

public class BraveKnightTest {

    @Test
    public void embarkOnQuestTest() {
        Quest mockQuest = mock(Quest.class);
        BraveKnightDemo braveKnight = new BraveKnightDemo(mockQuest);
        braveKnight.embarkOnQuest();
        verify(mockQuest, times(1)).embark();
    }
}
