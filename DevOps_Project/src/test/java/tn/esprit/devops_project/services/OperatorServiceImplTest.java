package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperatorServiceImplTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    private Operator operator1;
    private Operator operator2;

    @BeforeEach
    void setUp() {
        operator1 = new Operator();
        operator1.setIdOperateur(1L);
        operator1.setFname("John");
        operator1.setLname("Doe");
        operator1.setPassword("password123");
        operator1.setInvoices(new HashSet<>());

        operator2 = new Operator();
        operator2.setIdOperateur(2L);
        operator2.setFname("Jane");
        operator2.setLname("Smith");
        operator2.setPassword("password456");
        operator2.setInvoices(new HashSet<>());
    }

    @Test
    void retrieveAllOperators_ShouldReturnListOfOperators() {
        // Arrange
        List<Operator> operators = Arrays.asList(operator1, operator2);
        when(operatorRepository.findAll()).thenReturn(operators);

        // Act
        List<Operator> result = operatorService.retrieveAllOperators();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFname());
        assertEquals("Jane", result.get(1).getFname());
        verify(operatorRepository, times(1)).findAll();
    }

    @Test
    void addOperator_ShouldReturnSavedOperator() {
        // Arrange
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator1);

        // Act
        Operator result = operatorService.addOperator(operator1);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdOperateur());
        assertEquals("John", result.getFname());
        verify(operatorRepository, times(1)).save(any(Operator.class));
    }

    @Test
    void deleteOperator_ShouldDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        when(operatorRepository.findById(id)).thenReturn(Optional.of(operator1));
        doNothing().when(operatorRepository).deleteById(id);

        // Act
        operatorService.deleteOperator(id);

        // Assert
        verify(operatorRepository, times(1)).deleteById(id);
    }

    @Test
    void updateOperator_ShouldReturnUpdatedOperator() {
        // Arrange
        when(operatorRepository.save(any(Operator.class))).thenReturn(operator1);

        // Act
        operator1.setFname("John Updated");
        Operator result = operatorService.updateOperator(operator1);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getFname());
        verify(operatorRepository, times(1)).save(any(Operator.class));
    }

    @Test
    void retrieveOperator_ShouldReturnOperator() {
        // Arrange
        Long id = 1L;
        when(operatorRepository.findById(id)).thenReturn(Optional.of(operator1));

        // Act
        Operator result = operatorService.retrieveOperator(id);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdOperateur());
        assertEquals("John", result.getFname());
        verify(operatorRepository, times(1)).findById(id);
    }

    @Test
    void retrieveOperator_ShouldThrowExceptionWhenNotFound() {
        // Arrange
        Long id = 999L;
        when(operatorRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            operatorService.retrieveOperator(id);
        });
        verify(operatorRepository, times(1)).findById(id);
    }

    @Test
    void retrieveAllOperators_ShouldReturnEmptyList() {
        // Arrange
        when(operatorRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Operator> result = operatorService.retrieveAllOperators();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(operatorRepository, times(1)).findAll();
    }
}
