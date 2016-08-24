package uk.co.alt236.btlescan.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;

/*package*/ final class DialogFactory {

    private DialogFactory() {
        // NOOP
    }

    public static Dialog createAboutDialog(final Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_textview, null);
        final TextView textView = (TextView) view.findViewById(R.id.text);

        final SpannableString text = new SpannableString(context.getString(R.string.about_dialog_text));

        textView.setText(text);
        textView.setAutoLinkMask(Activity.RESULT_OK);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        Linkify.addLinks(text, Linkify.ALL);

        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
            }
        };

        return new AlertDialog.Builder(context)
                .setTitle(R.string.menu_about)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, listener)
                .setView(view)
                .create();
    }
}
