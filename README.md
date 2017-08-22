# unity
2017年7月27日</br>
step:开始使用</br>
问题如下：</br>
1）监听成功，client connect refused  
(Done)</br>
原因：</br>
server监听的端口未被打开</br>

2) client发送的数据未能到达server</br>
推测原因:</br>
a.tcp未能建立</br>
b.tcp建立了但是消息未能发送</br>
真正原因:</br>
端口未被打开－－－因为监听没有成功</br>
我们少了一条语句－－－server阻塞等待链路结束  client wait until the connection is closed</br>
bossChannel.closeFuture().sync();</br>
阻塞到tcp链路close，不然方法执行完，有可能信息还没有发</br>

2018年7月28日</br>
step:client一个线程成功与server一个线程通信</br>
next step:使用protobuf代替byteBuf</br>
参考信息：protobuf只是定义前后端的数据格式，网络传输层还是使用的byte[]数组的形式</br>

2018年7月31日</br>
明确进度点：</br>
1）封装protobuf为通用的Message</br>
2）信息分发的handler（群发的机制or get/set的注解发送的机制）</br>


