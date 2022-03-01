package com.bookxchange.repositories;

import com.bookxchange.model.NotificationsEntity;
import com.bookxchange.pojo.NotificationHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationsEntity, Integer> {

    @Query(value = "SELECT emailAddress, title FROM Notifications join Members M on M.MEMBERUSERID = Notifications.MEMBERID join BookMarket BM on BM.id = Notifications.MARKETBOOKID join books b on BM.bookID = b.isbn where bookStatus = 'AVAILABLE' and SENT = 0", nativeQuery = true)
    List<NotificationHelper> getEmailToNotify();


}
