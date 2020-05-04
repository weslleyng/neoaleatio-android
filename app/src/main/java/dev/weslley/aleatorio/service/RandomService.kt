package dev.weslley.aleatorio.service

import kotlin.random.Random

class RandomService {

    fun generateRandom(min: Long, max: Long): Long {

        return Random.nextLong(max)
    }

    fun generateRandomNumber(): Long {
        return Random.nextLong()
    }

    fun generateRandomNumber(until : Long): Long {
        return Random.nextLong(until+1)+1
    }

    fun getMaxFibonacciCircle(limit: Long): Int {
        var last = 1
        var fibonacci = last
        var circle = 0
        while (fibonacci + last <= limit) {
            var current = fibonacci;
            fibonacci += last
            last = current;
            circle++
        }
        return circle;
    }

}

