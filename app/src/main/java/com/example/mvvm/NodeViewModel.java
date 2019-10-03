package com.example.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NodeViewModel extends AndroidViewModel {
    private NodeRepository repository;
    private LiveData<List<Node>> allNodes;

    public NodeViewModel(@NonNull Application application) {
        super(application);
        repository = new NodeRepository(application);
        allNodes = repository.getAllNode();
    }

    public void insert(Node node){
        repository.insert(node);
    }
    public void update(Node node){
        repository.update(node);
    }
    public void delete(Node node){
        repository.delete(node);
    }
    public void deleteAllNodes(){
        repository.deleteAllNodes();
    }
    public LiveData<List<Node>> getAllNode(){
        return allNodes;
    }
}
