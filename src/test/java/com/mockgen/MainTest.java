package com.mockgen;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainExists() {
        Main main = new Main();
        assertNotNull(main);
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}