package repository;

import model.Operations;
import model.TypeOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationsRepository {

    private Map<Integer, Operations> operationsMap = new HashMap<>();

    // Добавление операции
    public void addOperation(Operations operation) {
        operationsMap.put(operation.getId(), operation);
    }

    // Получение операции по ID
    public Operations getOperationById(int operationId) {
        return operationsMap.get(operationId);
    }

    // Получение всех операций для конкретного аккаунта
    public List<Operations> getOperationsByAccountId(int bankAccountId) {
        return operationsMap.values().stream()
                .filter(operation -> operation.getIdAccount() == bankAccountId)
                .collect(Collectors.toList());
    }

    // Получение операций по типу
    public List<Operations> getOperationsByType(TypeOperation typeOperation) {
        return operationsMap.values().stream()
                .filter(operation -> operation.getTypeOperation() == typeOperation)
                .collect(Collectors.toList());
    }

    // Получение всех операций
    public List<Operations> getAllOperations() {
        return new ArrayList<>(operationsMap.values());
    }
}
