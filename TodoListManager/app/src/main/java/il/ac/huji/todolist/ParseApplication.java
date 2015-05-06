package il.ac.huji.todolist;

/**
 * Created by Tzvi on 30/04/2015.
 */
import android.app.Application;
import android.content.Intent;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(getApplicationContext());
        ParseObject.registerSubclass(TodoItem.class);
        Parse.initialize(getApplicationContext(), "Es7w85qI3iJpxrp4h13bQAQvJM172s9JZBjAiO9b", "T3s5UsNOBqG1IHEPzUq4I98CnUclDDQn0zxlithW");
        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();

    }
}
