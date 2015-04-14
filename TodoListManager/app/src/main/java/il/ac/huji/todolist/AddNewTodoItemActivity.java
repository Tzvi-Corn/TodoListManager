package il.ac.huji.todolist;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import java.util.Date;


public class AddNewTodoItemActivity extends Activity {
    private EditText whatTodo;
    private DatePicker datePicker;
    private Button btnOk;
    private Button btnCancel;
    static final int DATE_DIALOG_ID = 999;

    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding);
        whatTodo = (EditText) findViewById(R.id.edtNewItem);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    // Perform action on click
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent returnIntent = new Intent();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                Date date = calendar.getTime();
                returnIntent.putExtra("title",whatTodo.getText().toString());
                returnIntent.putExtra("dueDate", date.getTime());
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
        // set current date into datepicker
        datePicker.init(year, month, day,  new DatePicker.OnDateChangedListener() {

            // when dialog box is closed, below method will be called.
            public void onDateChanged(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;

                System.out.println(" i am here");

                // set selected date into datepicker also


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_todo_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
