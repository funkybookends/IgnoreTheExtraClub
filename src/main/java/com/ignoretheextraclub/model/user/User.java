package com.ignoretheextraclub.model.user;

import com.ignoretheextraclub.configuration.preferences.UserConfiguration;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by caspar on 05/03/17.
 */
@Document(collection = "users")
public class User implements UserDetails
{
    private @Id String username;
    private String encodedPassword;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean enabled;
    private List<Permission> permissions;
    private Map<String, PatternList> patternLists;
    private List<Juggled> juggleds;
    private boolean publiclyVisible;
    private @Transient List<Comment> comments;

    public User(final String username,
                final String encodedPassword)
    {
        this(username,
                encodedPassword,
                UserConfiguration.DEFAULT_PERMISSIONS);
    }

    public User(final String username,
                final String encodedPassword,
                final List<Permission> permissions)
    {
        this(username,
                encodedPassword,
                false,
                false,
                false,
                false,
                false,
                permissions,
                UserConfiguration.defaultUserPatternList(),
                new ArrayList<>());
    }

    @PersistenceConstructor
    public User(final String username,
                final String encodedPassword,
                final boolean accountExpired,
                final boolean accountLocked,
                final boolean credentialsExpired,
                final boolean enabled,
                final boolean publiclyVisible,
                final List<Permission> permissions,
                final Map<String, PatternList> patternLists,
                final List<Juggled> juggleds)
    {
        this.username = username;
        this.encodedPassword = encodedPassword;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.credentialsExpired = credentialsExpired;
        this.enabled = enabled;
        this.publiclyVisible = publiclyVisible;
        this.permissions = permissions;
        this.patternLists = patternLists;
        this.juggleds = juggleds;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return permissions.stream()
                          .map(Permission::getAsGrantedAuthority)
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

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public boolean isAdmin()
    {
        return permissions.contains(Permission.ADMIN);
    }

    public String getEncodedPassword()
    {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword)
    {
        this.encodedPassword = encodedPassword;
    }

    public boolean isAccountExpired()
    {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired)
    {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked()
    {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked)
    {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialsExpired()
    {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired)
    {
        this.credentialsExpired = credentialsExpired;
    }

    public List<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }

    public boolean hasPermission(final Permission perm)
    {
        return permissions.contains(perm);
    }

    public Map<String, PatternList> getPatternLists()
    {
        return patternLists;
    }

    public void setPatternLists(Map<String, PatternList> patternLists)
    {
        this.patternLists = patternLists;
    }

    public List<Juggled> getJuggleds()
    {
        return juggleds;
    }

    public void setJuggleds(List<Juggled> juggleds)
    {
        this.juggleds = juggleds;
    }

    public boolean isPubliclyVisible()
    {
        return publiclyVisible;
    }

    public void setPubliclyVisible(boolean publiclyVisible)
    {
        this.publiclyVisible = publiclyVisible;
    }

    public Optional<PatternList> getList(final String listName)
    {
        return Optional.ofNullable(patternLists.get(listName));
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(final List<Comment> comments)
    {
        this.comments = comments;
    }

    public boolean addJuggled(final Juggled juggled)
    {
        return juggleds.add(juggled);
    }
}
