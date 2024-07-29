package com.chicksgold.WaterBucket.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bucket {

    private final int capacity;

    private int currentVolume;

    public Bucket(int capacity) {
        this.capacity = capacity;
    }
    
    public void fill() {
        currentVolume = capacity;
    }

    public void empty() {
        currentVolume = 0;
    }
    
    public int add(int addLiquid) {
        if (addLiquid < 0) return 0;
        int emptyCapacity = this.capacity - this.currentVolume;
        if ( addLiquid > emptyCapacity) {
            addLiquid = emptyCapacity;
        }
        this.currentVolume += addLiquid;
        return addLiquid;
    }

    public int remove(int removeLiquid) {
        if (removeLiquid < 0) return 0;
        if ( removeLiquid > this.currentVolume) {
            removeLiquid = this.currentVolume;
        }
        this.currentVolume -= removeLiquid;
        return removeLiquid;
    }

    public boolean isFull() {
        return this.currentVolume == this.capacity;
    }

    public boolean isEmpty() {
        return this.currentVolume == 0;
    }

    public boolean hasVolume(int volume) {
        return this.currentVolume == volume;
    }

}
