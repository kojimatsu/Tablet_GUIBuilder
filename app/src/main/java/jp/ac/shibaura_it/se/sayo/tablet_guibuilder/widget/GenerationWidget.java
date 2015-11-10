package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by 浩司 on 2015/11/10.
 */
public class GenerationWidget {
    public static OutputWidget createWidget(Activity activity, int id) {
        OutputWidget view = null;
        if (id == R.id._button){
            view = new MyButton(activity, id);
        }else if (id == R.id._label){
            view = new MyLabel(activity,id);
        }
        return view;
    }

    public static View createWidget(Context context, String widgetName, String id) {
        View view = null;
        if (widgetName.equals(Widget.BUTTON.name())){
            view = new MyButton(context, R.id._button, id).getView();
        }else if (widgetName.equals(Widget.LABEL.name())){
            view = new MyLabel(context, R.id._label, id).getView();
        }
        return view;
    }
}
