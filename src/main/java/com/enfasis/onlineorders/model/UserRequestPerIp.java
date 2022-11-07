package com.enfasis.onlineorders.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequestPerIp implements Serializable {
    private String userIp;
    private String countRequest;
    private Instant dateRequest;
}
