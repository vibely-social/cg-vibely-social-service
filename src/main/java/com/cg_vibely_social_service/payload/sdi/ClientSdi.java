package com.cg_vibely_social_service.payload.sdi;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ClientSdi {
    private String name;
    private String username;
    private String email;

}
