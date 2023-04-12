package mx.com.hunkabann.skf.util;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class HibernateUtil 
{
	private static Logger logger = Logger.getLogger(HibernateUtil.class);

    private static final SessionFactory sessionFactory;

    static 
    {
        try 
        {
        	if (logger.isDebugEnabled()) 
        	{
				logger.debug("Configurando Hibernate");
			}
            // Create the SessionFactory
//            sessionFactory = new Configuration().configure().buildSessionFactory();
        	sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
            
        } 
        catch (Throwable ex) 
        {
            // Make sure you log the exception, as it might be swallowed
        	if (logger.isDebugEnabled()) 
        	{
				logger.debug("error al configurar Hibernate", ex);
			}
        	
            throw new ExceptionInInitializerError(ex);
        }
    }
    

    @SuppressWarnings("unchecked")
	public static final ThreadLocal session = new ThreadLocal();

    @SuppressWarnings("unchecked")
	public static Session currentSession() throws HibernateException 
    {
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        if (s == null) 
        {
            s = sessionFactory.openSession();
            session.set(s);
            if (logger.isDebugEnabled()) 
        	{
				logger.debug("creando conexion");
			}
        }
        return s;
        
    }	//currentSession

    @SuppressWarnings("unchecked")
	public static void closeSession() throws HibernateException 
    {
        Session s = (Session) session.get();
        session.set(null);
        if (s != null)
            s.close();
        if (logger.isDebugEnabled()) 
    	{
			logger.debug("closeSession");
		}
        
    }	// closeSession
    
    @SuppressWarnings("unchecked")
	public static void closeSession(Session p_s) throws HibernateException 
    {
        Session s1 = p_s;
        session.set(null);
        if (s1 != null)
        	s1.close();
        if (logger.isDebugEnabled()) 
    	{
			logger.debug("closeSession 2");
		}
        
    }	// closeSession
    
}	// end of class

