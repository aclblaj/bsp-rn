import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class UDPChat {
	static final int port = 50000;

	public static void main(String[] args) {
		System.out.println("Start chat ...");
		if (args.length == 0) {
			System.out.println("\nUsage:\n java UDPChat s              Run as server" +
					"\n java UDPChat c              Run as client on localhost" +
					"\n java UDPChat c <address>    Run as client and connect to a remote address\n");
			try {
				System.in.read();
			} catch (IOException e) {
			}
			System.exit(0);
		}
		if (args[0].charAt(0) == 's') {
			System.out.println("Listen...");
			try (DatagramSocket srvSock = new DatagramSocket(port)) {
				DatagramPacket prcv = new DatagramPacket(new byte[200], 0, 200);
				srvSock.receive(prcv);
				System.out.print("Received data: ");
				String received = new String(prcv.getData(), 0, prcv.getLength(), StandardCharsets.UTF_8);
				System.out.println("'" + received.trim() + "'");
				System.out.println("from: " + prcv.getAddress());

				String reply = "$" + received.trim() + "$";
				System.out.println("Send back to client: '" + reply + "'");
				byte[] buf = Arrays.copyOf(reply.getBytes(StandardCharsets.UTF_8), 200);

				InetAddress to = prcv.getAddress();
				DatagramPacket psend = new DatagramPacket(buf, 0, 200, to, port + 1);
				srvSock.send(psend);
				System.out.println("Sent successfully.");
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
			System.exit(0);
		}
		if (args[0].charAt(0) == 'c') {
			String adr = (args.length > 1) ? args[1] : "localhost";
			System.out.println("Try to connect to '" + adr + "'");
			try (DatagramSocket clnSock = new DatagramSocket(port + 1)) {
				System.out.print("Data to be sent: ");
				BufferedReader bIn = new BufferedReader(new InputStreamReader(System.in));
				String toSend = bIn.readLine();
				byte[] buf = Arrays.copyOf(toSend.getBytes(StandardCharsets.UTF_8), 200);
				System.out.println("Sending...");
				DatagramPacket psend = new DatagramPacket(buf, 0, 200, InetAddress.getByName(adr), port);
				clnSock.send(psend);
				DatagramPacket prcv = new DatagramPacket(new byte[200], 0, 200);
				clnSock.receive(prcv);
				System.out.print("Received: ");
				String received = new String(prcv.getData(), 0, prcv.getLength(), StandardCharsets.UTF_8);
				System.out.println("'" + received.trim() + "'");
				System.out.println("from: " + prcv.getAddress());
				System.out.println("Received successfully.");
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
			System.exit(0);
		}
	}
}