package mx.com.hunkabann.skf.policy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Token {
	
	public final static int TOKEN_LENGTH = 6;

	final private MessageDigest md;

	final private String id;

	private long nextNewToken = 0;

	private long currentTokenTime = 0;

	private String currentToken = null;

	/**
	 * 
	 */
	public Md5Token(String id) {
		super();
		this.id = id;
		try {
//			this.md = MessageDigest.getInstance("MD5");
			this.md = MessageDigest.getInstance("SHA-1");
			
		} catch (NoSuchAlgorithmException e) {
			
			throw new RuntimeException(e);
			
		}
		
	}
	
	
	

	private long getCurrentHttpTokenTime() {
		
		setCurrentToken(null);
		
		try {
			
			final URL url = new URL("http://unixtime.forsthaus.de/time.php");
			final URLConnection conn = url.openConnection();
			final InputStream istream = conn.getInputStream();
			
			try {
				
				final StringBuilder sb = new StringBuilder();

				int ch = -1;
				
				while ((ch = istream.read()) != -1) 
					sb.append((char) ch);
				
				long l1 = Long.parseLong(sb.toString());
				long ctt = l1 / 60;
				
				setCurrentTokenTime(ctt);
				setNextNewToken(System.currentTimeMillis() + ((ctt + 1) * 60 - l1) * 1000);

				return getCurrentTokenTime();
				
			} catch (NumberFormatException e) {
			} finally {
				
				istream.close();
				
			}
			
		} catch (IOException e) {
		}
		
		return System.currentTimeMillis() / 60000;
		
	}	// getCurrentHttpTokenTime
	
	
	

	private String getCurrentToken() {
		
		
		return this.currentToken;
	}

	private long getCurrentTokenTime() {
		
		if (System.currentTimeMillis() > getNextNewToken()) {
			getCurrentHttpTokenTime();
		}

		return this.currentTokenTime;
	}

	private String getId() {
		return this.id;
	}

	private MessageDigest getMd() {
		return this.md;
	}

	private long getNextNewToken() {
		return this.nextNewToken;
	}

	public String getToken() {
		
		long tokenTime = getCurrentTokenTime();
		
		if (getCurrentToken() == null) {
			return getToken(tokenTime);
		}
		
		return getCurrentToken();
	}

	private String getToken(long tokenTime) {
		getMd().reset();

		byte[] bs = getMd().digest((getId() + String.valueOf(tokenTime) + getId()).getBytes());

		StringBuilder key = new StringBuilder();
		
		boolean t = tokenTime % 2 == 0;
		
		for (byte b : bs) {
			
			if (t = !t) 
				continue;

			
			key.append(Math.abs(b % 10));
			
			if (key.length() >= TOKEN_LENGTH) 
				break;
			
		}
		
//		System.out.println("token: " + key.substring(0, TOKEN_LENGTH));
		
		return key.substring(0, TOKEN_LENGTH);
		
	}	// getToken
	
	
	

	public boolean isEqualsToken(String token) {
		
		long tokenTime = getCurrentTokenTime();

		if (isEqualsTokenImpl(token, tokenTime)) 
			return true;
		
		
		--tokenTime;
		
//		System.out.println(token);
//		System.out.println(tokenTime);
//		System.out.println(isEqualsTokenImpl(token, tokenTime));
		
		return isEqualsTokenImpl(token, tokenTime);
		
	}	// isEqualsToken
	
	
	

	private boolean isEqualsTokenImpl(String token, long tokenTime) {
		return getToken(tokenTime).equals(token);
	}

	private void setCurrentToken(String currentToken) {
		this.currentToken = currentToken;
	}

	private void setCurrentTokenTime(long currentTokenTime) {
		this.currentTokenTime = currentTokenTime;
	}

	private void setNextNewToken(long nexNewToken) {
		this.nextNewToken = nexNewToken;
	}
	

}	// end of class
