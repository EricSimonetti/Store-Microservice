package dev.microprofile.starter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *  Utitly class for managing connection to the mysql database
 *
 */
class MySQLUtil{
    private static InitialContext _ctx = null;
    private static DataSource _db = null;

    /**
     *  initalises or aquires the InitialContext for the service
     *
     *  @return InitialContext object
     */
    private static InitialContext getContext() throws Exception{
        if(_ctx != null){
            return _ctx;
        } else {
            return _ctx = new InitialContext();
        }
    }

    /**
     *  initalises or aquires the DataSource for the service
     *
     *  @return DataSource object
     */
    private static DataSource getDB() throws Exception {
        if(_db != null){
            return _db;
        } else {
            return _db = getContext().doLookup("jdbc/myDB");
        }
    }

    /**
     *  Gets a database connection
     *
     *  @return Connection object
     */
    static Connection getConnection() throws Exception {
        DataSource db = getDB();
        return db.getConnection();
    }

    /**
     *  Gets a database connection
     *
     *  @param con, open connection to close or null
     *  @param query, open query to close or null
     *  @param res, open result set to close or null
     *
     *
     *
     */
    static void cleanup(Connection con, Statement query, ResultSet res){
        if (res != null) {
            try {
                res.close();
            } catch (SQLException sqlEx) { } // ignore

            res = null;
        }
        if (query != null) {
            try {
                query.close();
            } catch (SQLException sqlEx) { } // ignore

            query = null;
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException sqlEx) { } // ignore

            con = null;
        }
    }
}
