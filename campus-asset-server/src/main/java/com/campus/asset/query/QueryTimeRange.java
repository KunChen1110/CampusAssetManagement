package com.campus.asset.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryTimeRange implements Serializable {

    public enum Type {
        ALL_TIME,
        LAST_MONTHS
    }

    private Type type = Type.ALL_TIME;
    private Integer amount;

    public static QueryTimeRange allTime() {
        return new QueryTimeRange(Type.ALL_TIME, null);
    }

    public static QueryTimeRange lastMonths(int amount) {
        return new QueryTimeRange(Type.LAST_MONTHS, amount);
    }
}
