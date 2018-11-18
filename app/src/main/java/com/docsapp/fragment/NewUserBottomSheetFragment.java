package com.docsapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.docsapp.R;
import com.docsapp.utils.UIUtils;

/**
 * Created by kushaal singla on 18-Nov-18.
 */
public class NewUserBottomSheetFragment extends BottomSheetDialogFragment {
    private CallBack callBack;
    private TextInputEditText etName;
    private FloatingActionButton btnStart;

    public void initViews(View view) {
        etName = view.findViewById(R.id.et_name);
        btnStart = view.findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etName.getText().toString())) {
                    if (callBack != null) {
                        callBack.onNewUser(etName.getText().toString());
                        dismiss();
                    }
                } else {
                    UIUtils.makeToast(getString(R.string.empty_name));
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_new_user, container, false);
        initViews(view);
        return view;
    }

    public static NewUserBottomSheetFragment getInstance(NewUserBottomSheetFragment.CallBack callBack) {
        NewUserBottomSheetFragment n = new NewUserBottomSheetFragment();
        n.setListener(callBack);
        return n;
    }

    private void setListener(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onNewUser(String name);
    }
}
