package Util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;


public class HibernateUtil
{
    @Getter
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        } catch (Throwable e) {
            System.out.println("EntityManagerFactory init failed. " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
