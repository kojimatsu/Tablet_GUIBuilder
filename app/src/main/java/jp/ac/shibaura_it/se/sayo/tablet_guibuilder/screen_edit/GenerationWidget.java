package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by matsu on 2015/10/28.
 */
public class GenerationWidget {

    public static View createView(Activity activity, View clickedView){
        View view = null;
        int id = clickedView.getId();

        if (id == R.id._button){
            Button button = new Button(activity);
            button.setText("ボタン");
            button.setClickable(false);
            view = button;
        }else if (id == R.id._label){
            TextView textView = new TextView(activity);
            textView.setText("ラベル");
            view = textView;
        }
        return view;
    }
}
