package com.example.sagar.urcatters.Cart;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sagar.urcatters.Adapter.CartListAdapter;
import com.example.sagar.urcatters.Adapter.CartListAdapter.MyViewHolder;
import com.example.sagar.urcatters.Adapter.RecyclerItemTouchHelper;
import com.example.sagar.urcatters.Adapter.RecyclerItemTouchHelper.RecyclerItemTouchHelperListener;
import com.example.sagar.urcatters.C0290R;
import com.example.sagar.urcatters.Database.Breakfast_Database;
import com.example.sagar.urcatters.Model.cart_item;
import java.util.ArrayList;
import java.util.List;

public class Chats_Cart extends AppCompatActivity implements RecyclerItemTouchHelperListener {
    private CartListAdapter adapter;
    Breakfast_Database bf_database;
    int getCount;
    private List<cart_item> listItem;
    Context mContext = this;
    private RecyclerView recyclerView;
    private Cursor res;
    Button single_confirm_button;
    TextView single_textView;
    final String titleName = "Chats";

    class C02761 implements OnClickListener {

        class C02741 implements DialogInterface.OnClickListener {
            C02741() {
            }

            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(Chats_Cart.this.getApplicationContext(), "Cancelled", 0).show();
                Toast.makeText(Chats_Cart.this.getApplicationContext(), "Count : " + Chats_Cart.this.getCount + " From Func " + Chats_Cart.this.bf_database.getCount("Chats"), 0).show();
                dialog.cancel();
            }
        }

        class C02752 implements DialogInterface.OnClickListener {
            C02752() {
            }

            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(Chats_Cart.this.getApplicationContext(), "Confirmed", 0).show();
            }
        }

        C02761() {
        }

        public void onClick(View v) {
            Builder alertDialogBuilder = new Builder(Chats_Cart.this.mContext);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("Chats Order Confirmation");
            alertDialogBuilder.setMessage("Please Confirm Your Chats Order and Move to One Step Process");
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new C02752()).setNegativeButton("Cancel", new C02741());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.getButton(-2).setBackgroundColor(-1);
            alertDialog.getButton(-1).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            alertDialog.getButton(-2).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            alertDialog.getButton(-1).setBackgroundColor(-1);
        }
    }

    public class GridSpacingItemDecoration extends ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % this.spanCount;
            if (this.includeEdge) {
                outRect.left = this.spacing - ((this.spacing * column) / this.spanCount);
                outRect.right = ((column + 1) * this.spacing) / this.spanCount;
                if (position < this.spanCount) {
                    outRect.top = this.spacing;
                }
                outRect.bottom = this.spacing;
                return;
            }
            outRect.left = (this.spacing * column) / this.spanCount;
            outRect.right = this.spacing - (((column + 1) * this.spacing) / this.spanCount);
            if (position >= this.spanCount) {
                outRect.top = this.spacing;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0290R.layout.single_cart_recylerview);
        this.single_confirm_button = (Button) findViewById(C0290R.id.single_confirm_button);
        this.single_textView = (TextView) findViewById(C0290R.id.single_textView);
        this.single_textView.setText("Your Chats Cart");
        this.bf_database = new Breakfast_Database(getApplicationContext());
        this.listItem = new ArrayList();
        this.res = this.bf_database.read();
        while (this.res.moveToNext()) {
            Cursor cursor = this.res;
            Cursor cursor2 = this.res;
            Breakfast_Database breakfast_Database = this.bf_database;
            String name = cursor.getString(cursor2.getColumnIndex("name"));
            cursor = this.res;
            cursor2 = this.res;
            breakfast_Database = this.bf_database;
            String shift = cursor.getString(cursor2.getColumnIndex("shiftName"));
            if (shift.contains("Chats")) {
                this.getCount++;
                this.listItem.add(new cart_item(name, shift));
            }
        }
        if (this.getCount == 0) {
            View nothing_in_cart = getLayoutInflater().inflate(C0290R.layout.nothing_in_cart, (ViewGroup) findViewById(C0290R.id.nothing_in_cart));
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(0);
            toast.setGravity(17, 0, 0);
            toast.setView(nothing_in_cart);
            toast.show();
        }
        this.adapter = new CartListAdapter(getApplication(), this.listItem);
        this.recyclerView = (RecyclerView) findViewById(C0290R.id.cartRecyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.adapter);
        new ItemTouchHelper(new RecyclerItemTouchHelper(0, 4, this)).attachToRecyclerView(this.recyclerView);
        this.single_confirm_button.setOnClickListener(new C02761());
    }

    public void onSwiped(ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyViewHolder) {
            final String name = ((cart_item) this.listItem.get(viewHolder.getAdapterPosition())).getCartOrderName();
            final String shift_name = ((cart_item) this.listItem.get(viewHolder.getAdapterPosition())).getCartShiftName();
            final cart_item deletedItem = (cart_item) this.listItem.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            this.adapter.removeItem(viewHolder.getAdapterPosition());
            if (Long.valueOf(this.bf_database.delete(name)).longValue() == -1) {
                Toast.makeText(this, "Data not Deleted", 0).show();
            } else {
                Toast.makeText(this, "Data Deleted", 0).show();
            }
            Snackbar snackbar = Snackbar.make(findViewById(C0290R.id.cart_rec_view), name + " removed from cart!", 0);
            snackbar.setAction((CharSequence) "UNDO", new OnClickListener() {
                public void onClick(View view) {
                    if (Long.valueOf(Chats_Cart.this.bf_database.create(name, shift_name)).longValue() == -1) {
                        Toast.makeText(Chats_Cart.this.getApplicationContext(), name + " : Not Added to Cart", 0).show();
                    } else {
                        Toast.makeText(Chats_Cart.this.getApplicationContext(), name + " : Added Back to Cart", 0).show();
                    }
                    Chats_Cart.this.adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor((int) InputDeviceCompat.SOURCE_ANY);
            snackbar.show();
        }
    }

    private int dpToPx(int dp) {
        return Math.round(TypedValue.applyDimension(1, (float) dp, getResources().getDisplayMetrics()));
    }
}
