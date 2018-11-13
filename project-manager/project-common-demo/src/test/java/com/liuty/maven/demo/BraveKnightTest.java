package com.liuty.maven.demo;

import com.liuty.maven.demo.impl.BraveKnight;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class BraveKnightTest {

    @Test
    public void embarkOnQuestTest() {
        Quest mockQuest = mock(Quest.class);
        BraveKnight braveKnight = new BraveKnight(mockQuest);
        braveKnight.embarkOnQuest();
        verify(mockQuest, times(1)).embark();
    }
}