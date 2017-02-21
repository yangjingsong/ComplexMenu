# ComplexMenu
###类似美团的下拉菜
####单使用方式：
#####布局文件：
```java  
  
<com.yjs.complexmenu.ComplexMenuView
        android:id="@+id/complexMenuView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <TextView
            android:id="@+id/textView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="我是菜单要改变的内容"

            />
    </com.yjs.complexmenu.ComplexMenuView>
  
```
#####可配置的属性：
######//设置顶部每个菜单之间分割线的高度
######app:divide_height_vertical="20dp"
######//设置顶部每个菜单之间分割线的宽度
######app:divide_width_vertical="1dp"
######//设置顶部每个菜单之间分割线的颜色
######app:divide_color_vertical="@android:color/darker_gray"
######//设置顶部菜单栏与下面包含的内容之间分割线的高度
######app:divide_height="1dp"
######//设置顶部菜单栏与下面包含的内容之间分割线的颜色
######app:divide_color="@android:color/background_dark"

##### Java代码
##### 首先创建顶部每个菜单的类型item,需要实现IComplexItemView接口，实现void setChecked(boolean isChecked);方法来设置切换不同菜单时顶部主菜单的背景及颜色变换
##### 举个栗子：
```java  
public class ComplexTitleView extends TextView implements IComplexItemView{
    public ComplexTitleView(Context context) {
        this(context, null);
    }

    public ComplexTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComplexTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChecked(false);
    }

    @Override
    public void setChecked(boolean isChecked) {
        //选中则把文字改成黑色
        if (isChecked){
            setTextColor(Color.BLACK);
        }else {//默认为蓝色
            setTextColor(Color.BLUE);
        }

    }
}

```
#####然后创建每个主菜单对应的子菜的选择项view
```Java
ublic class ComplexSortMenu extends LinearLayout {
    public ComplexSortMenu(Context context) {
        this(context, null);
    }

    public ComplexSortMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComplexSortMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private OnSelectedListener onSelectedListener;

    private void init() {
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.sort_view, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        List<String> strings = new ArrayList<>();
        strings.add("北京市");
        strings.add("东城区");
        strings.add("西城区");
        strings.add("海淀区");
        strings.add("朝阳区");
        SortAdapter sortAdapter = new SortAdapter(strings);
        recyclerView.setAdapter(sortAdapter);
        addView(recyclerView);


    }

    private class SortAdapter extends RecyclerView.Adapter {

        List<String> data;

        public SortAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).textView.setText(data.get(position));
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onSelectedListener!=null){
                            onSelectedListener.onSelected(data.get(position));
                        }

                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class ItemViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textView);
            }
        }
    }

    public interface OnSelectedListener {
        void onSelected(String name);
    }

    public OnSelectedListener getOnSelectedListener() {
        return onSelectedListener;
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }
}
```

###最后是创建菜单，使用ComplexMenuView的addTitleView(IComplexItemView titleView, View sortView) 方法一次加入主菜单及其对应的子菜单，
最后调用build()方法完成构建

```Java
 ComplexTitleView complexTitleView = new ComplexTitleView(this);
        complexTitleView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        complexTitleView.setText("选项一");
        complexTitleView.setHeight(100);
        complexTitleView.setGravity(Gravity.CENTER);
        ComplexTitleView complexTitleView1 = new ComplexTitleView(this);
        complexTitleView1.setText("选项二");
        complexTitleView1.setGravity(Gravity.CENTER);
        complexTitleView1.setHeight(100);



        ComplexSortMenu complexSortMenu = new ComplexSortMenu(this);
        complexSortMenu.setOnSelectedListener(new ComplexSortMenu.OnSelectedListener() {
            @Override
            public void onSelected(String name) {
                textView.setText(name);
                complexMenuView.hideMenus();
            }
        });
        complexMenuView.addTitleView(complexTitleView,complexSortMenu)
                .addTitleView(complexTitleView1,sortView1)
                .build();
```
