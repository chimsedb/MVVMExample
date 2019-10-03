package com.example.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NodeRepository {
    private NodeDAO nodeDAO;
    private LiveData<List<Node>> allNodes;

    public NodeRepository(Application application) {
        NodeDatabase database = NodeDatabase.getInstance(application);
        nodeDAO = database.nodeDAO();
        allNodes = nodeDAO.getAllNodes();
    }

    public void insert(Node node){
        new insertNodeAsynTask(nodeDAO).execute(node);
    }

    public void update(Node node){
        new updateNodeAsynTask(nodeDAO).execute(node);
    }

    public void delete(Node node){
        new deleteNodeAsynTask(nodeDAO).execute(node);
    }

    public void deleteAllNodes(){
        new deleteAllNodeAsynTask(nodeDAO).execute();
    };

    public LiveData<List<Node>> getAllNode(){
        return allNodes;
    }

    private static class insertNodeAsynTask extends AsyncTask<Node,Void,Void> {
        private NodeDAO nodeDAO;

        public insertNodeAsynTask(NodeDAO nodeDAO) {
            this.nodeDAO = nodeDAO;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDAO.insert(nodes[0]);
            return null;
        }
    }
    private static class updateNodeAsynTask extends AsyncTask<Node,Void,Void> {
        private NodeDAO nodeDAO;

        public updateNodeAsynTask(NodeDAO nodeDAO) {
            this.nodeDAO = nodeDAO;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDAO.update(nodes[0]);
            return null;
        }
    }
    private static class deleteNodeAsynTask extends AsyncTask<Node,Void,Void> {
        private NodeDAO nodeDAO;

        public deleteNodeAsynTask(NodeDAO nodeDAO) {
            this.nodeDAO = nodeDAO;
        }

        @Override
        protected Void doInBackground(Node... nodes) {
            nodeDAO.delete(nodes[0]);
            return null;
        }
    }
    private static class deleteAllNodeAsynTask extends AsyncTask<Void,Void,Void> {
        private NodeDAO nodeDAO;

        public deleteAllNodeAsynTask(NodeDAO nodeDAO) {
            this.nodeDAO = nodeDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            nodeDAO.deleteAllNode();
            return null;
        }
    }
}
