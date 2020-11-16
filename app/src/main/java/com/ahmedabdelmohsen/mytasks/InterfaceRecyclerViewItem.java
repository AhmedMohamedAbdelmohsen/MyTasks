package com.ahmedabdelmohsen.mytasks;

public interface InterfaceRecyclerViewItem {
    void onTaskClick(int id, boolean status);
    void onLongTaskClick(int id, String body, int requestCode);

}
