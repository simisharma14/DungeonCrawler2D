package com.example.cs2340project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340project.model.Room;

public class RoomViewModel extends ViewModel {
    private static MutableLiveData<Room> roomLiveData = new MutableLiveData<>();

    public RoomViewModel() {
        roomLiveData.setValue(new Room(1));
    }

    public RoomViewModel(Room room) {
        roomLiveData.postValue(room);
    }

    public LiveData<Room> getRoom() {
        return roomLiveData;
    }

    public void nextRoom() {
        Room currRoom = roomLiveData.getValue();
        currRoom.nextRoom();
        roomLiveData.postValue(currRoom);
    }
    public int getRoomNumber() {
        Room currRoom = roomLiveData.getValue();
        return currRoom.getRoomType();
    }

    public int getRoomTileType(int row, int col) {
        Room currRoom = roomLiveData.getValue();
        return currRoom.getTile(row, col).getType();
    }

    public void clearRoom() {
        roomLiveData.postValue(null);
    }
}