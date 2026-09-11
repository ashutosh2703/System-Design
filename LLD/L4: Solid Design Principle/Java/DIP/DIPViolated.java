class MySQLDatabase {  // Low-level module
    public void saveToSQL(String data) {
        System.out.println(
            "Executing SQL Query: INSERT INTO users VALUES('" 
            + data + "');"
        );
    }
}

class MongoDBDatabase {  // Low-level module
    public void saveToMongo(String data) {
        System.out.println(
            "Executing MongoDB Function: db.users.insert({name: '" 
            + data + "'})"
        );
    }
}

class UserService {  // High-level module (Tightly coupled)
    private final MySQLDatabase sqlDb = new MySQLDatabase();      
    private final MongoDBDatabase mongoDb = new MongoDBDatabase();

    public void storeUserToSQL(String user) {
        // MySQL-specific code
        sqlDb.saveToSQL(user);
    }

    public void storeUserToMongo(String user) {
        // MongoDB-specific code
        mongoDb.saveToMongo(user);
    }
}

public class DIPViolated {
    public static void main(String[] args) {
        UserService service = new UserService();
        service.storeUserToSQL("Aditya");
        service.storeUserToMongo("Rohit");
    }
}
