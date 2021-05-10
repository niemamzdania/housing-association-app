package com.przemkeapp.housingassociationapp.dupa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("SpellCheckingInspection")
public class DupaTest {

    private final Dupa dupa = new Dupa();

    @Test
    public void addingTwoNumbersTest() {
        // given
        int a = 1;
        int b = 2;
        // when
        int result = dupa.add(a, b);
        // then
        assertEquals(3, result);
    }

    @Test
    public void passingNullShouldCauseNPE() {
        assertThrows(NullPointerException.class, () -> dupa.add(null, 1));
    }
}