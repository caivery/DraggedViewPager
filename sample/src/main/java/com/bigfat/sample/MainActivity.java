package com.bigfat.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bigfat.draggedviewpager.utils.MDA_DraggedViewPagerController;
import com.bigfat.draggedviewpager.utils.MDA_DraggedViewPagerListener;
import com.bigfat.draggedviewpager.utils.OnPageSelectedListener;
import com.bigfat.draggedviewpager.view.MDA_DraggedViewPager;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private MDA_DraggedViewPager draggedViewPager;
    private MDA_DraggedViewPagerController<Section, Item> controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        draggedViewPager = (MDA_DraggedViewPager) findViewById(R.id.draggedViewPager);
        controller = new SimpleController(generatePageList(), R.layout.item_section, R.layout.item);
        draggedViewPager.setController(controller);
        draggedViewPager.setItemMoveDelay(200);
        draggedViewPager.setPageSwapDelay(400);
        //        draggedViewPager.setIsDragEnabled(false);//default is true
        draggedViewPager.setOnPageSelectedListener(new OnPageSelectedListener() {
            @Override
            public void onPageSelected(int pageIndex) {
                Log.i(TAG, "onPageSelected [pageIndex:" + pageIndex + "]");
            }
        });
        draggedViewPager.setDraggedViewPagerListener(new MDA_DraggedViewPagerListener() {
            @Override
            public void onDragStarted() {
                Log.i(TAG, "onDragStarted");
            }

            @Override
            public void onDragEnded() {
                Log.i(TAG, "onDragEnded");
            }

            @Override
            public void onPageSwapped(int firstPageIndex, int secondPageIndex) {
                Log.i(TAG, "onPageSwapped [firstPageIndex:" + firstPageIndex + ", secondPageIndex:" + secondPageIndex + "]");
            }

            @Override
            public void onItemMoved(int oldPageIndex, int oldItemIndex, int newPageIndex, int newItemIndex) {
                Log.i(TAG, "onPageSwapped [oldPageIndex:"
                    + oldPageIndex
                    + ", oldItemIndex:"
                    + oldItemIndex
                    + ", newPageIndex:"
                    + newPageIndex
                    + ", newItemIndex:"
                    + newItemIndex
                    + "]");
            }
        });
        //        draggedViewPager.setPageDragSetting(new PageDragSetting() {
        //            @Override
        //            public boolean canBeDragged(int pageIndex) {
        //                return false;
        //            }
        //
        //            @Override
        //            public boolean canBeSwiped(int pageIndex) {
        //                return false;
        //            }
        //        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_page:
                controller.addPage(0, new Section("add", generateItemList("add-", 5)));
                break;

            case R.id.action_remove_page:
                controller.removePage(0);
                break;

            case R.id.action_swap_page:
                controller.swapPages(0, 1);
                break;

            case R.id.action_add_item:
                controller.addItem(0, 0, new Item(R.drawable.abc_btn_radio_to_on_mtrl_000, "add"));
                break;

            case R.id.action_remove_item:
                controller.removeItem(0, 0);
                break;

            case R.id.action_update_item:
                controller.updateItem(0, 0, new Item(R.drawable.abc_btn_radio_to_on_mtrl_000, "update"));
                break;

            case R.id.action_move_item:
                controller.moveItem(0, 0, 1, 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Section> generatePageList() {
        List<Section> data = new ArrayList<>();

        data.add(new Section("页面1", generateItemList("1-", 20)));
        data.add(new Section("页面2", generateItemList("2-", 10)));
        data.add(new Section("页面3", generateItemList("3-", 35)));
        data.add(new Section("页面4", generateItemList("4-", 50)));

        return data;
    }

    private List<Item> generateItemList(String title, int size) {
        ArrayList<Item> itemList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Item item = new Item(R.mipmap.ic_launcher, title + i);
            itemList.add(item);
        }

        return itemList;
    }

    private class SimpleController extends MDA_DraggedViewPagerController<Section, Item> {
        public SimpleController(List<Section> data, int pageLayoutRes, int itemLayoutRes) {
            super(data, pageLayoutRes, itemLayoutRes);
        }

        @Override
        public void bindPageData(View pageView, final int pageIndex) {
            Section section = controller.getData().get(pageIndex);
            TextView tv = (TextView) pageView.findViewById(R.id.tv_item_section_title);
            tv.setText(section.getTitle());

            pageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "page:" + pageIndex, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void bindItemData(View itemView, final int pageIndex, final int itemIndex) {
            final Item item = controller.getData().get(pageIndex).getData().get(itemIndex);
            ImageView img = (ImageView) itemView.findViewById(R.id.img_item);
            TextView tv = (TextView) itemView.findViewById(R.id.tv_item);
            img.setImageResource(item.getImgRes());
            tv.setText(item.getText());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "page:" + pageIndex + " item:" + itemIndex, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}