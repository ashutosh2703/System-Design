// Component Interface: defines a common interface for Mario and all power-up decorators.
interface Character {
    String getAbilities();
}

// Concrete Component: Basic Mario character with no power-ups.
class Mario implements Character {
    public String getAbilities() {
        return "Mario";
    }
}

// Abstract Decorator: CharacterDecorator "is-a" Character and "has-a" Character.
abstract class CharacterDecorator implements Character {
    protected Character character;  // Wrapped component

    public CharacterDecorator(Character c) {
        this.character = c;
    }
}

// Concrete Decorator: Height-Increasing Power-Up.
class HeightUp extends CharacterDecorator {
    public HeightUp(Character c) {
        super(c);
    }

    public String getAbilities() {
        return character.getAbilities() + " with HeightUp";
    }
}

// Concrete Decorator: Gun Shooting Power-Up.
class GunPowerUp extends CharacterDecorator {
    public GunPowerUp(Character c) {
        super(c);
    }

    public String getAbilities() {
        return character.getAbilities() + " with Gun";
    }
}

// Concrete Decorator: Star Power-Up (temporary ability).
class StarPowerUp extends CharacterDecorator {
    public StarPowerUp(Character c) {
        super(c);
    }

    public String getAbilities() {
        return character.getAbilities() + " with Star Power (Limited Time)";
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {
        // Create a basic Mario character.
        Character mario = new Mario();
        System.out.println("Basic Character: " + mario.getAbilities());

        // Decorate Mario with a HeightUp power-up.
        mario = new HeightUp(mario);
        System.out.println("After HeightUp: " + mario.getAbilities());

        // Decorate Mario further with a GunPowerUp.
        mario = new GunPowerUp(mario);
        System.out.println("After GunPowerUp: " + mario.getAbilities());

        // Finally, add a StarPowerUp decoration.
        mario = new StarPowerUp(mario);
        System.out.println("After StarPowerUp: " + mario.getAbilities());
    }
}
