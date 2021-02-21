package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Announcement;
import com.przemkeapp.housingassociationapp.Entity.Comment;
import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.dao.AnnouncementDao;
import com.przemkeapp.housingassociationapp.dao.UserDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Value("${resultsperpage}")
    private int resultsPerPage;

    private final AnnouncementDao announcementDao;

    private final UserDao userDao;

    public AnnouncementServiceImpl(AnnouncementDao announcementDao, UserDao userDao) {
        this.announcementDao = announcementDao;
        this.userDao = userDao;
    }

    @Override
    public void saveAnnouncement(Announcement announcement, String username) {

        User user = userDao.findUserByUsername(username);

        announcement.setAuthor(user);
        announcement.setDate(LocalDateTime.now());

        announcementDao.saveAnnouncement(announcement);
    }

    @Override
    public List<Announcement> findAnnouncementsByCommunityId(int communityId, int page) {
        return announcementDao.findAnnouncementsByCommunityId(communityId, page);
    }

    @Override
    public int announcementsPagesCountForCommunity(int communityId) {
        int announcementsCount = announcementDao.getAnnouncementsCountForCommunity(communityId);
        return (announcementsCount + resultsPerPage - 1) / resultsPerPage;
    }

    @Override
    public List<Announcement> findAnnouncementsByAuthor(String username, int page) {
        return announcementDao.findAnnouncementsByUsername(username, page);
    }

    @Override
    public int announcementsPagesCountForAuthor(String username) {
        int announcementsCount = announcementDao.getAnnouncementsCountForAuthor(username);
        return (announcementsCount + resultsPerPage - 1) / resultsPerPage;
    }

    @Override
    public Announcement findAnnouncementById(int id) {
        return announcementDao.findAnnouncementById(id);
    }

    @Override
    public void deleteAnnouncementById(int id) {
        announcementDao.deleteAnnouncementById(id);
    }

    @Override
    public void saveComment(int announcementId, Comment comment, String username) {
        Announcement announcement = announcementDao.findAnnouncementById(announcementId);
        comment.setAnnouncement(announcement);
        comment.setDate(LocalDateTime.now());
        User user = userDao.findUserByUsername(username);
        comment.setAuthor(user);
        userDao.saveComment(comment);
    }
}
