package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Announcement;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

     @Value("${resultsperpage}")
     private int resultsPerPage;

    private final EntityManager entityManager;

    public AnnouncementDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveAnnouncement(Announcement announcement) {

        Session session = entityManager.unwrap(Session.class);

        session.saveOrUpdate(announcement);
    }

    @Override
    @Transactional
    public List<Announcement> findAnnouncementsByCommunityId(int communityId, int page) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("from Announcement u where u.author.community.id=:communityId order by u.date desc");
        theQuery.setParameter("communityId", communityId);
        theQuery.setFirstResult((page * resultsPerPage) - resultsPerPage);
        theQuery.setMaxResults(resultsPerPage);

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public int getAnnouncementsCountForCommunity(int communityId) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("select count(u.id) from Announcement u where u.author.community.id=:communityId");
        theQuery.setParameter("communityId", communityId);

        return Integer.parseInt(String.valueOf(theQuery.getSingleResult()));
    }

    @Override
    @Transactional
    public List<Announcement> findAnnouncementsByUsername(String username, int page) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("from Announcement u where u.author.userName=:username order by u.date desc");
        theQuery.setParameter("username", username);
        theQuery.setFirstResult((page * resultsPerPage) - resultsPerPage);
        theQuery.setMaxResults(resultsPerPage);

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public int getAnnouncementsCountForAuthor(String username) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("select count(u.id) from Announcement u where u.author.userName=:username");
        theQuery.setParameter("username", username);

        return Integer.parseInt(String.valueOf(theQuery.getSingleResult()));
    }

    @Override
    @Transactional
    public Announcement findAnnouncementById(int id) {

        Session currentSession = entityManager.unwrap(Session.class);

        return currentSession.get(Announcement.class, id);
    }
}
