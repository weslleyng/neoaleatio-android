package dev.weslley.aleatorio;

import org.junit.Assert;
import org.junit.Test;

import dev.weslley.aleatorio.service.RandomService;
public class RandomServiceTest {

    private RandomService randomService = new RandomService();

    @Test
    public void test_fibonacci(){
        Assert.assertEquals(2, randomService.getMaxFibonacciCircle(3));
        Assert.assertEquals(4, randomService.getMaxFibonacciCircle(8));
    }
    @Test
    public void test_generate_random_2_5(){
        Integer min = 2, max = 5;
        long result = randomService.generateRandom(min, max);
        System.out.println("random : " + result);
        Assert.assertTrue(result >= min && result <= max);
    }

    @Test
    public void test_generate_random_5_15(){
        Integer min = 5, max = 15;
        long result = randomService.generateRandom(min, max);
        System.out.println("random : " + result);
        Assert.assertTrue(result >= min && result <= max);
    }

    @Test
    public void test_generate_random_1_15(){
        Integer min = 1, max = 15;
        long result = randomService.generateRandom(min, max);
        System.out.println("random : " + result);
        Assert.assertTrue(result >= min && result <= max);
    }
    @Test
    public void test_random_number(){
        long result1 = randomService.generateRandomNumber();
        long result2 = randomService.generateRandomNumber();
        long result3 = randomService.generateRandomNumber();
        Assert.assertNotEquals(result1,result2);
        Assert.assertNotEquals(result1,result3);
    }
    @Test
    public void test_random_number_until(){
        long until = 5;
        long result1 = randomService.generateRandomNumber(5);
        long result2 = randomService.generateRandomNumber(5);
        Assert.assertNotEquals(result1,result2);

    }
}
