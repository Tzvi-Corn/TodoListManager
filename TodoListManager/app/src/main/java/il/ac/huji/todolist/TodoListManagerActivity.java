package il.ac.huji.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {

    ArrayList<String> todoList;
    MyCustomAdapter todoAdapter;
    AlertDialog.Builder alertDialogBuilder;
    ListView listView;
    AlertDialog alertDialog;
    EditText toAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        todoList = new ArrayList<>();
        todoAdapter = new MyCustomAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, todoList);
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



                // set title
                alertDialogBuilder.setTitle(todoList.get(pos));

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setNegativeButton("Delete item", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                todoList.remove(myPos);
                                todoAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                            }
                        });


                // create alert dialog
                alertDialog = alertDialogBuilder.create();

                // show it
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

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.add) {
            if (!(toAdd.getText() == null || toAdd.getText().toString() == null || toAdd.getText().toString().equals(""))) {
                todoList.add(toAdd.getText().toString());
                toAdd.setText("");
                todoAdapter.notifyDataSetChanged();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    @Override

    public boolean onMenuOpened(int featureId, Menu menu)
    {

        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                   int a = 7;
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }*/
}
