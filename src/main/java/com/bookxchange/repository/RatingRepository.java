package com.bookxchange.repository;

import com.bookxchange.model.RatingEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends PagingAndSortingRepository<RatingEntity, Integer> {


}
