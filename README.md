# unity
2017年7月27日
step:开始使用
问题如下：
1）监听成功，client connect refused  
(Done)
原因：
server监听的端口未被打开

2) client发送的数据未能到达server
推测原因:
a.tcp未能建立
b.tcp建立了但是消息未能发送
真正原因:
端口未被打开－－－因为监听没有成功
我们少了一条语句－－－server阻塞等待链路结束  client wait until the connection is closed
bossChannel.closeFuture().sync();
阻塞到tcp链路close，不然方法执行完，有可能信息还没有发

2018年7月28日
step:client一个线程成功与server一个线程通信
next step:使用protobuf代替byteBuf
参考信息：protobuf只是定义前后端的数据格式，网络传输层还是使用的byte[]数组的形式

2018年7月31日
明确进度点：
1）封装protobuf为通用的Message
2）信息分发的handler（群发的机制or get/set的注解发送的机制）


