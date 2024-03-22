import java.util.Scanner;

public class Menu {
    enum MenuItem {
        AUTORIZE("Авторизация"),
        REGISTRATION("Регистрация"),
        OPEN_ACCOUNT("Открыть счёт"),
        CHECK_BALANCE("Баланс"),
        DEPOSIT("Пополнение счёта"),
        WITHDRAW("Снятие средств"),
        EXCHANGE_CURRENCY("Обмен валюты"),
        PRINT_ACCOUNT_OPERATION("История транзакций"),
        CLOSE_ACCOUNT("Закрытие счёта"),
        EXCHANGE_HISTORY("История курса валют"),
        ADD_NEW_CURRENCY("Добавить новую валюту"),
        CHECK_ALL_OPERATIONS("Просмотр всех операций"),
        CHECK_ALL_ACCOUNTS("Просмотр всех счетов"),
        CHANGES_EXCHANGE("Изменение курса валют"),
        EXIT("Выход");



        private final String title;

        MenuItem(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }

    }

    // Private properties
    private final Scanner scanner = new Scanner(System.in);
    private MyLinkedList<MenuItem> menu = new MyLinkedList<>();
    private UserService userService = new UserService(new UserRepository());
    private BookService bookService = new BookService(new BookRepository());

    // Public methods
    public void run() {
        createAuthMenu();
        while (true) {
            displayMenu();
            inputUserAction();
        }

    }

    // Private methods
    private void displayMenu() {
        System.out.println("\nМеню:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.printf("%d. %s\n", (i + 1), menu.get(i));
        }
    }

    private void inputUserAction() {
        System.out.print("Выберите опцию: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        MenuItem menuItem = menu.get(choice - 1);
        menuAction(menuItem);
    }

    private void menuAction(MenuItem menuItem) {
        switch (menuItem) {
            case AUTORIZE:

                break;
            case REGISTRATION:

                break;
            case OPEN_ACCOUNT:

                break;
            case CHECK_BALANCE:

                break;
            case DEPOSIT:

                break;
            case WITHDRAW:

                break;
            case EXCHANGE_CURRENCY:

                break;
            case PRINT_ACCOUNT_OPERATION:

                break;
            case CLOSE_ACCOUNT:

                break;
            case EXCHANGE_HISTORY:

                break;
            case ADD_NEW_CURRENCY:

                break;
            case CHANGES_EXCHANGE:

                break;
            case CHECK_ALL_ACCOUNTS:

                break;
            case CHECK_ALL_OPERATIONS:

                break;

            case EXIT:
                break;
        }
    }

    // Private create menu methods
    private void createAuthMenu() {
        menu = new MyLinkedList<>();
        menu.addAll(
                MenuItem.AUTORIZE,
                MenuItem.REGISTRATION,
                MenuItem.EXIT);
    }

    private void createUserMenu() {
        menu = new MyLinkedList<>();
        menu.addAll(
                MenuItem.OPEN_ACCOUNT,
                MenuItem.CHECK_BALANCE,
                MenuItem.DEPOSIT,
                MenuItem.WITHDRAW,
                MenuItem.EXCHANGE_CURRENCY,
                MenuItem.EXCHANGE_HISTORY,
                MenuItem.PRINT_ACCOUNT_OPERATION,
                MenuItem.CLOSE_ACCOUNT,
                MenuItem.EXIT);
    }
    private void createAdminMenu() {
        menu = new MyLinkedList<>();
        menu.addAll(
                MenuItem.ADD_NEW_CURRENCY,
                MenuItem.CHANGES_EXCHANGE,
                MenuItem.CHECK_ALL_ACCOUNTS,
                MenuItem.CHECK_ALL_OPERATIONS,
                MenuItem.EXIT);
    }

    // Private menu actions
    private void loginAction() {
        System.out.print("Введите ваш Email: ");
        String email = scanner.nextLine();
        System.out.print("Введите ваш Пароль: ");
        String password = scanner.nextLine();
        User user = userService.authorize(email, password);
        if (user == null) {
            System.out.println("Неверный логин или пароль!");
        } else {
            System.out.println("Вход прошёл успешно!");
            switch (user.getRole()){
                case USER:
                    createUserMenu();
                    break;
                case ADMIN:
                    createAdminMenu();
                    break;
                case DEBTOR:
                    break;
                case GUEST:
                    break;
            }
        }
    }

    private void registerAction() {
        System.out.print("Введите ваш Email: ");
        String email = scanner.nextLine();
        System.out.print("Введите ваш Пароль: ");
        String password = scanner.nextLine();
        User user = userService.registerUser(email, password);
        if (user == null) {
            System.out.println("Упсс.... ты накосячил");
        } else {
            System.out.println("Вы успешно стали членом клуба");
            System.out.println("Вход в мир");
            switch (user.getRole()){
                case USER:
                    createUserMenu();
                    break;
                case ADMIN:
                    createAdminMenu();
                    break;
                case DEBTOR:
                    break;
                case GUEST:
                    break;
            }
        }
    }

    private void openAccountAction() {
        System.out.print("Введите название валюты: ");
        String title = scanner.nextLine();
        bookService.openAccount(title, author);
        System.out.println("Валюта успешно добавленна");
    }
    private void checkBalanceAction() {
        System.out.println("Ваш баланс по следующим валютам:");
        MyList<Book> books = bookService.getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
    private void depositAction() {
        System.out.println("Сколько хоиите внести денег: ");
        String search = scanner.nextLine();
        Book book = bookService.searchBook(search);
        if (book == null) {
            System.out.println("Неудалось пополнить счёт");
        } else {
            System.out.println("ВЫ успешно пополнили счёт: ");
            System.out.printf("%s %s |id: %d", book.getTitle(), book.getAuthor(), book.getBookId());
        }
    }
    private  void withdrawAction() {
        System.out.println("Сколько вы хотите вывести деняг");
        int search = scanner.nextInt();
        Book book = bookService.takeBook(search);
        if (book == null) {
            System.out.println("Деньги не найдены");
        } else if (!book.isAvailable()) {
            bookService.takeBook(book.getBookId());
            System.out.println("Деньги успешно выведенны");
        }

    }
    private void exchangeCurrencyAction() {
        System.out.println("Какую валюту вы хотите обменять?");
        int returnBook = scanner.nextInt();
        Book book = bookService.serchBookByID(returnBook);
        if (book == null) {
            System.out.println("Валюта не найдена");
        } else if (!book.isAvailable()) {
            bookService.returnBook(book.getBookId());
            System.out.println("Успешная конвертация!");
        }
    }
    private void printAccountOperationsAction() {
        System.out.println("История Транзацкий");
        MyList<Book> books = bookService.booksByRead();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
    private void exchangeHistoryAction() {

    }
    private void checkAllAccountsAction() {
        System.out.println("Проверить транзакции всех аккаунтов");
        MyList<Book> books = bookService.freeBooks();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
    private  void checkAllOperationsAction() {
        System.out.println("Проверить операции всех аккаунтов");
        MyList<Book> books = bookService.booksByRead();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
    private void addNewCurrency() {

    }
    private void changesExchange() {

    }
    private void exitAction() {
        System.exit(0);
    }
}
