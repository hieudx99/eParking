package com.example.eparking.model.dto;

import java.io.Serializable;

public class StartEndDateDTO implements Serializable {
    private String startDate;
    private String endDate;

    public StartEndDateDTO(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

