package tn.esprit.devops_project.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.Iservices.IOperatorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperatorServiceImpl implements IOperatorService {

    private static final Logger logger = LogManager.getLogger(OperatorServiceImpl.class);
    
    OperatorRepository operatorRepository;

    @Override
    public List<Operator> retrieveAllOperators() {
        logger.info("Starting retrieveAllOperators method");
        try {
            List<Operator> operators = (List<Operator>) operatorRepository.findAll();
            logger.info("Successfully retrieved {} operators", operators.size());
            logger.debug("Retrieved operators details: {}", 
                operators.stream()
                    .map(op -> String.format("ID: %d, Name: %s %s", 
                        op.getIdOperateur(), op.getFname(), op.getLname()))
                    .collect(Collectors.toList()));
            return operators;
        } catch (Exception e) {
            logger.error("Error while retrieving all operators: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Operator addOperator(Operator operator) {
        logger.info("Starting addOperator method for operator: {} {}", 
            operator.getFname(), operator.getLname());
        try {
            Operator savedOperator = operatorRepository.save(operator);
            logger.info("Successfully added operator - ID: {}, Name: {} {}", 
                savedOperator.getIdOperateur(), 
                savedOperator.getFname(), 
                savedOperator.getLname());
            return savedOperator;
        } catch (Exception e) {
            logger.error("Error while adding operator {} {}: {}", 
                operator.getFname(), 
                operator.getLname(), 
                e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteOperator(Long id) {
        logger.info("Starting deleteOperator method for ID: {}", id);
        try {
            // Log operator details before deletion
            operatorRepository.findById(id).ifPresent(operator -> 
                logger.info("Deleting operator - ID: {}, Name: {} {}", 
                    operator.getIdOperateur(), 
                    operator.getFname(), 
                    operator.getLname())
            );
            
            operatorRepository.deleteById(id);
            logger.info("Successfully deleted operator with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error while deleting operator with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public Operator updateOperator(Operator operator) {
        logger.info("Starting updateOperator method for operator ID: {}, Name: {} {}", 
            operator.getIdOperateur(), 
            operator.getFname(), 
            operator.getLname());
        try {
            Operator updatedOperator = operatorRepository.save(operator);
            logger.info("Successfully updated operator - ID: {}, Name: {} {}", 
                updatedOperator.getIdOperateur(), 
                updatedOperator.getFname(), 
                updatedOperator.getLname());
            return updatedOperator;
        } catch (Exception e) {
            logger.error("Error while updating operator ID {}: {}", 
                operator.getIdOperateur(), 
                e.getMessage());
            throw e;
        }
    }

    @Override
    public Operator retrieveOperator(Long id) {
        logger.info("Starting retrieveOperator method for ID: {}", id);
        try {
            Operator operator = operatorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Operator not found with ID: {}", id);
                    return new NullPointerException("Operator not found");
                });
            logger.info("Successfully retrieved operator - ID: {}, Name: {} {}", 
                operator.getIdOperateur(), 
                operator.getFname(), 
                operator.getLname());
            return operator;
        } catch (Exception e) {
            logger.error("Error while retrieving operator with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
