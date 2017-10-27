package dao;

import dao.impl.XmlDaoImpl;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private final XmlDao xmlDAO = new XmlDaoImpl();

    private DAOFactory() {}

    public XmlDao getXmlDao() {
        return xmlDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
