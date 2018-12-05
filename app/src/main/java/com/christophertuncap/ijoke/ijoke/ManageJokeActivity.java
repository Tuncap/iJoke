package com.christophertuncap.ijoke.ijoke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ManageJokeActivity extends Activity
implements View.OnClickListener{

    public String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managejokeactivity);
        Intent intent = getIntent();

        if(intent != null){
            String title = (String) intent.getSerializableExtra(MainActivity.TITLE_KEY);
            String date = (String) intent.getSerializableExtra(MainActivity.DATE_KEY);
            String description = (String) intent.getSerializableExtra(MainActivity.DESCRIPTION_KEY);
            String hyperlink = (String) intent.getSerializableExtra(MainActivity.LINK_KEY);
            index = (String) intent.getSerializableExtra(MainActivity.INDEX_KEY);

            EditText editText = findViewById(R.id.title_editText);
            editText.setText(title);

            editText = findViewById(R.id.date_editText);
            editText.setText(date);

            editText = findViewById(R.id.desc_editText);
            editText.setText(description);

            editText = findViewById(R.id.link_editText);
            editText.setText(hyperlink);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed(){
        EditText editText = findViewById(R.id.title_editText);
        String title = editText.getText().toString();

        editText = findViewById(R.id.date_editText);
        String date = editText.getText().toString();

        editText = findViewById(R.id.desc_editText);
        String description = editText.getText().toString();

        editText = findViewById(R.id.link_editText);
        String hyperlink = editText.getText().toString();


        if(title.isEmpty() || date.isEmpty() || description.isEmpty()){
            setResult(RESULT_CANCELED);
        }
        else{
            Intent intent = new Intent();
            intent.putExtra(MainActivity.TITLE_KEY, title);
            intent.putExtra(MainActivity.DATE_KEY, date);
            intent.putExtra(MainActivity.DESCRIPTION_KEY, description);
            intent.putExtra(MainActivity.INDEX_KEY, index);
            if(hyperlink.isEmpty()){
                intent.putExtra(MainActivity.LINK_KEY, "EMPTY");
            }
            else {
                intent.putExtra(MainActivity.LINK_KEY, hyperlink);
            }

            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();

    }
}
