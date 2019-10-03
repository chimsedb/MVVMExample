package com.example.mvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm.Adapter.AdapterRC;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_NODE_REQUEST = 1;
    public static final int EDIT_NODE_REQUEST = 2;
    private NodeViewModel nodeViewModel;
    private RecyclerView recyclerView;
    private AdapterRC adapterRC;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddNodeActivity.class), ADD_NODE_REQUEST);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterRC = new AdapterRC();
        recyclerView.setAdapter(adapterRC);

        nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);
        nodeViewModel.getAllNode().observe(this, new Observer<List<Node>>() {
            @Override
            public void onChanged(List<Node> nodes) {
                //updateRC here
                adapterRC.submitList(nodes);
                Log.d("12313", "onChanged");
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                nodeViewModel.delete(adapterRC.getNodeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Node deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapterRC.setOnClickListener(new OnItemClickListener() {
            @Override
            public void ItemClickListener(Node node) {
                Intent data = new Intent(MainActivity.this, AddNodeActivity.class);
                data.putExtra(AddNodeActivity.EXTRA_ID, node.getId());
                data.putExtra(AddNodeActivity.EXTRA_TITLE, node.getTitle());
                data.putExtra(AddNodeActivity.EXTRA_DESCRIPTION, node.getDescription());
                data.putExtra(AddNodeActivity.EXTRA_PRIORITY, node.getId());
                startActivityForResult(data, EDIT_NODE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NODE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNodeActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNodeActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNodeActivity.EXTRA_PRIORITY, 1);

            Node node = new Node(title, description, priority);
            nodeViewModel.insert(node);

            Toast.makeText(getApplicationContext(), "Node saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NODE_REQUEST && resultCode == RESULT_OK) {
            Log.d("777777",data.getIntExtra(AddNodeActivity.EXTRA_ID,-1)+"");
            String title = data.getStringExtra(AddNodeActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNodeActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNodeActivity.EXTRA_PRIORITY, 1);

            Node node = new Node(title, description, priority);
            node.setId(data.getIntExtra(AddNodeActivity.EXTRA_ID,-1));
            nodeViewModel.update(node);
        } else {
            Toast.makeText(getApplicationContext(), "Node not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        nodeViewModel.deleteAllNodes();
        return super.onOptionsItemSelected(item);
    }
}
