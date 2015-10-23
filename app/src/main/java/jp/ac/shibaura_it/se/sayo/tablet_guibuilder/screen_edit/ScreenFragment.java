package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLReading;


/**
 * Created by matsu on 2014/11/07.
 */
public class ScreenFragment extends Fragment {

    private LinearLayout root;

    /**
     *
     * @param usecaseName ユースケース名
     * @return
     */
    protected static ScreenFragment newInstance(String usecaseName) {
        ScreenFragment fragment = new ScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(XMLReading.USECASE_NAME, usecaseName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = new LinearLayout(getActivity());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ScreenEditActivity activity = (ScreenEditActivity) getActivity();
                    View view = activity.onClickedFromScreenFragment();
                    Debug.log("@");
//                    Button b = (Button) v;
//                    Debug.log((String) b.getText());
                    root.addView(new Button(getActivity()));
                } catch (ClassCastException e) {
                    throw new ClassCastException("activity が OnClickListener を実装していません.");
                }
            }
        });
    }
}
