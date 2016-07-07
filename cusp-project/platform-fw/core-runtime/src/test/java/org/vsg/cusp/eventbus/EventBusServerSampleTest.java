package org.vsg.cusp.eventbus;

import org.vsg.cusp.event.flow.impl.ZmqEventBusImplEndPoint;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class EventBusServerSampleTest {

	
	private ZmqEventBusImplEndPoint ebi;
	
	public EventBusServerSampleTest() {
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
        final ZMQ.Context context = ZMQ.context(1);  
        ZMQ.Socket router = context.socket(ZMQ.ROUTER);  
        ZMQ.Socket dealer = context.socket(ZMQ.DEALER);  
          
        router.bind("ipc://fjs1");  
        dealer.bind("ipc://fjs2");  
          
        for (int i = 0; i < 20; i++) {  
            new Thread(new Runnable(){  
  
                public void run() {  
                    ZMQ.Socket response = context.socket(ZMQ.REP);  
                    response.connect("ipc://fjs2");  
                    while (!Thread.currentThread().isInterrupted()) {  
                        response.recv();  
                        response.send("hello".getBytes());  
                        try {  
                            Thread.currentThread().sleep(1000);  
                            System.out.println("moii");
                        } catch (InterruptedException e) {  
                            // TODO Auto-generated catch block  
                            e.printStackTrace();  
                        }  
                    }
                    

                    response.close();  
                }  
                  
            }).start();  
        }  
        ZMQ.proxy(router, dealer, null);  
        router.close();  
        dealer.close();  
        context.term();  

		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventBusServerSampleTest ebt = new EventBusServerSampleTest();
		
		ebt.start();

	}

}
