package com.example.mvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNodeActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPrioriry;
    private Intent intentGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);

        editTextTitle = findViewById(R.id.et_title);
        editTextDescription = findViewById(R.id.et_description);
        numberPickerPrioriry = findViewById(R.id.numberPicker);

        numberPickerPrioriry.setMaxValue(10);
        numberPickerPrioriry.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Node");

        intentGetData = getIntent();
        if(intentGetData.hasExtra(EXTRA_ID)){
            Log.d("777777",intentGetData.getIntExtra(AddNodeActivity.EXTRA_ID,-1)+"");
            editTextTitle.setText(intentGetData.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intentGetData.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPrioriry.setValue(intentGetData.getIntExtra(EXTRA_PRIORITY,1));
        }
    }

    private void saveNote(){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPrioriry.getValue();



        if(title.trim().isEmpty()||description.trim().isEmpty()){
            Toast.makeText(this,"Please insert title and descrition",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        data.putExtra(EXTRA_ID,intentGetData.getIntExtra(EXTRA_ID,-1));
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_node_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_save:
                saveNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
