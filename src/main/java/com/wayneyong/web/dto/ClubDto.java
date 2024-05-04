package com.wayneyong.web.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotEmpty;
import java.security.cert.CertPathBuilder;
import java.time.LocalDateTime;

@Data
@Builder
public class ClubDto {
    //add validation here

    private Long id;
    @NotEmpty(message = "Club title should bot be empty")
    private String title;
    @NotEmpty(message = "Photo link should bot be empty")
    private String photoUrl;
    @NotEmpty(message = "Content should bot be empty")
    private String content;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
