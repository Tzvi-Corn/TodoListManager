package il.ac.huji.todolist;

/**
 * Created by Tzvi on 08/04/2015.
 */
public class itemModel {
    private long id;
    private String[] item;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getItem() {
        return item;
    }

    public void setItem(String[] item) {
        this.item = item;
    }

}
