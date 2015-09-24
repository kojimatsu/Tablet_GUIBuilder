package jp.ac.shibaura_it.se.sayo.tablet_guibuilder;

import android.util.Log;

/**
 * Created by matsu on 2014/10/26.
 */
public class DebugLog  {

    static public void log(String text){
        Log.d("TAG", text);
    }
    static public void log(int text){
        Log.d("TAG", Integer.toString(text));
    }
}
