package ppss.practica3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataArrayTest {
    public DataArrayTest() {
    }

    @Test
    void C1_delete_should_return_updatedArray_when_elementExists() {
        DataArray dataArray = new DataArray(new int[]{1, 3, 5, 7});
        assertDoesNotThrow(() -> dataArray.delete(5));
        assertArrayEquals(new int[]{1, 3, 7}, dataArray.getColeccion());
        assertEquals(3, dataArray.getColeccion().length);
    }

    @Test
    void C2_delete_should_return_updatedArray_when_elementExistsMultiple() {
        DataArray dataArray = new DataArray(new int[]{1, 3, 3, 5, 7});
        assertDoesNotThrow(() -> dataArray.delete(3));
        assertArrayEquals(new int[]{1, 3, 5, 7}, dataArray.getColeccion());
        assertEquals(4, dataArray.getColeccion().length);
    }

    @Test
    void C3_delete_should_return_updatedArray_when_elementExistsInMiddle() {
        DataArray dataArray = new DataArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        assertDoesNotThrow(() -> dataArray.delete(4));
        assertArrayEquals(new int[]{1, 2, 3, 5, 6, 7, 8, 9, 10}, dataArray.getColeccion());
        assertEquals(9, dataArray.getColeccion().length);
    }

    @Test
    void C4_delete_should_throw_DataException_when_arrayIsEmpty() {
        DataArray dataArray = new DataArray(new int[]{});
        Exception exception = assertThrows(DataException.class, () -> dataArray.delete(8));
        assertEquals("No hay elementos en la colección", exception.getMessage());
    }

    @Test
    void C5_delete_should_throw_DataException_when_elementIsNegative() {
        DataArray dataArray = new DataArray(new int[]{1, 3, 5, 7});
        Exception exception = assertThrows(DataException.class, () -> dataArray.delete(-5));
        assertEquals("El valor a borrar debe ser > 0", exception.getMessage());
    }

    @Test
    void C6_delete_should_throw_DataException_when_arrayIsEmptyAndElementZero() {
        DataArray dataArray = new DataArray(new int[]{});
        Exception exception = assertThrows(DataException.class, () -> dataArray.delete(0));
        assertEquals("Colección vacía. Y el valor a borrar debe ser > 0", exception.getMessage());
    }

    @Test
    void C7_delete_should_throw_DataException_when_elementNotFound() {
        DataArray dataArray = new DataArray(new int[]{1, 3, 5, 7});
        Exception exception = assertThrows(DataException.class, () -> dataArray.delete(8));
        assertEquals("Elemento no encontrado", exception.getMessage());
    }

    private static Stream<Arguments> casosDePrueba() {
        return Stream.of(
                Arguments.of(8, "No hay elementos en la colección"),
                Arguments.of(-5, "El valor a borrar debe ser > 0"),
                Arguments.of(0, "Colección vacía. Y el valor a borrar debe ser > 0"),
                Arguments.of(8, "Elemento no encontrado")
        );
    }

    @ParameterizedTest(name = "delete_With_Exceptions_{index} Message exception should be {1} when we want delete {0}")
    @MethodSource("casosDePrueba")
    @DisplayName("delete_With_Exceptions_")
    @Tag("parametrizado")
    @Tag("conExcepciones")
    public void C8_deleteWithExceptions(int element, String expectedMessage) {
        DataArray dataArray = new DataArray(new int[]{});
        Exception exception = assertThrows(DataException.class, () -> dataArray.delete(element));
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> deleteWithoutExceptionsProvider() {
        return Stream.of(
                Arguments.of(new int[]{1, 3, 5, 7}, 5, new int[]{1, 3, 7}, "when we want delete 5"),
                Arguments.of(new int[]{1, 3, 3, 5, 7}, 3, new int[]{1, 3, 5, 7}, "when we want delete 3"),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 4, new int[]{1, 2, 3, 5, 6, 7, 8, 9, 10}, "when we want delete 4")
        );
    }

    @ParameterizedTest(name = "delete_Without_Exceptions_{index} should be {2} {3}")
    @MethodSource("deleteWithoutExceptionsProvider")
    @DisplayName("delete_Without_Exceptions_")
    @Tag("parametrizado")
    public void C9_deleteWithoutExceptions(int[] initialArray, int element, int[] expectedArray, String message) {
        DataArray dataArray = new DataArray(initialArray);
        assertDoesNotThrow(() -> dataArray.delete(element));
        assertArrayEquals(expectedArray, dataArray.getColeccion(), "delete_Without_Exceptions should be " + expectedArray + " " + message);
    }
}
