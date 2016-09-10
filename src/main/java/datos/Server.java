/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author lacripta
 */
public class Server {

    String name;
    String state;
    Integer id;

    public void setName(String name) {
        this.name = name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Integer getId() {
        return id;
    }

    public Server() {
    }

    public Server(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public Server(String name, String state, Integer id) {
        this.name = name;
        this.state = state;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Server{" + "name=" + name + ", state=" + state + ", id=" + id + '}';
    }

    public List<Server> findAll() {
        try (conex db = new conex()) {
            ResultSetHandler<List<Server>> h = new BeanListHandler<>(Server.class);
            String sql = "select * from server";
            List<Server> servers = db.getQuery().query(db.getDB(), sql, h);
            return servers;
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Server findServerById(Integer id) {
        try (conex db = new conex()) {
            ResultSetHandler<List<Server>> h = new BeanListHandler<>(Server.class);
            String sql = "select * from server where id = ?";
            List<Server> servers = db.getQuery().query(db.getDB(), sql, new Object[]{id}, h);
            System.out.println("servers = " + servers);
            if (servers.isEmpty()) {
                return new Server();
            } else {
                return servers.get(0);
            }
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Integer editServer(Server server) {
        try (conex db = new conex()) {
            String sql = "update server set name = ?, state = ? where id = ?";
            Integer servers = db.getQuery().update(db.getDB(), sql, new Object[]{server.name, server.state, server.id});
            return servers;
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Integer deleteServer(Integer id) {
        try (conex db = new conex()) {
            String sql = "delete from server where id = ?";
            Integer servers = db.getQuery().update(db.getDB(), sql, new Object[]{id});
            return servers;
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
