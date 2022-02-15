package ua.kpi.cardgame;

import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NamingException;
import java.util.Enumeration;

public class Runner {
    public static void main(String[] args) throws NamingException {
        Name objectName = new CompositeName("java:comp/env/jdbc");

        Enumeration<String> elements = objectName.getAll();
        while(elements.hasMoreElements()) {
            System.out.println(elements.nextElement());
        }

//        Hashtable env = new Hashtable();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

//        Context ctx = new InitialContext(env);

        ConnectionManager connection = new ConnectionManager(); //.getConnection();
    }
}
