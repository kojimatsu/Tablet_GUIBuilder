package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by matsu on 2015/10/29.
 */
public class InputWidget extends OutputWidget{

    protected InputWidget(Context context, View clickedView) {
        super(context,clickedView);
        setIsInputwidget();
    }


}
