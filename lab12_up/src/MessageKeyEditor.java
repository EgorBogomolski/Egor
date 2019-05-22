import java.beans.*;
import java.awt.*;

/**
 * This PropertyEditor defines the enumerated values of the alignment property
 * so that a bean box or IDE can present those values to the user for selection
 **/
public class MessageKeyEditor extends PropertyEditorSupport {
    /** Return the list of value names for the enumerated type. */
    public String[] getTags() {
	return new String[] { "a", "b", "c", "d", "e" };
    }
    
    /** Convert each of those value names into the actual value. */
    public void setAsText(String s) { 
        setValue(s);
    }
    
    /** This is an important method for code generation. */
    public String getJavaInitializationString() {
	Object o = getValue();
        if (o.equals("a")) return "a";
	else if (o.equals("b")) return "b";
	else if (o.equals("c")) return "c";
        else if (o.equals("d")) return "d";
        else if (o.equals("e")) return "e";
	return null;
    }
}