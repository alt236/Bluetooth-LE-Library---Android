package uk.co.alt236.btlescan.permission

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class PermissionDeniedDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage(requireArguments().getCharSequence(EXTRA_MESSAGE))
            .setPositiveButton(getString(android.R.string.ok)) { _, _ -> }
            .create()


    companion object {
        private val EXTRA_MESSAGE =
            PermissionDeniedDialogFragment::class.java.name + ".EXTRA_MESSAGE"

        @JvmStatic
        fun create(message: CharSequence): DialogFragment {
            val fragment = PermissionDeniedDialogFragment()
            val args = Bundle()
            args.putCharSequence(EXTRA_MESSAGE, message)
            fragment.arguments = args
            return fragment
        }
    }
}