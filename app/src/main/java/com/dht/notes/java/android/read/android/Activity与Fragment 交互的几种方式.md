
### 一、直接使用getActivity(), new Fragment（） 方式直接调用方法
        HomeFragment homeFragment=new HomeFragment();  
        homeFragment.changeText();  
        
### 二、Activity和Fragment共享一个Handler，然后发送消息通知彼此更新。

### 三、使用接口回调方式 
        需要在fragment中实现 onAttach方法
        
### 四、使用startActivityForResult 
        Fragment和activity，和两个Activity之间交互
        
### 五、使用BroadCastReceiver 
        可以使用在Fragment和Activity，两个Activity之间交互（虽然广播比较万能，
        但是它有系统决定，且有优先级，可能会造成延迟）
        
        IntentFilter filter = new IntentFilter(AnotherActivity.action);    
        registerReceiver(broadcastReceiver, filter);  //注册广播
        
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {    
    
            @Override    
            public void onReceive(Context context, Intent intent) {    
                // TODO Auto-generated method stub    
                textView.setText(intent.getExtras().getString("data"));    
            }    
        };    
        
        Intent intent = new Intent(action);    
        intent.putExtra("data", "yes i am data");    
        sendBroadcast(intent);    //发送广播