package no.hvl.dat110.aciotdevice.client;

import com.google.gson.Gson;
import com.sun.xml.internal.ws.api.pipe.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RestClient {

	Gson gson;

	public RestClient() {
		// TODO Auto-generated constructor stub
		gson = new Gson();
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		AccessMessage accessMessage = new AccessMessage(message);

		try(Socket s = new Socket(Configuration.host, Configuration.port)){
			String jsonbody = gson.toJson(accessMessage);

			String httpputrequest =
					"PUT " + logpath + " HTTP/1.1\r\n" +
							"Host: " + Configuration.host + "\r\n" +
							"Content-Type application/json\r\n" +
							"Content-length: " + jsonbody.length() + "\r\n" +
							"Connection: close\r\n" +
							"\r\n" +
							jsonbody +
							"\r\n";

			OutputStream output = s.getOutputStream();

			PrintWriter pw = new PrintWriter(output, false);

			pw.print(httpputrequest);
			pw.flush();

			InputStream in = s.getInputStream();

			Scanner scan = new Scanner(in);
			StringBuilder jsonResponse = new StringBuilder();
			boolean header = true;

			while(scan.hasNext()){
				String nextLine = scan.nextLine();

				if (header){
					System.out.println(nextLine);
				}else {
					jsonResponse.append(nextLine);
				}

				if (nextLine.isEmpty()){
					header = false;
				}
			}

			scan.close();

		} catch (IOException ex){
			System.err.println(ex);
		}


	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		
		// TODO: implement a HTTP GET on the service to get current access code
		
		return code;
	}
}
