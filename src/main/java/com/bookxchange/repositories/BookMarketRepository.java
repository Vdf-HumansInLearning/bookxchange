package com.bookxchange.repositories;

import com.bookxchange.model.BookMarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookMarketRepository extends JpaRepository<BookMarketEntity, String> {


}
