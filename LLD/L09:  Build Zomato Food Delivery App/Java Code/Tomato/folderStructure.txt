OnlineFoodOrderingSystem/
│
├── Main.java                    # Composition root and entry point
├── TomatoApp.java                # Facade class (main orcjavaestrator)
│
├── models/
│   ├── MenuItem.java
│   ├── Restaurant.java
│   ├── User.java
│   ├── Cart.java
│   ├── Order.java                # Abstract Order
│   ├── DeliveryOrder.java
│   ├── PickupOrder.java
│
├── managers/
│   ├── RestaurantManager.java
│   ├── OrderManager.java
│
├── strategies/
│   ├── PaymentStrategy.java      # Base class
│   ├── CreditCardPaymentStrategy.java
│   ├── UpiPaymentStrategy.java
│
├── factories/
│   ├── OrderFactory.java         # Abstract factory
│   ├── NowOrderFactory.java
│   ├── ScheduledOrderFactory.java
│
├── services/
│   └── NotificationService.java
│
├── utils/
│   └── TimeUtils.java
