package com.univaq.project.auleweb.controller.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.framework.contoller.AbstractBaseController;
import com.univaq.project.framework.data.DataLayer;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

public abstract class AuleWebController extends AbstractBaseController {

    @Override
    protected DataLayer createDataLayer(DataSource ds) throws ServletException {
        try {
            return new DataLayerImpl(ds);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

}
