package com.greglturnquist.payroll;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {

    /**
     * Test that a valid employee object can be created
     */
    @Test
    public void getValidEmployeeObject() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;

        // Act
        Employee employee = new Employee(firstName, lastName, description, jobYears);

        // Assert
        assertNotNull(employee);
    }

    /**
     * Test that an invalid employee object with an empty first name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithEmptyFirstName_thenThrowsException() {
        // Arrange
        String firstName = "";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with an empty last name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithEmptyLastName_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "";
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with an empty description throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithEmptyDescription_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a negative job years throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNegativeJobYears_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = -1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a null first name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNullFirstName_thenThrowsException() {
        // Arrange
        String firstName = null;
        String lastName = "Baggins";
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a null last name throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNullLastName_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = null;
        String description = "ring bearer";
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * Test that an invalid employee object with a null description throws an exception
     */
    @Test
    public void testInvalidEmployeeObjectWithNullDescription_thenThrowsException() {
        // Arrange
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = null;
        int jobYears = 1;

        String expectedMessage = "Invalid input";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Employee(firstName, lastName, description, jobYears);
        });

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}

