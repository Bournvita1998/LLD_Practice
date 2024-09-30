

############################################### Coffee maker #############################################







from abc import ABC, abstractmethod

# Coffee Beans Class - A simple class to hold the amount of coffee beans
class CoffeeBeans:
    def __init__(self, quantity_in_grams: int):
        self.quantity_in_grams = quantity_in_grams

# Water Class - Similar to coffee beans, holds water level in milliliters
class Water:
    def __init__(self, quantity_in_milliliters: int):
        self.quantity_in_milliliters = quantity_in_milliliters

# Grinder class for grinding coffee beans (modular component)
class Grinder:
    def grind(self, beans: CoffeeBeans) -> str:
        if beans.quantity_in_grams > 0:
            return "Ground coffee ready."
        else:
            raise Exception("Not enough coffee beans.")

# Water Heater Class - Heats up the water for brewing
class WaterHeater:
    def heat(self, water: Water) -> str:
        if water.quantity_in_milliliters > 0:
            return "Water heated."
        else:
            raise Exception("Not enough water.")

# Abstract base class for Coffee Types (Liskov Substitution)
class Coffee(ABC):
    @abstractmethod
    def brew(self) -> str:
        pass

# Espresso Coffee Class - implements Coffee
class Espresso(Coffee):
    def brew(self) -> str:
        return "Brewing a rich Espresso."

# Cappuccino Coffee Class - implements Coffee
class Cappuccino(Coffee):
    def brew(self) -> str:
        return "Brewing a frothy Cappuccino."

# Coffee Machine Class - Composed of multiple components
class CoffeeMaker:
    def __init__(self, grinder: Grinder, water_heater: WaterHeater):
        self.grinder = grinder
        self.water_heater = water_heater
        self.clean_state = True  # Initially clean

    def add_water(self, water: Water):
        self.water = water

    def add_coffee_beans(self, beans: CoffeeBeans):
        self.beans = beans

    def brew_coffee(self, coffee: Coffee):
        if not self.clean_state:
            raise Exception("Machine needs to be cleaned before next brew.")

        # Grind coffee beans
        print(self.grinder.grind(self.beans))

        # Heat water
        print(self.water_heater.heat(self.water))

        # Brew coffee
        print(coffee.brew())
        print("Coffee is ready!")

        # Mark as dirty
        self.clean_state = False

    def clean_machine(self):
        print("Cleaning the coffee maker...")
        self.clean_state = True

# Factory Pattern for creating coffee objects
class CoffeeFactory:
    @staticmethod
    def create_coffee(coffee_type: str) -> Coffee:
        if coffee_type == 'espresso':
            return Espresso()
        elif coffee_type == 'cappuccino':
            return Cappuccino()
        else:
            raise ValueError(f"Unknown coffee type: {coffee_type}")

# Main function to simulate the usage
# if __name__ == "__main__":
#     # Create the necessary components
#     grinder = Grinder()
#     water_heater = WaterHeater()

#     # Create the coffee maker machine
#     coffee_maker = CoffeeMaker(grinder, water_heater)

#     # Add water and coffee beans
#     water = Water(200)  # 200 ml water
#     beans = CoffeeBeans(20)  # 20 grams of beans
#     coffee_maker.add_water(water)
#     coffee_maker.add_coffee_beans(beans)

#     # Brew espresso
#     coffee = CoffeeFactory.create_coffee("espresso")
#     coffee_maker.brew_coffee(coffee)

#     # Clean the machine
#     coffee_maker.clean_machine()

#     # Brew cappuccino
#     coffee = CoffeeFactory.create_coffee("cappuccino")
#     coffee_maker.brew_coffee(coffee)


