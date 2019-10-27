package jp.co.ne.cardreader.activity.fragment;

import android.app.Dialog;

/**
 * Builder for dialog fragment
 */
public interface IDialogBuilder {
    /**
     * Set title
     *
     * @param title
     * @return
     */
    IDialogBuilder title(String title);

    /**
     * Set message
     *
     * @param message
     * @return
     */
    IDialogBuilder message(String message);

    /**
     * Text for btn OK
     *
     * @param text
     * @return
     */
    IDialogBuilder btnOkText(String text);

    /**
     * Text for btn Cancel
     *
     * @param text
     * @return
     */
    IDialogBuilder btnCancelText(String text);

    /**
     * OK action
     *
     * @param runnable
     * @return
     */
    IDialogBuilder btnOKClicked(Runnable runnable);

    /**
     * Cancel action
     *
     * @param runnable
     * @return
     */
    IDialogBuilder btnCancelClicked(Runnable runnable);

    /**
     * @return
     */
    Dialog build();
}
