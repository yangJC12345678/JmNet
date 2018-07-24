[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Introduction
---
A socket communication tool


Source demo 
---

```java
 public class ServerEventListener implements PipeServerListener,PipeListener {
	private LogPrinter log = LogPrinter.getLogPrinter(ServerEventListener.class);
 
	public void onCreated(ServerCreatedEvent event) {
		log.info("Created server on port: " + event.getPort());
	}
	public void onError(ServerErrorEvent event) {
		log.info("Catched a server exception: " + event.detail);
	}
	public void onClosed(ServerClosedEvent event) {
		log.info("Closed server on port: " + event.getPort());
	}
 
	public void onConnect(PipeConnectEvent event) {
		log.info("onConnect request pipe: " + event.getRemoteHost());
	}
	public void onConnected(PipeConnectedEvent event) {
		log.info("onConnected request pipe: " + event.getRemoteHost());
	}
	public void onDisconnected(PipeDisconnectedEvent event) {
		log.info("Found disconnected pipe: "+event.getRemoteHost()+"]");
	}
 public void onDataReadOut(PipeDataReadEvent event) {
   byte[] data = event.getReadData();
   log.info("Read out data [" + new String(data)  + "] from pipe: "+ event.getSource());
	 	String replyMessage ="Received,thanks";
		 event.setReplyData(replyMessage.getBytes());
 }
 public void onError(PipeErrorEvent event) {
   Throwable cause = event.getCause();
   log.error("Catched a exception:", cause);
 }
}
```

```java
 public class ClientEventListener implements PipeListener {
  private LogPrinter log = LogPrinter.getLogPrinter(ClientEventListener.class);
  public void onConnect(PipeConnectEvent event){
  	log.info("onConnect");
  }
  public void onConnected(PipeConnectedEvent event){
  	log.info("onConnected");
  }
  public void onDisconnected(PipeDisconnectedEvent event){
   	log.info("onDisconnected");
  }
  public void onDataReadOut(PipeDataReadEvent event) {
    byte[] data = event.getReadData();
    log.info("Read out data [" + new String(data) + "] from pipe: " + event.getSource());
    event.setReplyData("Hello,Chris".getBytes());
  }
  public void onError(PipeErrorEvent event) {
    Throwable cause = event.getCause();
    log.error("Catched a exception:" + cause.getMessage() + "]");
  }
}
```

```java
public class TcpServer {
  public static void main(String args[]) throws Exception {
  	PipeFactory pipeFactory = PipeFactoryManager.getPipeFactory("tcp");
  	PipeServerListener serverListener = new ServerEventListener();
  	PipeServer server = pipeFactory.createServer(9988,serverListener,(PipeListener)serverListener);
   server.startup();
   Object lock=new Object();
    synchronized(lock){
     while(Thread.currentThread().isAlive())
     lock.wait();
    }
  }
}
```

```java
public class TcpClient {
  public static void main(String args[]) throws Exception {
  	 String message ="Hello,Chris";
   	PipeFactory pipeFactory = PipeFactoryManager.getPipeFactory("tcp");
   	Pipe pipe = pipeFactory.connect("localhost",9988,new ClientEventListener());
  	 pipe.write(message.getBytes());
   	System.out.println(new String(pipe.read()));
   	pipe.keepListening();
   	pipe.write(message.getBytes());
   	
   	Object lock=new Object();
     synchronized(lock){
       while(Thread.currentThread().isAlive())
       lock.wait();
     }
  }
}
```
