/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

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
}
