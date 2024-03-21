package service;

import model.BankAccount;
import model.Currency;
import model.Users;
import model.Operations;
import repository.BankAccountRepository;
import service.CurrencyService;
import service.OperationService;
import service.UserService;

import java.util.List;
import java.util.Optional;

public class BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private UserService userService;
    private CurrencyService currencyService;
    private OperationService operationService;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserService userService, CurrencyService currencyService, OperationService operationService) {
        this.bankAccountRepository = bankAccountRepository;
        this.userService = userService;
        this.currencyService = currencyService;
        this.operationService = operationService;
    }

    public void openAccount(int userId, String currencyCode, double initialBalance) {
        Users user = userService.getUserById(userId); // Предполагается, что метод getUserById существует в UserService
        Currency currency = currencyService.getCurrencyByCode(currencyCode); // Предполагается, что метод getCurrencyByCode существует в CurrencyService

        int accountId = bankAccountRepository.getAllBankAccounts().size() + 1;
        BankAccount newAccount = new BankAccount(initialBalance, accountId, user, currency);
        bankAccountRepository.addBankAccount(newAccount);
        System.out.println("Новый счет успешно создан для пользователя с ID: " + userId);
    }

    public void deposit(int accountId, double amount) {
        Optional<BankAccount> account = bankAccountRepository.getBankAccountById(accountId);
        account.ifPresentOrElse(acc -> {
            acc.setBalance(acc.getBalance() + amount);
            bankAccountRepository.updateBankAccount(acc);
            int operationId = operationService.generateOperationId(); // Предполагается, что метод generateOperationId существует в OperationService
            operationService.recordOperation(new Operations(operationId, amount, acc.getCurrency().getCode(), accountId, Operations.TypeOperation.DEPOSIT));
            System.out.println("Счет пополнен на " + amount);
        }, () -> System.out.println("Счет не найден."));
    }
    public void withdraw(int accountId, double amount) {
        Optional<BankAccount> account = bankAccountRepository.getBankAccountById(accountId);
        account.ifPresentOrElse(acc -> {
            if (acc.getBalance() >= amount) {
                acc.setBalance(acc.getBalance() - amount);
                bankAccountRepository.updateBankAccount(acc);
                int operationId = operationService.generateOperationId();
                operationService.recordOperation(new Operations(operationId, -amount, acc.getCurrency().getCode(), accountId, Operations.TypeOperation.WITHDRAWAL));
                System.out.println("Со счета снято: " + amount);
            } else {
                System.out.println("Недостаточно средств на счете.");
            }
        }, () -> System.out.println("Счет не найден."));
    }

    public void exchangeCurrency(int accountId, String targetCurrencyCode, double amount) {
        Optional<BankAccount> accountOpt = bankAccountRepository.getBankAccountById(accountId);
        accountOpt.ifPresentOrElse(account -> {
            double exchangeRate = currencyService.getExchangeCourse(account.getCurrency().getCode(), targetCurrencyCode);
            double convertedAmount = amount * exchangeRate;

            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount); // Снимаем сумму в исходной валюте
                // Здесь может быть необходимо добавить сумму в целевой валюте, если учет ведется отдельно
                bankAccountRepository.updateBankAccount(account);
                int operationId = operationService.generateOperationId();
                operationService.recordOperation(new Operations(operationId, -amount, account.getCurrency().getCode(), accountId, Operations.TypeOperation.EXCHANGE));
                System.out.println("Обмен выполнен. Снято " + amount + " " + account.getCurrency().getCode() + ", добавлено " + convertedAmount + " " + targetCurrencyCode + ".");
            } else {
                System.out.println("Недостаточно средств для обмена.");
            }
        }, () -> System.out.println("Счет не найден."));
    }

    public void closeAccount(int accountId) {
        Optional<BankAccount> account = bankAccountRepository.getBankAccountById(accountId);
        account.ifPresentOrElse(acc -> {
            bankAccountRepository.deleteBankAccount(accountId);
            int operationId = operationService.generateOperationId();
            operationService.recordOperation(new Operations(operationId, 0, acc.getCurrency().getCode(), accountId, Operations.TypeOperation.CLOSE_ACCOUNT));
            System.out.println("Счет закрыт.");
        }, () -> System.out.println("Счет не найден."));
    }



    public void printAccountOperation(int accountId) {
        List<Operations> transactions = operationService.getOperationsByAccountId(accountId);
        if (transactions.isEmpty()) {
            System.out.println("Транзакций по данному счету не найдено.");
        } else {
            System.out.println("История транзакций для счета " + accountId + ":");
            transactions.forEach(System.out::println);
        }
    }
}