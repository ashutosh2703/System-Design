interface IDocumentReader {
    void unlockPDF(String filePath, String password);
}

class RealDocumentReader implements IDocumentReader {
    @Override
    public void unlockPDF(String filePath, String password) {
        System.out.println("[RealDocumentReader] Unlocking PDF at: " + filePath);
        System.out.println("[RealDocumentReader] PDF unlocked successfully with password: " + password);
        System.out.println("[RealDocumentReader] Displaying PDF content...");
    }
}

class User {
    public String name;
    public boolean premiumMembership;

    public User(String name, boolean isPremium) {
        this.name = name;
        this.premiumMembership = isPremium;
    }
}

class DocumentProxy implements IDocumentReader {
    private RealDocumentReader realReader;
    private User user;

    public DocumentProxy(User user) {
        this.realReader = new RealDocumentReader();
        this.user = user;
    }

    @Override
    public void unlockPDF(String filePath, String password) {
        if (!user.premiumMembership) {
            System.out.println("[DocumentProxy] Access denied. Only premium members can unlock PDFs.");
            return;
        }
        realReader.unlockPDF(filePath, password);
    }
}

public class ProtectionProxy {
    public static void main(String[] args) {
        User user1 = new User("Rohan", false);
        User user2 = new User("Rashmi", true);

        System.out.println("== Rohan (Non-Premium) tries to unlock PDF ==");
        IDocumentReader docReader = new DocumentProxy(user1);
        docReader.unlockPDF("protected_document.pdf", "secret123");

        System.out.println("\n== Rashmi (Premium) unlocks PDF ==");
        docReader = new DocumentProxy(user2);
        docReader.unlockPDF("protected_document.pdf", "secret123");
    }
}
