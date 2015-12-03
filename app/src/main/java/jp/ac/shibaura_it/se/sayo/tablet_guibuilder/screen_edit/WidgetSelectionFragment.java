package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.CreationWidgetController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;

/**
 * Created by matsu on 2014/11/07.
 */
public class WidgetSelectionFragment extends Fragment implements
        View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.widget_selection_fragment, container,
                false);
    }

    @Override
    public void onStart() {
        setAbstractWidget();

        TextView layoutButton = (TextView) getActivity().findViewById(
                R.id._layout);
        layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layoutlayout = (LinearLayout) getActivity()
                        .findViewById(R.id.layout_layout);
                LinearLayout inputWidgetLayout = (LinearLayout) getActivity()
                        .findViewById(R.id.inputwidget_layout);
                LinearLayout outputWidgetLayout = (LinearLayout) getActivity()
                        .findViewById(R.id.outputwidget_layout);
                if (layoutlayout.getVisibility() == View.GONE) {
                    layoutlayout.setVisibility(View.VISIBLE);
                    inputWidgetLayout.setVisibility(View.GONE);
                    outputWidgetLayout.setVisibility(View.GONE);
                } else {
                    layoutlayout.setVisibility(View.GONE);
                }

            }
        });

        TextView inputWidgetButton = (TextView) getActivity().findViewById(
                R.id._inputWidget);
        inputWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layoutlayout = (LinearLayout) getActivity()
                        .findViewById(R.id.layout_layout);
                LinearLayout inputWidgetLayout = (LinearLayout) getActivity()
                        .findViewById(R.id.inputwidget_layout);
                LinearLayout outputWidgetLayout = (LinearLayout) getActivity()
                        .findViewById(R.id.outputwidget_layout);
                if (inputWidgetLayout.getVisibility() == View.GONE) {
                    layoutlayout.setVisibility(View.GONE);
                    inputWidgetLayout.setVisibility(View.VISIBLE);
                    outputWidgetLayout.setVisibility(View.GONE);
                } else {
                    inputWidgetLayout.setVisibility(View.GONE);
                }

            }
        });

        TextView outputWidgetButton = (TextView) getActivity().findViewById(
                R.id._outputWidget);
        outputWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layoutlayout = (LinearLayout) getActivity()
                        .findViewById(R.id.layout_layout);
                LinearLayout inputWidgetLayout = (LinearLayout) getActivity()
                        .findViewById(R.id.inputwidget_layout);
                LinearLayout outputWidgetLayout = (LinearLayout) getActivity()
                        .findViewById(R.id.outputwidget_layout);
                if (outputWidgetLayout.getVisibility() == View.GONE) {
                    layoutlayout.setVisibility(View.GONE);
                    inputWidgetLayout.setVisibility(View.GONE);
                    outputWidgetLayout.setVisibility(View.VISIBLE);
                } else {
                    outputWidgetLayout.setVisibility(View.GONE);
                }

            }
        });

        super.onStart();
    }

    /**
     * 各ウィジェットのviewを初期化
     */
    private void setAbstractWidget() {
        LinearLayout layoutlayout = (LinearLayout) getActivity().findViewById(
                R.id.layout_layout);
        View view;
        for (int i = 0; i < layoutlayout.getChildCount(); i++) {
            view = layoutlayout.getChildAt(i);
            view.setOnClickListener(this);
            view.setBackgroundResource(R.drawable.view_pressed_false);
        }
        LinearLayout inputWidgetLayout = (LinearLayout) getActivity()
                .findViewById(R.id.inputwidget_layout);
        for (int i = 0; i < inputWidgetLayout.getChildCount(); i++) {
            view = inputWidgetLayout.getChildAt(i);
            view.setOnClickListener(this);
            view.setBackgroundResource(R.drawable.view_pressed_false);
        }
        LinearLayout outputWidgetLayout = (LinearLayout) getActivity()
                .findViewById(R.id.outputwidget_layout);
        for (int i = 0; i < outputWidgetLayout.getChildCount(); i++) {
            view = outputWidgetLayout.getChildAt(i);
            view.setOnClickListener(this);
            view.setBackgroundResource(R.drawable.view_pressed_false);
        }

    }

    private static int widgetID = -1;
    private static boolean clicked = false;
    private static View clickedView;

    @Override
    public void onClick(View v) {
        clickedView = v;
        setAbstractWidget();
        if (v.getId() != widgetID) {
            clicked = true;
            widgetID = clickedView.getId();
            clickedView.setBackgroundResource(R.drawable.view_pressed_true);
        }else {
            if (!clicked) {
                clicked = true;
                widgetID = clickedView.getId();
                clickedView.setBackgroundResource(R.drawable.view_pressed_true);
            }else {
                clicked = false;
                widgetID = -1;
                clickedView.setBackgroundResource(R.drawable.view_pressed_false);
            }
        }
    }

    public static int getWidgetID(){
        if (clicked){
            return widgetID;
        }
        return -1;
    }
}