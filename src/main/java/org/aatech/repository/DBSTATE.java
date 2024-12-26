package org.aatech.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBSTATE {
	
	DBConfig config = DBConfig.getInstance();
	
	protected Connection conn = config.getConn();
	protected PreparedStatement stmt = config.getStatement();
	protected ResultSet rs = config.getResultSet();
	protected CallableStatement cstmt = config.getCallStatement();
	DBSTATE(){
		System.out.println(config.getConn());
	}

}
