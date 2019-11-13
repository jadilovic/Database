package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton - klasa koju mozemo instancirati jednom
public class ds {

	// instanca same klase
	private static ds instance = null;

	// vrijednosti koje smo izabrali prilikom instalacije MySQL Servera
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	// localhost//imeBazeNaKojuSeSpajamo
	private static final String CONN_STRING = "jdbc:mysql://localhost/webshop";

	// connection object
	private Connection connection = null;

	// privatni konstruktor - klasa moze biti instancirana samo unutar sebe
	private ds() {

	}

	// provjeriti da li je instanca null, instancirati i vratiti ili samo vratiti
	public static ds getInstance() {
		if (instance == null) {
			instance = new ds();
		}
		return instance;
	}

	private boolean openConnection() {
		try {
			connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			return true;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		}
	}

	public Connection getConnection() {
		if (connection == null) {
			if (openConnection()) {
				System.out.println("Konekcija otvorena.");
				return connection;
			} else {
				return null;
			}
		}
		return connection;
	}

	public void close() {
		System.out.println("Konekcija zatvorena.");
		try {
			connection.close();
			connection = null;
		} catch (Exception e) {
		}
	}
}