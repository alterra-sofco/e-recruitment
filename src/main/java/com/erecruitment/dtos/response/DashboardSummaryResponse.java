package com.erecruitment.dtos.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DashboardSummaryResponse {
    private final Integer totalJobPosting;

    private final Integer totalJobRequest;

    private final Integer totalNewJobRequest;

    private final Integer totalApplied;
}
