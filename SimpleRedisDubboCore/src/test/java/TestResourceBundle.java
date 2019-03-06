import java.util.Locale;
import java.util.ResourceBundle;



/**
 * @author zd
 *  src路径下的文件在编译后会放到WEB-INF/clases路径下（默认的classpath）
 *  直接放到WEB-INF下的话，是不在classpath下的。
 */
public class TestResourceBundle {

    public static final String PROPERTIES_FILE_NAME = "property";
    public static final String MY_NAME_KEY = "name";
    public static final String MY_VALUE_KEY = "value";
 
    private static String myName;
    private static String myValue;
    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME, Locale.ENGLISH);
            myName = bundle.getString(MY_NAME_KEY).trim();
            myValue = bundle.getString(MY_VALUE_KEY).trim();
        } catch (Exception ex) {
            System.err.println("[Property]:Can't Load property.properties");
        }
    }

    public static void print() {
        System.out.println("My name is: " + myName);
        System.out.println("My value is: " + myValue);
    }

    public static void main(String[] args) {
        //读取property.properties
    	TestResourceBundle.print();
       // TestResourceBundle trb = new  TestResourceBundle();
        //trb.print();
        //读取protest.properties
        ResourceBundle rb = ResourceBundle.getBundle("protest");
        System.out.println(rb.getString("welcome"));
        //读取prop_en.properties
        ResourceBundle rb_prop = ResourceBundle.getBundle("redis", Locale.ENGLISH);
        System.out.println(rb_prop.getString("redis.host"));

    }
}
