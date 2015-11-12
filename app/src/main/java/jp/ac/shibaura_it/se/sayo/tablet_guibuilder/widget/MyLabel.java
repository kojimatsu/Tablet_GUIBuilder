package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by 浩司 on 2015/11/06.
 */
public class MyLabel extends OutputWidget {
    public MyLabel(Context context) {
        super(context, R.id._label);
        setView(createTextView());
    }


    public MyLabel(Context context, String uniqueID) {
        super(context, R.id._label, uniqueID);
        setView(createTextView());
    }

    private TextView createTextView(){
        TextView textView = new TextView(getContext());
        textView.setText("ラベル");
        return textView;
    }
}
