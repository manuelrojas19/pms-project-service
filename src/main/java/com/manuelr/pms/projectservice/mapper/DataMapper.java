package com.manuelr.pms.projectservice.mapper;

import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@ApplicationScoped
public class DataMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp ts) {
        if (Objects.nonNull(ts)) {
            return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime) {
        if (Objects.nonNull(offsetDateTime))
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        else
            return null;

    }

    public String asString(ObjectId id) {
        if (Objects.nonNull(id))
            return id.toString();
        else
            return null;
    }

    public ObjectId asObjectId(String id) {
        if (Objects.nonNull(id))
            return new ObjectId(id);
        else
            return null;
    }
}
