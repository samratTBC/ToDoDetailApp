package com.example.tododetailapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {


    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";
    private String[] mTodos;
    private int mTodoIndex =0;

    private Button todoDetail_btn;
    private TextView textViewTodo;

    private TextView textViewComplete;

    private Button buttonNext, buttonPrev, buttonTodoDetail;
    private static final String TODO_INDEX = "com.example.todoIndex";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TODO_INDEX, mTodoIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        if (savedInstanceState != null){
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);

        todoDetail_btn = findViewById(R.id.buttonTodoDetail);
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrev = findViewById(R.id.buttonPrev);
        buttonTodoDetail = findViewById(R.id.buttonTodoDetail);
        textViewTodo = findViewById(R.id.textViewTodo);

        setTextViewComplete("");

        textViewTodo.setText(mTodos[mTodoIndex].toString());

        todoDetail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TodoDetailActivity.newIntent(TodoActivity.this, mTodoIndex);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTodoIndex = (mTodoIndex + 1) % mTodos.length;
                textViewTodo.setText(mTodos[mTodoIndex].toString());
                setTextViewComplete("");
            }
        });

        ActivityResultLauncher<Intent> todoDetailActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent intent = result.getData();
                        boolean isTodoComplete = intent.getBooleanExtra(IS_TODO_COMPLETE, false);
                        updateTodoComplete(isTodoComplete);
                    }
                });

        buttonTodoDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TodoDetailActivity.newIntent(TodoActivity.this, mTodoIndex);
                todoDetailActivityLauncher.launch(intent);
            }
        });

    }

    private void updateTodoComplete(boolean isTodoComplete) {
        textViewTodo = findViewById(R.id.textViewTodo);

        if(isTodoComplete)
            textViewTodo.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundSuccess));
        else
            textViewTodo.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccess));

        setTextViewComplete("\u2713");
    }

    private void setTextViewComplete(String message) {

        textViewComplete = findViewById(R.id.textViewComplete);
        textViewComplete.setText(message);
    }
}