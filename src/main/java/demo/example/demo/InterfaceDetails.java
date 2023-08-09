package demo.example.demo;

import demo.example.demo.entity.OutDoc;
import demo.example.demo.form.InterfaceDetailsForm;
import demo.example.demo.form.InterfaceForm;

import java.util.TreeMap;

public class InterfaceDetails {

    public static void showInterfaceDetails(OutDoc outDoc){
        InterfaceDetailsForm dialog = new InterfaceDetailsForm(outDoc);
        dialog.pack();
        dialog.setVisible(true);
    }
}
