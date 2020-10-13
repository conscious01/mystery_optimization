package com.zgzx.metaphysics.rongmessage;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgzx.metaphysics.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.PrivateConversationProvider;

@ProviderTag(messageContent = SystemMessage.class)
public class SystemMessageItemProvider extends PrivateConversationProvider.MessageProvider<SystemMessage> {

    class ViewHolder {
        TextView message;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gv, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(android.R.id.text1);
        view.setTag(holder);
        return view;
    }



    @Override
    public void bindView(View view, int i, SystemMessage orderMessage, UIMessage uiMessage) {
        System.out.println("bindView");
    }

    @Override
    public Spannable getContentSummary(SystemMessage data) {
        return new SpannableString("这是一条自定义消息OrderMessage");
    }

    @Override
    public void onItemClick(View view, int i, SystemMessage orderMessage, UIMessage uiMessage) {

    }


}