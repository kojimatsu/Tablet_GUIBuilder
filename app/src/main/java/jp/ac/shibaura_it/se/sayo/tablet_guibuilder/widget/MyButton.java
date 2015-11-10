package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by matsu on 2015/10/29.
 */
public class MyButton extends InputWidget {

    public MyButton(Context context, int id) {
        super(context, id);
        setView(createButton());
    }

    public MyButton(Context context, int widgetID, String uniqueID) {
        super(context, widgetID, uniqueID);
        setView(createButton());
    }

    private Button createButton(){
        Button button = new Button(getContext());
        button.setText("Button");
        button.setClickable(false);
        return button;
    }
}
