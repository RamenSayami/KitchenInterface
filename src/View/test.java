package View;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.primer.SlavePrimer;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;


public class test {
	public String serverIP = null;

	public static void main(String[] args) throws Exception
	{
		int timeout=500;
        int port = 13000;
        String serverIP = null;
        
        ActorSystem system = ActorSystem.create();
		
		ActorRef master = system.actorOf(Props.create(MasterFinder.class));
		master.tell(n, null);
		

	    Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
	    for (; n.hasMoreElements();)
	    {
	        NetworkInterface e = n.nextElement();
	        Enumeration<InetAddress> a = e.getInetAddresses();
	        for (; a.hasMoreElements();)
	        {
	            InetAddress addr = a.nextElement();
	            String currentIp = addr.getHostAddress();
	            Pattern p = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	            		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	            		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	            		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	            Matcher m = p.matcher(currentIp);  
	            boolean validIp = m.matches();  
	            String self = InetAddress.getLocalHost().getHostAddress().toString();
	            System.out.println("=>"+self);
	            if(validIp&&!getSubnet(currentIp).equals(getSubnet(self))){
	            	System.out.println("  " + addr.getHostAddress());
	            	 try {
	                     System.out.println("Current Ip"+currentIp);
	                     String subnet = getSubnet(currentIp);
	                     System.out.println("subnet: " + subnet);

	                     for (int i=135;i<140;i++){

	                         String host = subnet + i;
	                         System.out.println("Checking :" + host);

	                         if (InetAddress.getByName(host).isReachable(timeout)){
	                             try {
	                                 Socket connected = new Socket(host, port);
	                                 System.out.println(host + " is reachable");

	                             }
	                             catch (Exception s) {
	         	                   s.printStackTrace();
	                             }
	                         }
	                     }
	                 }
	                 catch(Exception c){
	                     System.out.println(c);
	                 }
	            	
	            }
	        }
	    }
	} 


	public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator+1, lastSeparator+1);
    }

	public class MasterFinder extends UntypedActor{
		private ActorSystem sys = ActorSystem.create();

		@Override
		public void onReceive(Object object) throws Throwable {
			if(object instanceof String){
				ActorRef child = sys.actorOf(Props.create(SlaveFinder.class));
				child.tell((String) object, getSelf());
			}
		}
		
	}
	
	public class SlaveFinder extends UntypedActor{

		@Override
		public void onReceive(Object obj) throws Throwable {
			if(obj instanceof String){
				
			}
			
		}
		
	}
	
}
