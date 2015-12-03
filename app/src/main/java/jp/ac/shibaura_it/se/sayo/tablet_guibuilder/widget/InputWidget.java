package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;
import android.view.View;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;

/**
 * Created by matsu on 2015/10/29.
 */
public class InputWidget extends OutputWidget{


    protected InputWidget(Context context, Mode mode, int widgetID, int uniqueID, View createView) {
        super(context, mode, widgetID, uniqueID, createView);
        setIsInputWidget();
    }
}
