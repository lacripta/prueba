/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 *
 * @author USERPC
 */
public class conex implements Closeable {

    private Gson gson;
    private String url;
    private Connection db;
    private QueryRunner query;

    @Override
    public void close() throws IOException {
        try {
            if (!this.db.isClosed()) {
                rollback();
                cerrar();
            }
        } catch (SQLException ex) {
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
        }
        DbUtils.closeQuietly(this.db);
    }

    /**
     * ESTABLECE UNA CONEXION CON LA BASE DE DATOS SIN AUTO COMMIT USANDO LAS
     * CREDENCIALES POR DEFECTO DE LA LIBRERIA
     *
     */
    public conex() {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        try {
            this.url = "jdbc:mysql://localhost:3306/elibom";
            Class.forName("com.mysql.jdbc.Driver");
            this.db = DriverManager.getConnection(this.url, "root", "");
            this.db.setAutoCommit(false);
            this.query = new QueryRunner();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param sid
     * @param pwd
     *
     */
    public conex(String sid, String pwd) {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        try {
            this.url = "jdbc:mysql://localhost:3306/elibom";
            Class.forName("com.mysql.jdbc.Driver");
            this.db = DriverManager.getConnection(this.url, sid, pwd);
            this.db.setAutoCommit(false);
            this.query = new QueryRunner();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * HACE COMMIT DE LOS CAMBIOS REALIZADOS.
     *
     * @throws SQLException
     */
    public void commit() throws SQLException {
        getDB().commit();
    }

    /**
     * REVERSA LOS CAMBIOS REALIZADOS POR LA CONEXION.
     *
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        getDB().rollback();
    }

    /**
     * CIERRA LA CONEXION A LA BASE DE DATOS.
     */
    public void cerrar() {
        try {
            DbUtils.closeQuietly(getDB());
        } catch (NullPointerException ex) {
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * OBTIENE EL TEXTO DE LA FECHA DEL DIA ACTUAL EN FORMATO DD/MM/YYYY
     *
     * @return
     */
    public String getHoy() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * REALIZA UNA CONSULTA DE UPDATE,DELETE,INSERT A LA BASE DE DATOS
     * SUMINISTRANDO LA CONSULTA SIN PARAMETROS YA SEA QUE ESTEN INCLUIDOS EN EL
     * QUERY O NO LOS REQUIERA
     *
     * @param db       Conexion
     * @param sid      Usuario que realiza la accion
     * @param sql      consulta a la base de datos
     * @param terminar indica si debe cerrar y hacer commit de la consulta
     *
     * @return
     */
    public String actualizar(conex db, String sid, String sql, boolean terminar) {
        Integer afectados;
        try {
            afectados = db.getQuery().update(db.getDB(), sql);
            if (terminar) {
                db.commit();
                db.cerrar();
            }
            return afectados.toString();
        } catch (SQLException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex1);
            }
            db.cerrar();
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getLocalizedMessage();
        }
    }

    /**
     * REALIZA UNA CONSULTA DE UPDATE,DELETE,INSERT A LA BASE DE DATOS
     * SUMINISTRANDO LA CONSULTA Y LOS PARAMETROS
     *
     * @param db       Conexion
     * @param sid      Usuario que realiza la accion
     * @param sql      consulta a la base de datos
     * @param datos    parametros de la consulta
     * @param terminar indica si debe cerrar y hacer commit de la consulta
     *
     * @return
     */
    public String actualizar(conex db, String sid, String sql, Object[] datos, boolean terminar) {
        Integer afectados;
        try {
            afectados = db.getQuery().update(db.getDB(), sql, datos);
            if (terminar) {
                db.commit();
                db.cerrar();
            }
            return afectados.toString();
        } catch (SQLException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex1);
            }
            db.cerrar();
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getLocalizedMessage();
        }
    }

    /**
     * REALIZA UNA CONSULTA DE SELECT SIN PARAMETROS. este metodo recibe la
     * consulta para un select y la ejecuta para luego retornar los resultados.
     * este metodo adicionalmente recibe un parametro <b>true</b> o <b>false</b>
     * para indicar si se debe cerrar la conexion al terminar el llamado indicar
     *
     * @param db
     * @param sql      sentencia SQL del select a ejecutar
     * @param terminar indica si debe hacer commit y cerrar la conexion
     *
     * @return retorna la lista de los registros encontrados o un mensaje de
     *         error en un campo llamado mensaje en la posicion 0 de la lista
     */
    public List<Map<String, Object>> select(conex db, String sql, boolean terminar) {
        List<Map<String, Object>> afectados;
        try {
            afectados = db.getQuery().query(db.getDB(), sql, new MapListHandler());
            if (terminar) {
                db.cerrar();
            }
            return afectados;
        } catch (SQLException ex) {
            db.cerrar();
            afectados = new ArrayList();
            afectados.add(new HashMap());
            afectados.get(0).put("ERROR", ex);
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
            return afectados;
        }
    }

    /**
     * REALIZA UNA CONSULTA DE SELECT CON PARAMETROS. este metodo recibe la
     * consulta para un select y los parametros de este mismo. este metodo
     * adicionalmente recibe un parametro <b>true</b> o <b>false</b> para
     * indicar si se debe cerrar la conexion al terminar el llamado indicar
     *
     * @param db
     * @param sql      sentencia SQL del select a ejecutar
     * @param datos    parametros de la sentencia a ejecutar
     * @param terminar indica si debe hacer commit y cerrar la conexion
     *
     * @return retorna la lista de los registros encontrados o un mensaje de
     *         error en un campo llamado mensaje en la posicion 0 de la lista
     */
    public List<Map<String, Object>> select(conex db, String sql, Object[] datos, boolean terminar) {
        List<Map<String, Object>> afectados;
        try {
            afectados = db.getQuery().query(db.getDB(), sql, new MapListHandler(), datos);
            if (terminar) {
                db.cerrar();
            }
            if (!afectados.isEmpty()) {
                afectados.get(0).put("ESTADO_PETICION", 1);
            }
            return afectados;
        } catch (SQLException ex) {
            if (terminar) {
                db.cerrar();
            }
            afectados = new ArrayList();
            afectados.add(new HashMap());
            afectados.get(0).put("ERROR", ex);
            afectados.get(0).put("ESTADO_PETICION", 0);
            Logger.getLogger(conex.class.getName()).log(Level.SEVERE, null, ex);
            return afectados;
        }
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public Connection getDB() {
        return db;
    }

    public void setDB(Connection db) {
        this.db = db;
    }

    public QueryRunner getQuery() {
        return query;
    }

    public void setQuery(QueryRunner query) {
        this.query = query;
    }
}
