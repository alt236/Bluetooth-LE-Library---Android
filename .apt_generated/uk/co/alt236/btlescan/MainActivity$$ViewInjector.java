// Generated code from Butter Knife. Do not modify!
package uk.co.alt236.btlescan;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final uk.co.alt236.btlescan.MainActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131230722);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230722' for field 'mTvBluetoothStatus' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mTvBluetoothStatus = (android.widget.TextView) view;
    view = finder.findById(source, 2131230721);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131230721' for field 'mTvBluetoothLeStatus' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mTvBluetoothLeStatus = (android.widget.TextView) view;
  }

  public static void reset(uk.co.alt236.btlescan.MainActivity target) {
    target.mTvBluetoothStatus = null;
    target.mTvBluetoothLeStatus = null;
  }
}
