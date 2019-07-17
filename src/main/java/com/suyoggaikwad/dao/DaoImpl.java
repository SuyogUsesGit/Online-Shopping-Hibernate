package com.suyoggaikwad.dao;

import com.suyoggaikwad.model.Cart;
import com.suyoggaikwad.model.Item;
import com.suyoggaikwad.model.User;

import javax.persistence.*;
import java.util.*;

public class DaoImpl implements Dao{

    @Override
    public int validateUser(String userName, String password) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("select u from User u where u.username=?1 and u.password=?2");
        query.setParameter(1, userName);
        query.setParameter(2, password);
        User u = null;
        try {
            u = (User) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Caught exception");
        }
        em.close();
        if(u!=null) return u.getId();
        return -1;
    }

    @Override
    public boolean registerUser(String userName, String password) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query q = em.createQuery("select u.username from User u where u.username=?1");
        q.setParameter(1, userName);
        String s = null;
        try {
            s = (String) q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Caught exception");
        }

        if(s==null) {
            em.persist(new User(userName, password));
            et.commit();
            em.close();
            return true;
        } else {
            em.close();
            return false;
        }
    }

    @Override
    public List<Item> getItems() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("select i from Item i where i.quantity > 0");
        List<Item> list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public boolean addToCart(List<Cart> cartList, int userId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("delete from Cart c where c.userID = ?1");
        query.setParameter(1, userId);
        query.executeUpdate();
        for(Cart cart: cartList)
            em.persist(cart);
        et.commit();
        em.close();
        return true;

    }

    @Override
    public List<Cart> checkCartForUser(int userId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createQuery("select c from Cart c where c.userID = ?1");
        query.setParameter(1, userId);
        List<Cart> list = query.getResultList();
        return list;
    }

    @Override
    public boolean checkout(int userId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createQuery("select i from Item i");
        List<Item> items = query.getResultList();
        Map<String, Integer> itemsMap = new HashMap<>();
        for (Item item: items) itemsMap.put(item.getName(), item.getQuantity());

        Query query2 = em.createQuery("select c from Cart c");
        List<Cart> carts = query2.getResultList();
        Map<String, Integer> cartsMap = new HashMap<>();
        for (Cart cart: carts) cartsMap.put(cart.getItemName(), cart.getQuantity());

        Map<String, Integer> rsMap = new HashMap<>();

        for(String s: cartsMap.keySet()) {
                if(itemsMap.containsKey(s)) {
                    int quantityAvailable = itemsMap.get(s);
                    int quantityRequired = cartsMap.get(s);
                    if(quantityAvailable >= quantityRequired) {
                        int quantityRemaining = quantityAvailable - quantityRequired;
                        rsMap.put(s, quantityRemaining);
                    } else return false;
                }
        }


        for(String s: rsMap.keySet())  {
               Query query3 = em.createQuery("update Item i set i.quantity = ?1 where i.name = ?2");
               query3.setParameter(1, rsMap.get(s));
               query3.setParameter(2, s);
               query3.executeUpdate();
        }

        Query query4 = em.createQuery("delete from Cart c where c.userID = ?1");
        query4.setParameter(1, userId);
        query4.executeUpdate();

        et.commit();

        return true;


    }
}
