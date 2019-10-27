package jp.co.ne.cardreader.activity.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

/**
 * Simple implementation of dialog fragment builder based on system class AlertDialog builder
 */
public class DialogBuilderSimpleImpl implements IDialogBuilder {

    private final AlertDialog.Builder builder;

    private String btnOkText = "";

    private String btnCancelText = "";

    private Runnable btnOkClicked = null;

    private Runnable btnCancelClicked = null;

    /**
     * Construct
     *
     * @param builder
     */
    private DialogBuilderSimpleImpl(AlertDialog.Builder builder) {
        this.builder = builder;
    }

    /**
     * Instantiate by context
     *
     * @param context
     * @return
     */
    public static IDialogBuilder getInstance(Context context) {
        return new DialogBuilderSimpleImpl(new AlertDialog.Builder(context));
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
        builder.setPositiveButton(btnOkText, (dialog, which) -> {
            dialog.dismiss();
            if (btnOkClicked != null) {
                btnOkClicked.run();
            }
        });
        builder.setNegativeButton(btnCancelText, (dialog, which) -> {
            dialog.dismiss();
            if (btnCancelClicked != null) {
                btnCancelClicked.run();
            }
        });
        return builder.create();
    }
}
