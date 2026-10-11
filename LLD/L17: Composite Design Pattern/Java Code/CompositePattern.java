import java.util.ArrayList;
import java.util.List;

// Base interface for files and folders
interface FileSystemItem {
    void ls(int indent);            
    void openAll(int indent);      
    int getSize();                  
    FileSystemItem cd(String name); 
    String getName();
    boolean isFolder();
}

// Leaf: File
class File implements FileSystemItem {
    private String name;
    private int size;

    public File(String n, int s) {
        name = n;
        size = s;
    }

    @Override
    public void ls(int indent) {
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + name);
    }

    @Override
    public void openAll(int indent) {
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + name);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public FileSystemItem cd(String name) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}

class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> children;

    public Folder(String n) {
        name = n;
        children = new ArrayList<>();
    }

    public void add(FileSystemItem item) {
        children.add(item);
    }

    @Override
    public void ls(int indent) {
        String indentSpaces = " ".repeat(indent);
        for (FileSystemItem child : children) {
            if (child.isFolder()) {
                System.out.println(indentSpaces + "+ " + child.getName());
            } else {
                System.out.println(indentSpaces + child.getName());
            }
        }
    }

    @Override
    public void openAll(int indent) {
        String indentSpaces = " ".repeat(indent);
        System.out.println(indentSpaces + "+ " + name);
        for (FileSystemItem child : children) {
            child.openAll(indent + 4);
        }
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemItem child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override
    public FileSystemItem cd(String target) {
        for (FileSystemItem child : children) {
            if (child.isFolder() && child.getName().equals(target)) {
                return child;
            }
        }
        // not found or not a folder
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFolder() {
        return true;
    }
}

public class CompositePattern {
    public static void main(String[] args) {
        // Build file system
        Folder root = new Folder("root");
        root.add(new File("file1.txt", 1));
        root.add(new File("file2.txt", 1));

        Folder docs = new Folder("docs");
        docs.add(new File("resume.pdf", 1));
        docs.add(new File("notes.txt", 1));
        root.add(docs);

        Folder images = new Folder("images");
        images.add(new File("photo.jpg", 1));
        root.add(images);

        root.ls(0);

        docs.ls(0);

        root.openAll(0);

        FileSystemItem cwd = root.cd("docs");
        if (cwd != null) {
            cwd.ls(0);
        } else {
            System.out.println("\nCould not cd into docs\n");
        }

        System.out.println(root.getSize());
    }
}
