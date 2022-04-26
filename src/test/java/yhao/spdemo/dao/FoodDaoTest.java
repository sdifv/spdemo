package yhao.spdemo.dao;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yhao.spdemo.entity.Food;

import java.util.Random;

@SpringBootTest
class FoodDaoTest {
    @Autowired
    private FoodDao foodDao;

    @Test
    public void saveOneFood() {
        Random rand = new Random();
        String str="abcdef";
        for (int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder();
            for (int l = 0; l < 4; l++) {
                sb.append(str.charAt(rand.nextInt(str.length())));
            }
            String name = sb.toString();
            float price = rand.nextInt(20);
            int cid = rand.nextInt(5);
            Food food = new Food(name, price, cid);
            foodDao.save(food);
            System.out.println("save %d: " + food);
        }
    }

    @Test
    public void getFood(){
        System.out.println(foodDao.getById(4));
    }

    @Test
    public void delAllFood(){
        foodDao.deleteAll();
    }
}