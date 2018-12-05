package com.christophertuncap.ijoke.ijoke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewJokeActivity extends Activity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewjokeactivity);
        Intent intent = getIntent();

        if(intent != null){
            String title = (String) intent.getSerializableExtra(MainActivity.TITLE_KEY);
            String date = (String) intent.getSerializableExtra(MainActivity.DATE_KEY);
            String description = (String) intent.getSerializableExtra(MainActivity.DESCRIPTION_KEY);
            String hyperlink = (String) intent.getSerializableExtra(MainActivity.LINK_KEY);

            TextView tv = findViewById(R.id.title_textView);
            tv.setText(title);

            tv = findViewById(R.id.date_textView);
            tv.setText(date);

            tv = findViewById(R.id.description_textView);
            tv.setText(description);

            tv = findViewById(R.id.hyperlink_textView);
            tv.setText(hyperlink);
        }

    }

    @Override
    public void onClick(View view) {

    }
}
