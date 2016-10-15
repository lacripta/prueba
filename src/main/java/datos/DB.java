/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Sql2o;

/**
 *
 * @author lacripta
 */
public class DB {

    public static final Sql2o CON;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CON = new Sql2o("jdbc:mysql://localhost:3306/tabla", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("NO SE PUEDE ESTABLECER LA CONEXIONA LA BASE DE DATOS");
        }
    }

}
