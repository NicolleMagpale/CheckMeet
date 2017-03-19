package com.example.checkmeet.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.checkmeet.R;

public class AddGroupActivity extends AppCompatActivity {

    ImageView ivCancel, ivAdd;
    EditText etName;
    ImageView ivAddMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        ivCancel = (ImageView) findViewById(R.id.btn_cancel);
        ivAdd = (ImageView) findViewById(R.id.btn_create);
        etName = (EditText) findViewById(R.id.et_group_name);
        ivAddMember = (ImageView) findViewById(R.id.iv_add);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ViewContactsActivity.class);
                startActivity(i);
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ViewContactsActivity.class);
                startActivity(i);
            }
        });

        ivAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ViewContactsActivity.class);
                startActivity(i);
            }
        });
    }
}
