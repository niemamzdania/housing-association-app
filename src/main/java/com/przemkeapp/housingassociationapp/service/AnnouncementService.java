package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    void saveAnnouncement(Announcement announcement, String username);
    List<Announcement> findAnnouncementsByCommunityId(int communityId, int page);
    int announcementsPagesCountForCommunity(int communityId);
    List<Announcement> findAnnouncementsByAuthor(String username, int page);
    int announcementsPagesCountForAuthor(String username);
    Announcement findAnnouncementById(int id);
}
