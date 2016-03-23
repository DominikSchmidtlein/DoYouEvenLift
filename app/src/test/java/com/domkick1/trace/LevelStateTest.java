package com.domkick1.trace;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by domin_2o9sb4z on 2016-03-21.
 */
public class LevelStateTest {

    LevelState state;

    @Before
    public void setup() {
        state = new LevelState(0, 10, new RemainingLevels(10), new LevelList(5));
    }

    public LevelList setup_levellist(int size) {
        LevelList levelList = new LevelList(size);
        for (int i = 0; i < size; i++)
            levelList.add(new LineList());
        return levelList;
    }

    public RemainingLevels setup_remaininglevels(int size) {
        RemainingLevels remainingLevels = new RemainingLevels(size);
        for (int i = 0; i < size; i++)
            remainingLevels.add(i);
        return remainingLevels;
    }

    @Test
    public void testEnsureSize_levels_removed() {
        testEnsureSize(10, 10, 5);
        testEnsureSize(10, 5, 5);
        testEnsureSize(10, 0, 5);
    }

    @Test
    public void testEnsureSize_levels_added() {
        testEnsureSize(5, 5, 10);
        testEnsureSize(5, 3, 10);
        testEnsureSize(5, 0, 10);
    }

    public void testEnsureSize(int recordedTotal, int remCount, int actualTotal) {
        LevelState state = new LevelState(0, recordedTotal, setup_remaininglevels(remCount), setup_levellist(actualTotal));
        state.ensureSize();
        Assert.assertEquals(state.getTotalLevels(), Integer.valueOf(actualTotal));
        for (int i = 0; i < actualTotal && i < remCount; i++)
            Assert.assertTrue(state.getRemainingLevels().contains(i));
        for (int i = actualTotal; i < recordedTotal; i++)
            Assert.assertTrue(!state.getRemainingLevels().contains(i));
    }


}
