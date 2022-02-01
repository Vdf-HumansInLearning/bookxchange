package com.bookxchange.model;

import java.sql.Date;
import java.util.UUID;

public class Renting {

    private UUID id;
    private long marketBookId;
    private long memberId;
    private Date rentDate;

    public Renting(UUID id, long marketBookId, long memberId, Date rentDate) {
        this.id = UUID.randomUUID();
        this.marketBookId = marketBookId;
        this.memberId = memberId;
        this.rentDate = rentDate;
    }
}
