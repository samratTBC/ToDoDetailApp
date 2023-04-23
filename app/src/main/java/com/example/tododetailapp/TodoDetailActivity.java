package com.example.tododetailapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TodoDetailActivity extends AppCompatActivity {

    private int mTodoIndex;
    private String[] todoDetails;
    private static final String TODO_INDEX = "com.example.todoIndex";
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";
    public static Intent newIntent(Context todoActivityClass, int mTodoIndex) {

        Intent intent = new Intent(todoActivityClass, TodoDetailActivity.class);
        intent.putExtra(TODO_INDEX, mTodoIndex);
        return intent;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TODO_INDEX, mTodoIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo_detail);


        if(savedInstanceState!= null)
        {
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX,0);
        }
        
        Resources res = getResources();
        todoDetails = res.getStringArray(R.array.todo_detail);
        
        int mTodoIndex = getIntent().getIntExtra(TODO_INDEX,0);
        updateTextViewTodoDetaul(mTodoIndex);


        CheckBox checkBoxIsComplete = (CheckBox) findViewById(R.id.checkBoxIsComplete);
        checkBoxIsComplete.setOnClickListener(listener);
        
    }


    private final View.OnClickListener listener = view -> {
        int id = view.getId();
        if(id== R.id.checkBoxIsComplete)
        {
            CheckBox checkboxIsComplete = (CheckBox) findViewById(R.id.checkBoxIsComplete);
            setIsComplete(checkboxIsComplete.isChecked());
            finish();
        }
    };
    private void updateTextViewTodoDetaul(int mTodoIndex) {
        final TextView textViewTodoDetail;
        textViewTodoDetail = (TextView) findViewById(R.id.textViewTodoDetail);

        textViewTodoDetail.setText(todoDetails[mTodoIndex]);
    }

    private void setIsComplete(boolean isChecked) {

        /* celebrate with a static Toast! */
        if(isChecked){
            Toast.makeText(TodoDetailActivity.this,
                    "Hurray, it's done!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TodoDetailActivity.this,
                    "There is always tomorrow!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        intent.putExtra(IS_TODO_COMPLETE, isChecked);
        setResult(RESULT_OK, intent);
    }
}