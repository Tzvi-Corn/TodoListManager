package il.ac.huji.todolist;
import com.parse.ParseObject;
import com.parse.ParseClassName;

@ParseClassName("TodoItem")
public class TodoItem extends ParseObject {

   public TodoItem() {
        super();
   }
    // Add a constructor that contains core properties
    public TodoItem(String todo, String date) {
        super();
        setItem(todo, date);
    }

    // Use getString and others to access fields
    public String[] getItem() {
        String[] item = new String[3];
        item[0] = getString("todo");
        item[1] = getString("date");
        item[2] = getObjectId();
        return item;
    }

    // Use put to modify field values
    public void setItem(String todo, String date) {
        put("todo", todo);
        put("date", date);
    }
}

