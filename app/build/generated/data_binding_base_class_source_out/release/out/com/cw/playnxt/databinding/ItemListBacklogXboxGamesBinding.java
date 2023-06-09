// Generated by view binder compiler. Do not edit!
package com.cw.playnxt.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cw.playnxt.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemListBacklogXboxGamesBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout llEdit;

  @NonNull
  public final LinearLayout llXboxGames;

  @NonNull
  public final TextView tvListName;

  @NonNull
  public final TextView tvTotalGame;

  private ItemListBacklogXboxGamesBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout llEdit, @NonNull LinearLayout llXboxGames, @NonNull TextView tvListName,
      @NonNull TextView tvTotalGame) {
    this.rootView = rootView;
    this.llEdit = llEdit;
    this.llXboxGames = llXboxGames;
    this.tvListName = tvListName;
    this.tvTotalGame = tvTotalGame;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemListBacklogXboxGamesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemListBacklogXboxGamesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_list_backlog_xbox_games, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemListBacklogXboxGamesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.llEdit;
      LinearLayout llEdit = ViewBindings.findChildViewById(rootView, id);
      if (llEdit == null) {
        break missingId;
      }

      id = R.id.llXboxGames;
      LinearLayout llXboxGames = ViewBindings.findChildViewById(rootView, id);
      if (llXboxGames == null) {
        break missingId;
      }

      id = R.id.tvListName;
      TextView tvListName = ViewBindings.findChildViewById(rootView, id);
      if (tvListName == null) {
        break missingId;
      }

      id = R.id.tvTotalGame;
      TextView tvTotalGame = ViewBindings.findChildViewById(rootView, id);
      if (tvTotalGame == null) {
        break missingId;
      }

      return new ItemListBacklogXboxGamesBinding((RelativeLayout) rootView, llEdit, llXboxGames,
          tvListName, tvTotalGame);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
