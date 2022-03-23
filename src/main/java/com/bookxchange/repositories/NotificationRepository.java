package com.bookxchange.repositories;

import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.pojo.NotificationHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationsEntity, Integer> {

    @Query(value = "SELECT notifications.id as notid, email_address, title, subject, content_body, template_name, username, member_user_id, notifications.member_uuid FROM notifications join members M on M.member_uuid = notifications.member_uuid join email_templates et on notifications.email_template_id = et.id join book_market BM on BM.book_market_uuid = notifications.market_book_uuid join books b on BM.book_isbn = b.isbn where book_status = 'AVAILABLE' and SENT = 0;", nativeQuery = true)
    List<NotificationHelper> getEmailToNotify();

    @Modifying
    @Query(value = "UPDATE notifications SET sent = 1 where id = ?1", nativeQuery = true)
    void updateToSent(Integer id);


    boolean existsNotificationsEntitiesByMarketBookUuidAndMemberUuid(String marketBookUuid, String memberUuid);


}
