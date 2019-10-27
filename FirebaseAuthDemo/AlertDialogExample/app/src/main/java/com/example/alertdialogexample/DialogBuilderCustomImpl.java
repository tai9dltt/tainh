package jp.co.ne.cardreader.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import jp.co.ne.cardreader.util.CH;

/**
 * Simple implementation of dialog fragment builder based on system class AlertDialog builder
 */
public class DialogBuilderCustomImpl implements IDialogBuilder {

    private final AlertDialog.Builder builder;

    private final int layoutResId;

    private final int btnOkId;

    private final int btnCancelId;

    private String btnOkText = "";

    private String btnCancelText = "";

    private Runnable btnOkClicked = null;

    private Runnable btnCancelClicked = null;

    /**
     * Construct
     *
     * @param builder
     */
    public DialogBuilderCustomImpl(AlertDialog.Builder builder, int layoutResId, int btnOkId, int btnCancelId) {
        this.builder = builder;
        this.layoutResId = layoutResId;
        this.btnOkId = btnOkId;
        this.btnCancelId = btnCancelId;
    }

    /**
     * Instantiate by context and id of buttons
     *
     * @param context
     * @param layoutResId
     * @param btnOkId
     * @param btnCancelId
     * @return
     */
    public static IDialogBuilder getInstance(Context context, int layoutResId, int btnOkId, int btnCancelId) {
        return new DialogBuilderCustomImpl(new AlertDialog.Builder(context), layoutResId, btnOkId, btnCancelId);
    }

    @Override
    public IDialogBuilder title(String title) {
        builder.setTitle(title);
        return this;
    }

    @Override
    public IDialogBuilder message(String message) {
        builder.setMessage(message);
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
