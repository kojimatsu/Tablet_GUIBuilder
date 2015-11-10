package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformation;


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
        bundle.putString(ShareInformation.ATTRIBUTE_NAME, usecaseName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = new LinearLayout(getActivity());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        root = ShareInformation.getScreen(root,getUsecaseName());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenEditActivity activity = (ScreenEditActivity) getActivity();
                OutputWidget view = activity.onClickedFromScreenFragment();
                if (view != null) {
                    root.addView(view.getView());
                    String usecaseName = getUsecaseName();
                    ShareInformation.writeWidget(usecaseName, view);
                }
            }
        });
    }

    private String getUsecaseName(){
        Bundle bundle = getArguments();
        return bundle.getString(ShareInformation.ATTRIBUTE_NAME);
    }
}
