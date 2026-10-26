// Abstraction (Interface)
interface Database {
    void save(String data);
}

// MySQL implementation (Low-level module)
class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println(
            "Executing SQL Query: INSERT INTO users VALUES('" 
            + data + "');"
        );
    }
}

// MongoDB implementation (Low-level module)
class MongoDBDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println(
            "Executing MongoDB Function: db.users.insert({name: '" 
            + data + "'})"
        );
    }
}

// High-level module (Now loosely coupled via Dependency Injection)
class UserService {
    private final Database db;

    public UserService(Database database) {
        this.db = database;
    }

    public void storeUser(String user) {
        db.save(user);
    }
}

public class DIPFollowed {
    public static void main(String[] args) {
        MySQLDatabase mysql = new MySQLDatabase();
        MongoDBDatabase mongodb = new MongoDBDatabase();

        UserService service1 = new UserService(mysql);
        service1.storeUser("Aditya");

        UserService service2 = new UserService(mongodb);
        service2.storeUser("Rohit");
    }
}
