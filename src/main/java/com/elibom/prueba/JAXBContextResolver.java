/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elibom.prueba;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

/**
 *
 * @author lacripta
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private final static String ENTITY_PACKAGE = "com.elibom.prueba";
    private final static JAXBContext context;

    static {
        try {
            context = new JAXBContextAdapter(new JSONJAXBContext(JSONConfiguration.mapped().rootUnwrapping(false).build(), ENTITY_PACKAGE));
        } catch (final JAXBException ex) {
            throw new IllegalStateException("Could not resolve JAXBContext.", ex);
        }
    }

    @Override
    public JAXBContext getContext(final Class<?> type) {
        try {
            if (type.getPackage().getName().contains(ENTITY_PACKAGE)) {
                return context;
            }
        } catch (final Exception ex) {
            // trap, just return null
        }
        return null;
    }

    public static final class JAXBContextAdapter extends JAXBContext {

        private final JAXBContext context;

        public JAXBContextAdapter(final JAXBContext context) {
            this.context = context;
        }

        @Override
        public Marshaller createMarshaller() {
            Marshaller marshaller = null;
            try {
                marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            } catch (final PropertyException pe) {
                return marshaller;
            } catch (final JAXBException jbe) {
                return null;
            }
            return marshaller;
        }

        @Override
        public Unmarshaller createUnmarshaller() throws JAXBException {
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new DefaultValidationEventHandler());
            return unmarshaller;
        }

        @Override
        public Validator createValidator() throws JAXBException {
            return context.createValidator();
        }
    }
}
