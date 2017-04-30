package com.ignoretheextraclub.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by caspar on 05/03/17.
 */
public enum Permission
{
    USER,
    COMMENTER_WRITER,
    COMMENTS_AUTOMATICALLY_VISIBLE,
    ADMIN;

    public GrantedAuthority getAsGrantedAuthority()
    {
        return new SimpleGrantedAuthority(this.toString());
    }
}
