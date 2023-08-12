package com.univaq.project.auleweb.controller;

import com.univaq.project.auleweb.data.implementation.DataLayerImpl;
import com.univaq.project.auleweb.data.model.Amministratore;
import com.univaq.project.framework.contoller.AbstractBaseController;
import com.univaq.project.framework.data.DataException;
import com.univaq.project.framework.data.DataLayer;
import com.univaq.project.framework.security.SecurityHelpers;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    protected Amministratore getLoggedAdminstrator(DataLayerImpl dataLayer, HttpServletRequest request) throws DataException {
        HttpSession session = SecurityHelpers.checkSession(request);
        if (session != null) {
            return dataLayer.getAmministratoriDAO().getAmministratoreById((Integer) session.getAttribute("userid"));
        }
        return null;
    }
}
