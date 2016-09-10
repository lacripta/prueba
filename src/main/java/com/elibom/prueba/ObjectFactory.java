/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elibom.prueba;

import datos.Server;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 * @author lacripta
 */
@XmlRegistry
public class ObjectFactory {

    public Server createNewServer() {
        return new Server();
    }
}
