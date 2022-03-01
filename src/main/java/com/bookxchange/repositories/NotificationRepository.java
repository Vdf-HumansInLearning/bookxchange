package com.bookxchange.repositories;

import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.pojo.NotificationHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationsEntity, Integer> {

    @Query(value = "SELECT email_address, title FROM notifications join members M on M.member_user_id = notifications.member_id join book_market BM on BM.book_market_id = notifications.market_book_id join books b on BM.book_id = b.isbn where book_status = 'AVAILABLE' and SENT = 0", nativeQuery = true)
    List<NotificationHelper> getEmailToNotify();


}
