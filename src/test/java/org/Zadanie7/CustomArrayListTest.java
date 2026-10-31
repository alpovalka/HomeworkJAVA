package org.Zadanie7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

    @Test
    void newListIsEmpty() {
        CustomList<Integer> list = new CustomArrayList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
}