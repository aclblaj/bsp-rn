import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPChat {
	static final int port = 50000;

	public static void main(String[] args) {
		System.out.println("Start chat ...");
		if (args.length == 0) {
			System.out.println("\nUsage:\n java ChatMinimal s Run as server" +
					"\n java ChatMinimal c Run as client on localhost" +
					"\n java ChatMinimal c <address> Run as client and connects to a remote address\n");
			try {
				System.in.read();
			} catch (IOException e) {
			}
			System.exit(0);
		}
		if (args[0].charAt(0) == 's') {
			System.out.println("Listen...");
			try {
				DatagramSocket srvSock = new DatagramSocket(port);
				DatagramPacket prcv = new DatagramPacket(new byte[200], 0, 200);
				srvSock.receive(prcv);
				System.out.print("Received data: ");
				String cePrimesc = new String(prcv.getData());
				System.out.println("'" + cePrimesc.trim() + "'");
				System.out.println("from:" + prcv.getAddress());

				cePrimesc = "$" + cePrimesc.trim() + "$";
				System.out.println("Send back to server: '" + cePrimesc + "'");
				byte[] buf = new byte[200];
				cePrimesc.getBytes(0, cePrimesc.length(), buf, 0);

				InetAddress to = prcv.getAddress();
				prcv.setAddress(InetAddress.getLocalHost());
				DatagramPacket psend = new DatagramPacket(buf, 0, 200, to, port + 1);
				srvSock.send(psend);
				srvSock.disconnect();
				System.out.println("Send successfully.");
			} catch (Exception e) {
				System.out.println("Error:" + e);
			}
			System.exit(0);
		}
		if (args[0].charAt(0) == 'c') {
			String adr = null;
			if (args.length > 1)
				adr = args[1];
			else
				adr = "localhost";
			System.out.println("Try to connect to '" + adr + "'");
			try {
				DatagramSocket clnSock = new DatagramSocket(port + 1);
				System.out.print("Data to be send:");
				String ceTrimit = null;
				BufferedReader bIn = new BufferedReader(new InputStreamReader(System.in));
				ceTrimit = bIn.readLine();
				byte[] buf = new byte[200];
				ceTrimit.getBytes(0, ceTrimit.length(), buf, 0);
				System.out.println("Sending...");
				DatagramPacket psend = new DatagramPacket(buf, 0, 200, InetAddress.getByName(adr), port);
				clnSock.send(psend);
				DatagramPacket prcv = new DatagramPacket(new byte[200], 0, 200);
				clnSock.receive(prcv);
				System.out.print("Receiving:");
				String ceAmPrimit = new String(prcv.getData());
				System.out.println("'" + ceAmPrimit.trim() + "'");
				System.out.println("from: " + prcv.getAddress());
				clnSock.disconnect();
				System.out.println("Send successfully.");
			} catch (Exception e) {
				System.out.println("Error:" + e);
			}
			System.exit(0);
		}
	}
}