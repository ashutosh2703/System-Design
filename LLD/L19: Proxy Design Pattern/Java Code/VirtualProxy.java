interface IImage {
    void display();
}

class RealImage implements IImage {
    private String filename;

    public RealImage(String file) {
        this.filename = file;
        System.out.println("[RealImage] Loading image from disk: " + filename);
    }

    @Override
    public void display() {
        System.out.println("[RealImage] Displaying " + filename);
    }
}

class ImageProxy implements IImage {
    private RealImage realImage;
    private String filename;

    public ImageProxy(String file) {
        this.filename = file;
        this.realImage = null;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

public class VirtualProxy {
    public static void main(String[] args) {
        IImage image1 = new ImageProxy("sample.jpg");
        image1.display();
    }
}
