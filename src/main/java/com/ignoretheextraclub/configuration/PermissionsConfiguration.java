package com.ignoretheextraclub.configuration;

import com.ignoretheextraclub.model.data.Permissions;

import java.util.Arrays;
import java.util.List;

/**
 * Created by caspar on 11/03/17.
 */
public class PermissionsConfiguration
{
    public static final List<Permissions> DEFAULT_PERMISSIONS = Arrays.asList(
            Permissions.COMMENTER,
            Permissions.USER
    );
}
