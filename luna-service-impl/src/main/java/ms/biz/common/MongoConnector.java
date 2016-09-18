package ms.biz.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * Copyright (C) 2015 - 2016 Microscene Inc., All Rights Reserved.
 *
 * @author: shawn@visualbusiness.com
 * @Date: Apr 7, 2016
 *
 */
public class MongoConnector {

	private static final String HOSTS = "mongo.hosts";
	private static final String DB_NAME = "mongo.dbname";
	private static final String CONNECTIONS_PER_HOST = "mongo.connectionsPerHost";
	private static final String THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER = "mongo.threadsAllowedToBlockForConnectionMultiplier";
	private static final String CONNECT_TIMEOUT = "mongo.connectTimeout";
	private static final String MAX_WAIT_TIME = "mongo.maxWaitTime";
//	private static final String AUTOCONNECTRETRY = "mongo.autoConnectRetry";
	private static final String SOCKET_KEEP_ALIVE = "mongo.socketKeepAlive";
	private static final String SOCKET_TIMEOUT = "mongo.socketTimeout";
//	private static final String SLAVEOK = "mongo.slaveOk";
	private static final String USER = "mongo.user";
	private static final String PASSWORD = "mongo.password";

	private MongoClient client;
	private MongoDatabase db;
	private MongoClientOptions mongoClientOptions;
	List<MongoCredential> credentials = new ArrayList<MongoCredential>();
	private List<ServerAddress> replica = new ArrayList<ServerAddress>();
	private String dbName;

	public MongoConnector(String filePath) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);
		Properties profile = new Properties();
		try {
			profile.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String hosts = profile.getProperty(HOSTS, "");
		dbName = profile.getProperty(DB_NAME, "");
		Integer connectionsPerHost = Integer.parseInt(profile.getProperty(CONNECTIONS_PER_HOST, "0"));
		Integer threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(profile.getProperty(THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER, "0"));
		Integer connectTimeout = Integer.parseInt(profile.getProperty(CONNECT_TIMEOUT, "0"));
		Integer maxWaitTime = Integer.parseInt(profile.getProperty(MAX_WAIT_TIME, "0"));
		Boolean socketKeepAlive = Boolean.parseBoolean(profile.getProperty(SOCKET_KEEP_ALIVE, "true"));
		Integer socketTimeout = Integer.parseInt(profile.getProperty(SOCKET_TIMEOUT, "0"));
		String user = profile.getProperty(USER);
		String password = profile.getProperty(PASSWORD);

		// TODO:
//		Boolean autoConnectRetry = Boolean.parseBoolean(profile.getProperty(AUTOCONNECTRETRY, "false"));
//		Boolean slaveOk = Boolean.parseBoolean(profile.getProperty(SLAVEOK, "false"));
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] servers = hosts.split(",");
		for(String server : servers)
		{
			String[] terms = server.split(":");
			ServerAddress address = new ServerAddress(terms[0],Integer.valueOf(terms[1]));
			replica.add(address);
		}

//		TODO:private static final String SLAVEOK = "mongo.slaveOk";
//		TODO:private static final String AUTOCONNECTRETRY = "mongo.autoConnectRetry"; 
		// autoRetry可以通过设置更长的timeout实现，已经被废弃
		//http://stackoverflow.com/questions/23295333/mongodb-java-driver-autoconnectretry
		mongoClientOptions = new MongoClientOptions.Builder()
		.connectionsPerHost(connectionsPerHost)
		.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier)
		.connectTimeout(connectTimeout)
		.maxWaitTime(maxWaitTime)
		.socketKeepAlive(socketKeepAlive)
		.socketTimeout(socketTimeout)
		.build();
		MongoCredential credential = MongoCredential.createCredential(user, dbName, password.toCharArray());
		credentials.add(credential);
		
		try {
			initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	public void initialize() throws IOException
	{
		client = new MongoClient(replica, credentials, mongoClientOptions);
		db = client.getDatabase(dbName);
	}

	public void dispose()
	{
		if(client != null)
		{
			client.close();
		}
	}
	
	public MongoCollection<Document> getDBCollection(String collName)
	{
		if(db != null)
		{
			return db.getCollection(collName);
		}
		return null;
	}
	
	public void createDBCollection(String collName)
	{
		if(db != null)
		{
			db.createCollection(collName);
		}
	}

	public MongoDatabase getMongoDB() {
		return db;
	}

}
