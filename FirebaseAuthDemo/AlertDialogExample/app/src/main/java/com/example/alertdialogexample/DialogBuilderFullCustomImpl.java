package jp.co.ne.cardreader.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.co.ne.cardreader.util.CH;

/**
 * Builder for fully customized dialog, with dynamic title and message
 */
public class DialogBuilderFullCustomImpl implements IDialogBuilder {

    private final AlertDialog.Builder builder;

    private final int layoutResId;

    private final int btnOkId;

    private final int btnCancelId;

    private final int tvDlgTitleId;

    private final int tvDlgMessageId;

    private String btnOkText = "";

    private String btnCancelText = "";

    private String title = "";

    private String message = "";

    private Runnable btnOkClicked = null;

    private Runnable btnCancelClicked = null;

    /**
     * Construct
     *
     * @param builder
     */
    public DialogBuilderFullCustomImpl(AlertDialog.Builder builder, int layoutResId, int tvDlgTitleId, int tvDlgMessageId, int btnOkId, int btnCancelId) {
        this.builder = builder;
        this.layoutResId = layoutResId;
        this.tvDlgTitleId = tvDlgTitleId;
        this.tvDlgMessageId = tvDlgMessageId;
        this.btnOkId = btnOkId;
        this.btnCancelId = btnCancelId;
    }

    /**
     * Instantiate by context and id of elements
     *
     * @param context
     * @param layoutResId
     * @param tvDlgTitleId
     * @param tvDlgMessageId
     * @param btnOkId
     * @param btnCancelId
     * @return
     */
    public static IDialogBuilder getInstance(Context context, int layoutResId, int tvDlgTitleId, int tvDlgMessageId, int btnOkId, int btnCancelId) {
        return new DialogBuilderFullCustomImpl(new AlertDialog.Builder(context), layoutResId, tvDlgTitleId, tvDlgMessageId, btnOkId, btnCancelId);
    }

    @Override
    public IDialogBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IDialogBuilder message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public IDialogBuilder btnOkText(String text) {
        this.btnOkText = text;
        return this;
    }

    @Override
    public IDialogBuilder btnCancelText(String text) {
        this.btnCancelText = text;
        return this;
    }

    @Override
    public IDialogBuilder btnOKClicked(Runnable runnable) {
        this.btnOkClicked = runnable;
        return this;
    }

    @Override
    public IDialogBuilder btnCancelClicked(Runnable runnable) {
        this.btnCancelClicked = runnable;
        return this;
    }

    @Override
    public Dialog build() {
        LayoutInflater layoutInflater = LayoutInflater.from(this.builder.getContext());
        View layoutView = layoutInflater.inflate(this.layoutResId, null);
        builder.setView(layoutView);
        Dialog dlg = builder.create();
        // Hide the system-generated background
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Title
        TextView tvTitle = layoutView.findViewById(this.tvDlgTitleId);
        if (tvTitle != null) {
            tvTitle.setText(this.title);
        }
        // Message
        TextView tvMessage = layoutView.findViewById(this.tvDlgMessageId);
        if (tvMessage != null) {
            tvMessage.setText(this.message);
        }
        // OK button
        Button btnOk = layoutView.findViewById(this.btnOkId);
        if (btnOk != null) {
            if (!CH.isNullOrEmpty(btnOkText)) {
                btnOk.setText(btnOkText);
            }

            btnOk.setOnClickListener(view -> {
                dlg.dismiss();
                if (btnOkClicked != null) {
                    btnOkClicked.run();
                }
            });
        }
        // Cancel button
        Button btnCancel = layoutView.findViewById(this.btnCancelId);
        if (btnCancel != null) {
            if (!CH.isNullOrEmpty(btnCancelText)) {
                btnCancel.setText(btnCancelText);
            }

            btnCancel.setOnClickListener(view -> {
                dlg.dismiss();
                if (btnCancelClicked != null) {
                    btnCancelClicked.run();
                }
            });
        }
        return dlg;
    }
}
