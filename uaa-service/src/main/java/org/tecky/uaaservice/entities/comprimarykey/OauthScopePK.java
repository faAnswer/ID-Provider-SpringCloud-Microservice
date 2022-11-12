package org.tecky.uaaservice.entities.comprimarykey;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OauthScopePK implements Serializable {

    private int appid;
    private int scopeid;
}
