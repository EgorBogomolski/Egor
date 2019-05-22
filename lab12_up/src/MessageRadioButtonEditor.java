import java.beans.*;
import java.awt.*;

/**
 * This PropertyEditor defines the enumerated values of the alignment property
 * so that a bean box or IDE can present those values to the user for selection
 **/
public class MessageRadioButtonEditor extends PropertyEditorSupport {
    /** Return the list of value names for the enumerated type. */
    public String[] getTags() {
	return new String[] { "Choose me", "Do not choose", "Smth"};
    }
    
    /** Convert each of those value names into the actual value. */
    public void setAsText(String s) { 
        setValue(s);
    }
    
    /** This is an important method for code generation. */
    public String getJavaInitializationString() {
	Object o = getValue();
        if (o.equals("Choose me")) return "Choose me";
	else if (o.equals("Do not choose")) return "Do not choose";
        else if (o.equals("Smth")) return "Smth";
	return null;
    }
}
