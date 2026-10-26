// Return Type Rule : 
// Subtype overridden method return type should be either identical 
// or narrower than the parent method's return type.
// This is also called return type covariance.
// Java supports this out of the box.

class Animal {
    // some common Animal methods
}

class Dog extends Animal {
    // Additional Dog methods specific to Dogs.
}

class Parent {
    public Animal getAnimal() {
        System.out.println("Parent : Returning Animal instance");
        return new Animal();
    }
}

class Child extends Parent {
    @Override
    public Animal getAnimal() {
        System.out.println("Child : Returning Dog instance");
        return new Dog();
    }
}

class Client {
    private Parent p;

    public Client(Parent p) {
        this.p = p;
    }

    public void takeAnimal() {
        p.getAnimal();
    }
}

public class ReturnTypeRule {
    public static void main(String[] args) {
        Parent parent = new Parent();
        Child child   = new Child();

        Client client = new Client(child);
        //Client client = new Client(parent);
        client.takeAnimal();
    }
}
