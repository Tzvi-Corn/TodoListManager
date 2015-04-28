package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.net.Uri;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import android.content.Intent;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.database.sqlite.SQLiteDatabase;


public class TodoListManagerActivity extends ActionBarActivity {

    ArrayList<String[]> todoList;
    MyCustomAdapter todoAdapter;
    AlertDialog.Builder alertDialogBuilder;
    ListView listView;
    AlertDialog alertDialog;
    EditText toAdd;
    CharSequence[] items;
    AlertDialog.Builder builder;
    mySqlLite myDb;
    ArrayList<Integer> rowIdHolder;
    long heighestId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rowIdHolder = new ArrayList<>();
        myDb = new mySqlLite(getApplicationContext());
        SQLiteDatabase db = myDb.getReadableDatabase();
        todoList = new ArrayList<String[]>();
        Cursor resultSet = db.rawQuery("Select * from Todo",null);

        resultSet.moveToFirst();
        for (int i = 0; i < resultSet.getCount(); ++i) {
            String title = resultSet.getString(1);
            String dueDate = resultSet.getString(2);
            heighestId = Math.max (resultSet.getLong(0), heighestId);
            String id = Long.toString(resultSet.getLong(0));
            String[] entry = new String[] {title, dueDate, id};
            todoList.add(entry);

            resultSet.moveToNext();
        }

        setContentView(R.layout.activity_todo_list_manager);



        todoAdapter = new MyCustomAdapter(getApplicationContext(),R.layout.listitem, todoList);
        listView = (ListView) findViewById(R.id.lstTodoItems);
        listView.setAdapter((todoAdapter));
        toAdd = (EditText) findViewById(R.id.edtNewItem);
        listView.setLongClickable(true);
        alertDialogBuilder = new AlertDialog.Builder(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                final int myPos = pos;
                // TODO Auto-generated method stub


                  if (todoList.get(pos)[0].startsWith("Call ")) {
                      items = new CharSequence[2];
                      items[0] = "Delete item";
                      items[1] = todoList.get(pos)[0];
                  } else {
                      items = new CharSequence[1];
                      items[0] = "Delete item";
                  }

                builder = new AlertDialog.Builder(TodoListManagerActivity.this);
                builder.setTitle(todoList.get(pos)[0]);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {

                            long id = Long.parseLong(todoList.get(myPos)[2]);

                            SQLiteDatabase db = myDb.getWritableDatabase();
                            db.delete(mySqlLite.TABLE_TODO, mySqlLite.KEY_ID + "='" + id + "'", null);
                            todoList.remove(myPos);
                            todoAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        }
                        if (item == 1) {
                            String phone = todoList.get(myPos)[0];
                            phone = phone.substring(5);
                            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                            startActivity(dial);
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();



                return true;
            }


        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //android.util.Log.v("c", "am here");
        if (id == R.id.add) {
            Intent intent = new Intent(this, AddNewTodoItemActivity.class);

            startActivityForResult(intent, 1);
            //startActivity(intent);



        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String theTitle = data.getStringExtra("title");
                Date dueDate = new Date();
                dueDate.setTime(data.getLongExtra("dueDate", -1));
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String theDate = df.format(dueDate);


                SQLiteDatabase db = myDb.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(mySqlLite.KEY_ID, heighestId + 1);
                ++heighestId;
                //values.put(mySqlLite.KEY_ID, todoList.size());
                values.put(mySqlLite.KEY_TITLE, theTitle);
                values.put(mySqlLite.KEY_DUE_DATE, theDate);

                // Insert the new row, returning the primary key value of the new row
                long newRowId;
                newRowId = db.insert(mySqlLite.TABLE_TODO,null,values);

                todoList.add(new String[] {theTitle, theDate, Long.toString(heighestId)});
                todoAdapter.notifyDataSetChanged();

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }


}
