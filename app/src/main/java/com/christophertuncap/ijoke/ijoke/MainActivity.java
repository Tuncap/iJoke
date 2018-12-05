package com.christophertuncap.ijoke.ijoke;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends ListActivity {

    private final String DATA_FILENAME = "file.txt";
    public static int JOKE_REQUEST_CODE = 57;
    public static int DELETE_REQUEST_CODE = 81;

    public static final String TITLE_KEY = "HUSAWATSAH";
    public static final String DATE_KEY = "DA_DATE";
    public static final String DESCRIPTION_KEY = "DA_DESCRIPTION";
    public static final String LINK_KEY = "DA_LINK";
    public static final String INDEX_KEY = "DA_INDEX";


    ArrayAdapter<Joke> adapter;
    ArrayList<Joke> jokes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load file
        jokes = readFile();

        //Display jokes in ArrayAdapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jokes);
        setListAdapter(adapter);
    }

    private ArrayList<Joke> readFile() {
        ArrayList<Joke> jokes = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput(DATA_FILENAME);
            Scanner scanner = new Scanner(fis);

            while (scanner.hasNextLine()) {
                String title = scanner.nextLine();
                String date = scanner.nextLine();
                String description = scanner.nextLine();
                String link = scanner.nextLine();
                Log.i("Joke", title + ", " + date + ", " + description + ", " + link);
                Joke joke = new Joke(title, date, description, link);
                jokes.add(joke);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // ok if file does not exist
        }

        return jokes;
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {

        //String name = (String) l.getItemAtPosition(position);
        Toast.makeText(this, "You poked nothing", Toast.LENGTH_LONG).show();
        final Joke joke = (Joke) l.getItemAtPosition(position);

        final String title = joke.getTitle();
        final String date = joke.getDate();
        final String description = joke.getVerbiage();
        final String hyperlink = joke.getHyperlink();
        final int index = position;


        final String[] options = {"View", "Edit", "Delete", "Add New Joke"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an action:");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    Intent intent = new Intent(getApplicationContext(), ViewJokeActivity.class);
                    intent.putExtra(TITLE_KEY, title);
                    intent.putExtra(DATE_KEY, date);
                    intent.putExtra(DESCRIPTION_KEY, description);
                    intent.putExtra(LINK_KEY, hyperlink);
                    startActivity(intent);
                }
                else if(which == 1){
                    Intent intent = new Intent(getApplicationContext(), ManageJokeActivity.class);
                    intent.putExtra(TITLE_KEY, title);
                    intent.putExtra(DATE_KEY, date);
                    intent.putExtra(DESCRIPTION_KEY, description);
                    intent.putExtra(LINK_KEY, hyperlink);
                    intent.putExtra(INDEX_KEY,  index + "");
                    startActivityForResult(intent, JOKE_REQUEST_CODE);
                }
                else if(which == 2){
                    Log.i("Removed item ", index + "");
                    jokes.remove(index);
                    writeData();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
                else if(which == 3){
                    Intent intent = new Intent(getApplicationContext(), ManageJokeActivity.class);
                    intent.putExtra(TITLE_KEY, "");
                    intent.putExtra(DATE_KEY, "");
                    intent.putExtra(DESCRIPTION_KEY, "");
                    intent.putExtra(LINK_KEY, "");
                    intent.putExtra(INDEX_KEY,  (jokes.size()+1) + "");
                    startActivityForResult(intent, JOKE_REQUEST_CODE);
                }
                String t = title;
                Toast.makeText(MainActivity.this, "You poked: " + index, Toast.LENGTH_LONG).show();

            }
        });
        builder.show();

        super.onListItemClick(l, v, position, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == JOKE_REQUEST_CODE){

            if(resultCode == RESULT_OK){

                String position = "-1";
                String title = "BAD TITLE";
                String date = "BAD DATE";
                String description = "BAD DESCRIPTION";
                String hyperlink = "BAD HYPERLINK";

                if(data.hasExtra(TITLE_KEY)){
                    title = data.getStringExtra(TITLE_KEY);
                }

                if(data.hasExtra(DATE_KEY)){
                     date = data.getStringExtra(DATE_KEY);
                }

                if(data.hasExtra(DESCRIPTION_KEY)){
                     description = data.getStringExtra(DESCRIPTION_KEY);
                }

                if(data.hasExtra(LINK_KEY)){
                     hyperlink = data.getStringExtra(LINK_KEY);
                }

                if(data.hasExtra(INDEX_KEY)){
                    position = data.getStringExtra(INDEX_KEY);
                }

                int index = Integer.parseInt(position);

                Log.i("Index from Intent ", index + "");

                if(index != -1 && index < jokes.size()){
                    Joke joke = jokes.get(index);
                    joke.setTitle(title);
                    joke.setDate(date);
                    joke.setVerbiage(description);
                    joke.setHyperlink(hyperlink);
                    jokes.set(index,joke);

                    writeData();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jokes);
                    setListAdapter(adapter);

                }else if(index > jokes.size()){
                    Joke joke = new Joke(title, date, description, hyperlink);
                    jokes.add(joke);
                    writeData();
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jokes);
                    setListAdapter(adapter);
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void writeData() {
        try {
            FileOutputStream fos = openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);


            for (int i = 0; i < jokes.size(); i++) {
                Joke joke = jokes.get(i);
                pw.println(joke.getTitle() + "\n" + joke.getDate() +"\n" + joke.getVerbiage() + "\n" + joke.getHyperlink());
            }

            pw.close();
        } catch (FileNotFoundException e) {
            Log.e("WRITE_ERR", "Cannot save data: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}
