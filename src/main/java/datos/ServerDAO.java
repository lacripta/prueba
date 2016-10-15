/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

/**
 *
 * @author lacripta
 */
public final class ServerDAO {

    private static final Logger LOG = Logger.getLogger(ServerDAO.class.getName());

    public static Resultado findAll() {
        try (Connection con = DB.CON.beginTransaction()) {
            String sql = "select * from server";
            List<Server> servers = con.createQuery(sql)
                    .executeAndFetch(Server.class);
            Resultado respuesta = new Resultado(servers, true, servers.size());
            return respuesta;
        } catch (Sql2oException ex) {
            LOG.log(Level.SEVERE, null, ex);
            Resultado respuesta = new Resultado("HA OCURRIDO UN ERROR", ex.getLocalizedMessage(), ex, false, 0);
            return respuesta;
        }
    }

    public static Resultado findServerById(Integer id) {
        try (Connection con = DB.CON.beginTransaction()) {
            String sql = "select * from server where id = :id";
            Server servers = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Server.class);
            Resultado respuesta = new Resultado(servers, true, 1);
            return respuesta;
        } catch (Sql2oException ex) {
            LOG.log(Level.SEVERE, null, ex);
            Resultado respuesta = new Resultado("HA OCURRIDO UN ERROR", ex.getLocalizedMessage(), ex, false, 0);
            return respuesta;
        }
    }

    public static Resultado editServer(Server server) {
        try (Connection con = DB.CON.beginTransaction()) {
            String sql = "update server set name = :name, state = :state where id = :id";
            con.createQuery(sql)
                    .bind(server)
                    .executeUpdate();
            Resultado respuesta = new Resultado(new String[0], true, 1);
            return respuesta;
        } catch (Sql2oException ex) {
            LOG.log(Level.SEVERE, null, ex);
            Resultado respuesta = new Resultado("HA OCURRIDO UN ERROR", ex.getLocalizedMessage(), ex, false, 0);
            return respuesta;
        }
    }

    public static Resultado addServer(Server server) {
        try (Connection con = DB.CON.beginTransaction()) {
            String sql = "insert into server (name,state) values(:name,:state)";
            con.createQuery(sql)
                    .bind(server)
                    .executeUpdate();
            Resultado respuesta = new Resultado(new String[0], true, 1);
            return respuesta;
        } catch (Sql2oException ex) {
            LOG.log(Level.SEVERE, null, ex);
            Resultado respuesta = new Resultado("HA OCURRIDO UN ERROR", ex.getLocalizedMessage(), ex, false, 0);
            return respuesta;
        }
    }

    public static Resultado deleteServer(Integer id) {
        try (Connection con = DB.CON.beginTransaction()) {
            String sql = "delete from server where id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            Resultado respuesta = new Resultado(new String[0], true, 1);
            return respuesta;
        } catch (Sql2oException ex) {
            LOG.log(Level.SEVERE, null, ex);
            Resultado respuesta = new Resultado("HA OCURRIDO UN ERROR", ex.getLocalizedMessage(), ex, false, 0);
            return respuesta;
        }
    }
}
