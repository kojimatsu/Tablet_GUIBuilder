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

    private Button button;

    public MyButton(Context context, View clickedView) {
        super(context,clickedView);
        button = new Button(getContext());
        button.setText("ボタン");
        button.setClickable(false);
        view = button;
    }

}
