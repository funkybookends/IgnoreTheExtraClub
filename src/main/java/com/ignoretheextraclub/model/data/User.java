package com.ignoretheextraclub.model.data;

import com.ignoretheextraclub.configuration.PermissionsConfiguration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by caspar on 05/03/17.
 */
@Document(collection = "users")
public class User implements UserDetails
{

    private @Id String            username;
    private     String            encodedPassword;
    private     boolean           accountExpired;
    private     boolean           accountLocked;
    private     boolean           credentialsExpired;
    private     boolean           enabled;
    private     List<Permissions> permissions;

    public User(final String username,
                final String encodedPassword)
    {
        this(username,
             encodedPassword,
             PermissionsConfiguration.DEFAULT_PERMISSIONS);
    }

    public User(final String username,
                final String encodedPassword,
                final List<Permissions> permissions)
    {
        this(username,
             encodedPassword,
             false,
             false,
             false,
             false,
             permissions);
    }

    public User(final String username,
                final String encodedPassword,
                final boolean accountExpired,
                final boolean accountLocked,
                final boolean credentialsExpired,
                final boolean enabled,
                final List<Permissions> permissions)
    {
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.credentialsExpired = credentialsExpired;
        this.enabled = enabled;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return permissions.stream()
                          .map(role -> new SimpleGrantedAuthority(role.toString()))
                          .collect(Collectors.toList());
    }

    @Override
    public String getPassword()
    {
        return encodedPassword;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled()
    {
        return !enabled;
    }
}
