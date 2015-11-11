package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 浩司 on 2015/11/06.
 */
public class MyLabel extends OutputWidget {
    public MyLabel(Context context, int widgetID) {
        super(context, widgetID);
        setView(createTextView());
    }


    public MyLabel(Context context, int widgetID, String uniqueID) {
        super(context, widgetID, uniqueID);
        setView(createTextView());
    }

    private TextView createTextView(){
        TextView textView = new TextView(getContext());
        textView.setText("ラベル");
        return textView;
    }
}
