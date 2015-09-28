package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;


/**
 * Created by matsu on 2014/11/07.
 */
public class ScreenFragment extends Fragment {

    private final static String FILE_PATH = "NAME";

    /**
     *
     * @param useCasePath プロジェクト名/ユースケース名
     * @return
     */
    protected static ScreenFragment newInstance(String useCasePath) {
        ScreenFragment fragment = new ScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FILE_PATH, useCasePath);
        fragment.setArguments(bundle);
        return fragment;
    }

    // ここのメソッドは消すかは保留
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        String filePath = getArguments().getString(FILE_PATH);
//        XMLReading xmlReading = new XMLReading("/休講ナビ/cancel.xml");
//        XmlPullParser xmlPullParser = xmlReading.getXmlPullParser();
//        if (xmlPullParser == null){
//            DebugLog.log("null");
//        }
//        return inflater.inflate(xmlPullParser,container,false);
//    }
}
