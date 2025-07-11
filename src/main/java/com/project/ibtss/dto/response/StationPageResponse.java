package com.project.ibtss.dto.response;

import com.project.ibtss.model.Stations;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationPageResponse {
    List<Stations> stations;
    int currentPage;
    int totalPages;
    long totalElements;
    boolean hasNextPage;
    boolean hasPreviousPage;
    int size;
}
