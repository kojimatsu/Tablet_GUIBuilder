package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.CreationWidgetController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;


/**
 * Created by matsu on 2014/11/07.
 */
public class ScreenFragment extends Fragment implements View.OnClickListener {

    public static final String MODE = "mode";

    private LinearLayout root;
    /**
     *
     * @return
     */
    protected static ScreenFragment newInstance() {
        ScreenFragment fragment = new ScreenFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = getDefaultLinearLayout();
        ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
        Bundle bundle = getArguments();
        Mode mode = (Mode) bundle.get(MODE);
        root = shareInformationManager.getScreen(mode, getUseCaseName(), root);
        if (mode == Mode.EDITION){
            root.setOnClickListener(this);
        }
        return root;
    }

    private LinearLayout getDefaultLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return linearLayout;
    }


    public String getUseCaseName(){
        Bundle bundle = getArguments();
        return bundle.getString(ShareInformationManager.ATTRIBUTE_NAME);
    }

    /**
     * 画面編集時のView追加処理
     * @param v
     */
    @Override
    public void onClick(View v) {
        int widgetID = WidgetSelectionFragment.getWidgetID();
        if (widgetID != -1) {
            OutputWidget createdView = CreationWidgetController.createWidget(Mode.EDITION,getActivity(),widgetID,-1);
            root.addView(createdView.getView());
            String useCaseName = getUseCaseName();
            ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
            shareInformationManager.writeWidget(useCaseName, createdView);
        }
    }
}
