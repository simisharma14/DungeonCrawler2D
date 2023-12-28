package com.example.cs2340project.model;

public interface Subject {
    public void registerObserver(MovementObserver observer);
    public void unregisterObserver(MovementObserver observer);
    public void notifyObservers();
}
