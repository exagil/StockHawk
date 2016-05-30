package com.sam_chordas.android.stockhawk.data.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PersistableBooleanTest {
    @Test
    public void testPersistableBooleanKnowsItsValueWhenItIsTrue() {
        assertEquals(1, PersistableBoolean.TRUE.value());
    }

    @Test
    public void testPersistableBooleanKnowsItsValueWhenItIsFalse() {
        assertEquals(0, PersistableBoolean.FALSE.value());
    }
}
