package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.Announcement;

import java.util.List;

public interface AnnouncementDao {
    void saveAnnouncement(Announcement announcement);
    List<Announcement> findAnnouncementsByCommunityId(int communityId, int page);
    int getAnnouncementsCountForCommunity(int communityId);
    List<Announcement> findAnnouncementsByUsername(String username, int page);
    int getAnnouncementsCountForAuthor(String username);
    Announcement findAnnouncementById(int id);
    void deleteAnnouncementById(int id);
}
