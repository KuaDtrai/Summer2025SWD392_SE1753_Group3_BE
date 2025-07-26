package com.project.ibtss.dto.request;

import com.project.ibtss.utilities.enums.StationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStatusStation {
    StationStatus status;
}
