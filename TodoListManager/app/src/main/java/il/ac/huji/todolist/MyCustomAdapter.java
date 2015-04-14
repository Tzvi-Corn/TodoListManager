package il.ac.huji.todolist;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;


public class MyCustomAdapter extends ArrayAdapter<String[]> {

    // declaring our ArrayList of items
    private ArrayList<String[]> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<String[]> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listitem, null);
        }
        //LinearLayout linear = (LinearLayout) super.getView(position, v, parent);
        LinearLayout linear = (LinearLayout) v;
        TextView title = (TextView) linear.findViewById(R.id.txtTodoTitle);
        TextView theDate = (TextView) linear.findViewById(R.id.txtTodoDueDate);
		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        String[] i = objects.get(position);

        if (i != null) {
            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dueDate = null;

            title.setText(i[0]);
            title.setTextColor(Color.BLACK);
            if (i.length < 2 || i[1] == null) {
                theDate.setText("No due date");
                theDate.setTextColor(Color.BLACK);
            } else {
                theDate.setText(i[1]);
                theDate.setTextColor(Color.BLACK);


                try {
                    dueDate = df.parse(i[1]);
                    Date today = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dueDate);
                    int _year = cal.get(Calendar.YEAR);
                    int _month = cal.get(Calendar.MONTH);
                    int _day = cal.get(Calendar.DAY_OF_MONTH);
                    Calendar cal2 = Calendar.getInstance();
                    cal.setTime(today);
                    int year2 = cal2.get(Calendar.YEAR);
                    int month2 = cal2.get(Calendar.MONTH);
                    int day2 = cal2.get(Calendar.DAY_OF_MONTH);
                    if (dueDate.before(today) &&!(_year == year2 && _month == month2 && _day==day2)) {
                        title.setTextColor(Color.RED);
                        theDate.setTextColor(Color.RED);
                    }

                } catch (Exception e) {
                    android.util.Log.v("v", "something is wrong");
                }
            }


        }
        // the view must be returned to our activity
        return linear;

    }

}
