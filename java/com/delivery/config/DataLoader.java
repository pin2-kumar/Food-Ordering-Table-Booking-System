package com.delivery.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.delivery.entity.MenuItem;
import com.delivery.entity.Restaurant;
import com.delivery.entity.RestaurantSlot;
import com.delivery.repository.MenuItemRepository;
import com.delivery.repository.RestaurantRepository;
import com.delivery.repository.RestaurantSlotRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(RestaurantRepository restaurantRepo,
                               MenuItemRepository menuRepo,
                               RestaurantSlotRepository slotRepo) {

        return args -> {

           if (restaurantRepo.count() > 0) return;

            
            // ----------- Restaurant 1 -------------
            Restaurant r1 = new Restaurant();
            r1.setName("Barbeque Nation");
            r1.setLocation("Delhi sector60");
           // Restaurant r1 = new Restaurant("Barbeque Nation", "Noida");
            restaurantRepo.save(r1);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Paneer Tikka", 299, "Grilled paneer cubes", r1),
                createMenu("Veg Biryani", 249, "Aromatic rice", r1),
                createMenu("Cold Coffee", 159, "Chilled coffee", r1),
                createMenu("Margherita Pizza", 449, "Cheese pizza", r1),
                createMenu("Hara Bhara Kebab", 199, "Spinach patty", r1),
                createMenu("Dal Makhani", 229, "Creamy dal", r1),
                createMenu("Butter Naan", 49, "Soft naan", r1),
                createMenu("Gulab Jamun", 99, "Sweet dessert", r1),
                createMenu("Spring Roll", 189, "Crispy rolls", r1),
                createMenu("Mojito", 149, "Mint drink", r1)
            ));

            createSlots(r1, slotRepo);

            // ----------- Restaurant 2 -------------
           // if (restaurantRepo.count() == 0) {

                Restaurant r2 = new Restaurant();
                r2.setName("Bikanervala");
                r2.setLocation("Delhi");

                restaurantRepo.save(r2);

                menuRepo.saveAll(Arrays.asList(
                    createMenu("Chole Bhature", 180, "Spicy chole", r2),
                    createMenu("Raj Kachori", 120, "Crispy snack", r2),
                    createMenu("Rasmalai", 90, "Sweet dessert", r2),
                    createMenu("Paneer Tikka", 299, "Grilled paneer cubes", r2),
                    createMenu("Veg Biryani", 249, "Aromatic rice", r2),
                    createMenu("Cold Coffee", 159, "Chilled coffee", r2),
                    createMenu("Margherita Pizza", 449, "Cheese pizza", r2),
                    createMenu("Hara Bhara Kebab", 199, "Spinach patty", r2),
                    createMenu("Dal Makhani", 229, "Creamy dal", r2),
                    createMenu("Butter Naan", 49, "Soft naan", r2),
                    createMenu("Gulab Jamun", 99, "Sweet dessert", r2),
                    createMenu("Spring Roll", 189, "Crispy rolls", r2),
                    createMenu("Mojito", 149, "Mint drink", r2)
                ));

                createSlots(r2, slotRepo);
            
            
            
                Restaurant r3 = new Restaurant();
                r3.setName("Dominos");
                r3.setLocation("Noida sector2");

                if(!restaurantRepo.existsByName("Dominos")){

                    restaurantRepo.save(r3);

                    menuRepo.saveAll(Arrays.asList(
                        createMenu("Margherita", 299, "Classic cheese", r3),
                        createMenu("Farmhouse", 399, "Veg loaded pizza", r3),
                        createMenu("Peppy Paneer", 449, "Paneer pizza", r3),
                        createMenu("Cheese Burst", 499, "Extra cheese", r3),
                        createMenu("Garlic Bread", 129, "Garlic sticks", r3),
                        createMenu("Pasta", 199, "Creamy pasta", r3),
                        createMenu("Choco Lava", 99, "Chocolate dessert", r3),
                        createMenu("Coke", 60, "Cold drink", r3),
                        createMenu("Veg Taco", 149, "Mexican taco", r3),
                        createMenu("Stuffed Bread", 179, "Cheese bread", r3)
                    ));

                    createSlots(r3, slotRepo);
                }
            
            
            Restaurant r4 = new Restaurant();
            r4.setName("KFC");
            r4.setLocation("Noida");
          //  Restaurant r4 = new Restaurant("KFC", "Noida");
            restaurantRepo.save(r4);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Zinger Burger", 199, "Crispy burger", r4),
                createMenu("Veg Strips", 149, "Crispy strips", r4),
                createMenu("Chicken Bucket", 599, "Family pack", r4),
                createMenu("Popcorn Chicken", 199, "Bite size", r4),
                createMenu("Fries", 109, "Crispy fries", r4),
                createMenu("Krushers", 129, "Cold drink", r4),
                createMenu("Wrap", 179, "Veg wrap", r4),
                createMenu("Rice Bowl", 159, "Rice combo", r4),
                createMenu("Pepsi", 60, "Cold drink", r4),
                createMenu("Brownie", 89, "Chocolate brownie", r4)
            ));

            createSlots(r4, slotRepo);
            
            
            
            Restaurant r5 = new Restaurant();
            r5.setName("Pizza Hut");
            r5.setLocation("Delhi");
           // Restaurant r5 = new Restaurant("Pizza Hut", "Delhi");
            restaurantRepo.save(r5);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Veg Supreme", 499, "Loaded pizza", r5),
                createMenu("Tandoori Paneer", 449, "Spicy paneer", r5),
                createMenu("Classic Cheese", 299, "Cheese pizza", r5),
                createMenu("Garlic Bread", 129, "Bread sticks", r5),
                createMenu("Pasta Alfredo", 229, "White sauce pasta", r5),
                createMenu("Stuffed Crust", 549, "Cheese crust", r5),
                createMenu("Mousse Cake", 149, "Chocolate cake", r5),
                createMenu("Sprite", 60, "Cold drink", r5),
                createMenu("Veg Sandwich", 199, "Grilled sandwich", r5),
                createMenu("Ice Cream", 99, "Vanilla scoop", r5)
            ));

            createSlots(r5, slotRepo);
            
            Restaurant r6 = new Restaurant();
            r6.setName("Haldiram");
            r6.setLocation("Gurgaon");
            //Restaurant r6 = new Restaurant("Haldiram", "Gurgaon");
            restaurantRepo.save(r6);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Raj Kachori", 179, "Crispy kachori", r6),
                createMenu("Chole Bhature", 159, "North Indian combo", r6),
                createMenu("Pav Bhaji", 149, "Spicy bhaji", r6),
                createMenu("Samosa", 25, "Fried snack", r6),
                createMenu("Kaju Katli", 899, "Premium sweet", r6),
                createMenu("Rasgulla", 30, "Soft sweet", r6),
                createMenu("Dahi Bhalla", 129, "Curd snack", r6),
                createMenu("Masala Dosa", 139, "South Indian", r6),
                createMenu("Cold Drink", 50, "Soft drink", r6),
                createMenu("Thali", 249, "Full meal", r6)
            ));

            createSlots(r6, slotRepo);
            
            Restaurant r7 = new Restaurant();
            r7.setName("Bikanervala22");
            r7.setLocation("MUMBAI");
            //Restaurant r7 = new Restaurant("Bikanervala", "Delhi");
            restaurantRepo.save(r7);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Aloo Tikki", 79, "Crispy tikki", r7),
                createMenu("Pani Puri", 59, "Street snack", r7),
                createMenu("Dal Kachori", 35, "Spicy kachori", r7),
                createMenu("Special Thali", 299, "Full meal", r7),
                createMenu("Gajar Halwa", 149, "Sweet dessert", r7),
                createMenu("Rasmalai", 49, "Milk sweet", r7),
                createMenu("Veg Pulao", 189, "Rice dish", r7),
                createMenu("Lassi", 69, "Curd drink", r7),
                createMenu("Chaat Platter", 199, "Mixed chaat", r7),
                createMenu("Tea", 20, "Hot tea", r7)
            ));

            createSlots(r7, slotRepo);
            
            
            Restaurant r8 = new Restaurant();
            r8.setName("Burger King");
            r8.setLocation("Noida");
            //Restaurant r8 = new Restaurant("Burger King", "Noida");
            restaurantRepo.save(r8);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Veg Whopper", 179, "Large burger", r8),
                createMenu("Cheese Burger", 129, "Cheesy burger", r8),
                createMenu("French Fries", 99, "Crispy fries", r8),
                createMenu("Onion Rings", 109, "Fried rings", r8),
                createMenu("Veg Wrap", 149, "Soft wrap", r8),
                createMenu("Cold Coffee", 129, "Chilled drink", r8),
                createMenu("King Meal", 299, "Burger combo", r8),
                createMenu("Chocolate Shake", 139, "Sweet shake", r8),
                createMenu("Nuggets", 159, "Crispy bites", r8),
                createMenu("Coke", 60, "Soft drink", r8)
            ));

            createSlots(r8, slotRepo);
            
           
            
            Restaurant r9 = new Restaurant();
            r9.setName("Wow Momo");
            r9.setLocation("Delhi");
            //Restaurant r9 = new Restaurant("Wow Momo", "Delhi");
            restaurantRepo.save(r9);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Veg Momo", 129, "Steamed momo", r9),
                createMenu("Fried Momo", 149, "Crispy momo", r9),
                createMenu("Paneer Momo", 169, "Paneer filling", r9),
                createMenu("Cheese Momo", 179, "Cheesy momo", r9),
                createMenu("Darjeeling Momo", 159, "Spicy momo", r9),
                createMenu("Momo Burger", 189, "Fusion burger", r9),
                createMenu("Thukpa", 199, "Noodle soup", r9),
                createMenu("Momo Platter", 249, "Mixed momo", r9),
                createMenu("Cold Drink", 50, "Soft drink", r9),
                createMenu("Chocolate Momo", 199, "Sweet momo", r9)
            ));

            createSlots(r9, slotRepo);
            
            
            Restaurant r10 = new Restaurant();
            r10.setName("Subway");
            r10.setLocation("Gurgaon");
            //Restaurant r10 = new Restaurant("Subway", "Gurgaon");
            restaurantRepo.save(r10);

            menuRepo.saveAll(Arrays.asList(
                createMenu("Veg Sub", 199, "Healthy sandwich", r10),
                createMenu("Paneer Tikka Sub", 229, "Spicy paneer", r10),
                createMenu("Corn Sub", 179, "Sweet corn sandwich", r10),
                createMenu("Salad Bowl", 159, "Fresh salad", r10),
                createMenu("Wrap", 189, "Veg wrap", r10),
                createMenu("Cookie", 49, "Chocolate cookie", r10),
                createMenu("Cold Coffee", 129, "Chilled coffee", r10),
                createMenu("Chips", 40, "Crispy chips", r10),
                createMenu("Pepsi", 60, "Cold drink", r10),
                createMenu("Sub Meal Combo", 299, "Combo meal", r10)
            ));

            createSlots(r10, slotRepo);
            
            
            
            Restaurant r11 = new Restaurant();
            r11.setName("McDonalds");
            r11.setLocation("Noida");
           // Restaurant r11 = new Restaurant("McDonalds", "Noida");
            restaurantRepo.save(r11);

            menuRepo.saveAll(Arrays.asList(
                createMenu("McAloo Tikki", 59, "Veg burger", r11),
                createMenu("McVeggie", 149, "Premium burger", r11),
                createMenu("Fries", 99, "Crispy fries", r11),
                createMenu("McFlurry", 89, "Ice cream", r11),
                createMenu("Happy Meal", 199, "Kids combo", r11),
                createMenu("Veg Wrap", 129, "Soft wrap", r11),
                createMenu("Cold Coffee", 119, "Chilled drink", r11),
                createMenu("Coke", 60, "Soft drink", r11),
                createMenu("Pizza McPuff", 49, "Snack item", r11),
                createMenu("Nuggets", 149, "Crispy nuggets", r11)
            ));

            createSlots(r11, slotRepo);
            
            Restaurant r12 = new Restaurant();
            r12.setName("Dominos Express");
            r12.setLocation("Noida");

            if(!restaurantRepo.existsByName("Dominos Express")){

                restaurantRepo.save(r12);

                menuRepo.saveAll(Arrays.asList(
                    createMenu("McAloo Tikki", 59, "Veg burger", r12),
                    createMenu("McVeggie", 149, "Premium burger", r12)
                ));

                createSlots(r12, slotRepo);
            }

            };   
            }

    private MenuItem createMenu(String name, double price, String desc, Restaurant r) {
        MenuItem m = new MenuItem();
        m.setName(name);
        m.setPrice(price);
        m.setDescription(desc);
        m.setRestaurant(r);
        return m;
    }

    private void createSlots(Restaurant r, RestaurantSlotRepository slotRepo) {

        List<String> slots = Arrays.asList(
            "09:00 AM - 12:00 PM",
            "12:00 PM - 03:00 PM",
            "03:00 PM - 06:00 PM",
            "06:00 PM - 09:00 PM",
            "09:00 PM - 12:00 AM"
        );

        for (String s : slots) {
            RestaurantSlot slot = new RestaurantSlot();
            slot.setSlotTime(s);
            slot.setAvailable(true);
            slot.setTotalSeats(10);
            slot.setRestaurant(r);
            slotRepo.save(slot);
        }
        
    }
    
    
}