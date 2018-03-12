package com.ljf.wechatmoment.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.ErrorMessage;
import com.ljf.wechatmoment.data.Repository;
import com.ljf.wechatmoment.recyclerview.HeaderFooterAdapter;
import com.ljf.wechatmoment.recyclerview.HeaderProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mRepository = Repository.getInstance(this);
        initRecyclerView();
        loadUserInfo();
    }

    private void loadUserInfo() {
        mRepository.loadUserInfo();
    }

    private void initRecyclerView() {
        HeaderFooterAdapter adapter = new HeaderFooterAdapter();
        adapter.registerHeader(new HeaderProvider(this));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Subscribe
    public void onError(ErrorMessage message) {
        Toast.makeText(this, message.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
