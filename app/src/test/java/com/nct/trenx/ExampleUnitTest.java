package com.nct.trenx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void exerciseModel_isCorrect() {
        com.nct.trenx.model.Exercise exercise = new com.nct.trenx.model.Exercise(
                "Push up",
                "15 reps",
                "Chest",
                "Standard push up exercise",
                "push_up.png",
                "Monday",
                "Beginner"
        );

        assertEquals("Push up", exercise.getName());
        assertEquals("15 reps", exercise.getReps());
        assertEquals("Chest", exercise.getCategory());
        assertEquals("Standard push up exercise", exercise.getDescription());
        assertEquals("push_up.png", exercise.getImageName());
        assertEquals("Monday", exercise.getDay());
        assertEquals("Beginner", exercise.getDifficulty());
    }

    @Test
    public void testSanitizeString_TruncatesAndRemovesTags() {
        String input = "<h1>Hello</h1> World! Very long string that should be cut off.";
        String sanitized = com.nct.trenx.utils.ResilienceLayer.sanitizeString(input, 15);
        assertEquals("Hello World! Ve", sanitized);
    }

    @Test
    public void testSanitizeSearchQuery_RemovesSQLInjectionKeywords() {
        String sqlInjection = "Chest UNION";
        String sanitized = com.nct.trenx.utils.ResilienceLayer.sanitizeSearchQuery(sqlInjection);
        assertEquals("Chest", sanitized); // Removes "UNION"
    }

    @Test
    public void testIsValidEmail_IdentifiesValidAndInvalidEmails() {
        assertTrue(com.nct.trenx.utils.ResilienceLayer.isValidEmail("test@example.com"));
        assertTrue(com.nct.trenx.utils.ResilienceLayer.isValidEmail("user.name+tag@domain.co.uk"));
        assertFalse(com.nct.trenx.utils.ResilienceLayer.isValidEmail("invalid-email"));
        assertFalse(com.nct.trenx.utils.ResilienceLayer.isValidEmail("test@domain"));
        assertFalse(com.nct.trenx.utils.ResilienceLayer.isValidEmail(null));
    }

    @Test
    public void testSafeParseInt_HandlesExceptionsAndBoundaries() {
        // Safe standard parsing
        assertEquals(180, com.nct.trenx.utils.ResilienceLayer.safeParseInt("180", 100, 250, 170));
        // Underflow boundary check
        assertEquals(100, com.nct.trenx.utils.ResilienceLayer.safeParseInt("50", 100, 250, 170));
        // Overflow boundary check
        assertEquals(250, com.nct.trenx.utils.ResilienceLayer.safeParseInt("300", 100, 250, 170));
        // Exception formatting check
        assertEquals(170, com.nct.trenx.utils.ResilienceLayer.safeParseInt("invalid_num", 100, 250, 170));
    }
}